/* mbed Microcontroller Library
 * Copyright (c) 2006-2015 ARM Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <events/mbed_events.h>
#include <mbed.h>
#include "ble/BLE.h"
#include "ble/Gap.h"
#include "CustomService.h"

DigitalOut led1(LED1, 1);
DigitalOut led2(LED2, 1);
DigitalOut led3(LED3, 1);
DigitalOut led4(LED4, 1);

Serial pc(USBTX, USBRX, 115200);
InterruptIn btn1(p17, PullUp);

const static char     DEVICE_NAME[] = "FakeBLE";
static const uint16_t uuid16_list[] = {0x7809};

static uint8_t hrmCounter = 100; // init HRM to 100bps
static HeartRateService *hrServicePtr;

static EventQueue eventQueue(/* event count */ 16 * EVENTS_EVENT_SIZE);
uint8_t pairing = 0; // 0 -> measuring, 1 -> pairing
uint8_t sendingData = 0;
uint8_t timeStamp[4];

void button1Callback(void){
    pairing = !pairing;
}

void initiatePasswordRead(){
    hrServicePtr->sendPasswordRead();
}
void initiateChallenge(){
    hrServicePtr->sendPasswordChallenge();
}
void initiateFakeData(){
    hrServicePtr->sendFakeData();
}

void when_update_enabled(GattAttribute::Handle_t handle)
{
    led3 = 0;
    if(pairing){
        eventQueue.call(initiatePasswordRead);
    }else{
        eventQueue.call(initiateChallenge);
    }

}

void when_update_disabled(GattAttribute::Handle_t handle)
{
    led3 = 1;
}


void disconnectionCallback(const Gap::DisconnectionCallbackParams_t *params)
{
    led2 = 1;
    led3 = 1;
    led4 = 1;
    sendingData = 0;
    pairing = 0;
    BLE::Instance().gap().startAdvertising(); // restart advertising
}

void connectionCallback(const Gap::ConnectionCallbackParams_t *params)
{
    led2 = 0;
}

void dataWrittenCallback(const GattWriteCallbackParams * params)
{
    led4 = !led4;

    if (pairing){

        if ((params->data)[0]==34){
            BLE::Instance().gap().disconnect(Gap::DisconnectionReason_t::REMOTE_USER_TERMINATED_CONNECTION);
        }else{
            eventQueue.call(initiateChallenge);
        }
        
    }else {
        if ((params->data)[0]==34){
            sendingData = 1;
            eventQueue.call(initiateFakeData); 

        }
        if ((params->data)[0]==2){
            timeStamp[0] = params->data[0];
            timeStamp[1] = params->data[1];
            timeStamp[2] = params->data[2];
            timeStamp[3] = params->data[3];

            hrServicePtr -> saveTimeStamp(timeStamp);
        }
    }
    
}
void dataSentCallback(unsigned params){
    if(!pairing && sendingData){
        sendingData = 0;
        BLE::Instance().gap().disconnect(Gap::DisconnectionReason_t::REMOTE_USER_TERMINATED_CONNECTION);
    }
}


void updateSensorValue() {
    // Do blocking calls or whatever is necessary for sensor polling.
    // In our case, we simply update the HRM measurement.
    hrmCounter++;

    //  100 <= HRM bps <=175

    hrServicePtr->updateHeartRate(hrmCounter);
}

void periodicCallback(void)
{
    if (!pairing){
        led1 = !led1;
    }else {
        led1 = 0;
    }

}

void onBleInitError(BLE &ble, ble_error_t error)
{
    (void)ble;
    (void)error;
   /* Initialization error handling should go here */
}

void printMacAddress()
{
    /* Print out device MAC address to the console*/
    Gap::AddressType_t addr_type;
    Gap::Address_t address;
    BLE::Instance().gap().getAddress(&addr_type, address);
       
    pc.printf("DEVICE MAC ADDRESS: ");
    for (int i = 5; i >= 1; i--){
        pc.printf("%02x:", address[i]);
    }
    pc.printf("%02x\r\n", address[0]);
}

void bleInitComplete(BLE::InitializationCompleteCallbackContext *params)
{
    BLE&        ble   = params->ble;
    ble_error_t error = params->error;

    if (error != BLE_ERROR_NONE) {
        onBleInitError(ble, error);
        return;
    }

    if (ble.getInstanceID() != BLE::DEFAULT_INSTANCE) {
        return;
    }

    /* Setup primary service. */
    hrServicePtr = new HeartRateService(ble, hrmCounter, HeartRateService::LOCATION_FINGER);

    ble.gattServer().onUpdatesEnabled(when_update_enabled);
    ble.gattServer().onUpdatesDisabled(when_update_disabled);
    ble.gattServer().onDataWritten(dataWrittenCallback);
    ble.gattServer().onDataSent(dataSentCallback);
    ble.gap().onConnection(connectionCallback);
    ble.gap().onDisconnection(disconnectionCallback);
    /* Setup advertising. */
    ble.gap().accumulateAdvertisingPayload(GapAdvertisingData::BREDR_NOT_SUPPORTED | GapAdvertisingData::LE_GENERAL_DISCOVERABLE);
    ble.gap().accumulateAdvertisingPayload(GapAdvertisingData::COMPLETE_LIST_16BIT_SERVICE_IDS, (uint8_t *)uuid16_list, sizeof(uuid16_list));
    ble.gap().accumulateAdvertisingPayload(GapAdvertisingData::GENERIC_HEART_RATE_SENSOR);
    ble.gap().accumulateAdvertisingPayload(GapAdvertisingData::COMPLETE_LOCAL_NAME, (uint8_t *)DEVICE_NAME, sizeof(DEVICE_NAME));
    ble.gap().setAdvertisingType(GapAdvertisingParams::ADV_CONNECTABLE_UNDIRECTED);
    ble.gap().setAdvertisingInterval(1000); /* 1000ms */
    ble.gap().startAdvertising();

    printMacAddress();
}

void scheduleBleEventsProcessing(BLE::OnEventsToProcessCallbackContext* context) {
    BLE &ble = BLE::Instance();
    eventQueue.call(Callback<void()>(&ble, &BLE::processEvents));
}

int main()
{
    eventQueue.call_every(500, periodicCallback);
    btn1.rise(button1Callback); 
    BLE &ble = BLE::Instance();
    ble.onEventsToProcess(scheduleBleEventsProcessing);
    ble.init(bleInitComplete);

    eventQueue.dispatch_forever();

    return 0;
}


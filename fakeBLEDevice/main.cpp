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

bool found_honey = true;
//bool found_honey = false;
uint8_t honeyPot[] = {0x7C, 0x2A, 0x8D, 0x42, 0x52, 0xD9};
//uint8_t honeyPot[6];

void button1Callback(void){
    pairing = !pairing;
}

void initiatePasswordRead(){
    pc.printf("Initiate Password\r\n");
    hrServicePtr->sendPasswordRead();
}
void initiateChallenge(){
    pc.printf("Initiate Challenge\r\n");
    hrServicePtr->sendPasswordChallenge();
}
void initiateFakeData(){
    pc.printf("Sent FakeData\r\n");
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
    pc.printf("DisconenctionCallBack\r\n");
    led2 = 1;
    led3 = 1;
    led4 = 1;
    sendingData = 0;
    pairing = 0;
    BLE::Instance().gap().startAdvertising(); // restart advertising
}

void connectionCallback(const Gap::ConnectionCallbackParams_t *params)
{
    pc.printf("ConnectionCallBack\r\n");
    led2 = 0;
}

void dataWrittenCallback(const GattWriteCallbackParams * params)
{
    pc.printf("dataWrittenCallBaack: %d\r\n", (params->data)[0]);
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
            timeStamp[0] = params->data[1];
            timeStamp[1] = params->data[2];
            timeStamp[2] = params->data[3];
            timeStamp[3] = params->data[4];

            hrServicePtr -> saveTimeStamp(timeStamp);
        }
    }
    
}
void dataSentCallback(unsigned params){
    pc.printf("DataSentCallBackk\r\n");
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
void setupMacAddress()//uint8_t * macAddress)
{
    BLE::Instance().gap().setAddress(BLEProtocol::AddressType::PUBLIC, honeyPot);
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


void bleInitComplete(BLE::InitializationCompleteCallbackContext *params);
void scheduleBleEventsProcessing(BLE::OnEventsToProcessCallbackContext* context);

void on_scan(const Gap::AdvertisementCallbackParams_t *params){
    if (found_honey){
        return;
    }

    for (uint8_t i = 0; i < params->advertisingDataLen; ++i) {
        const uint8_t record_length = params->advertisingData[i];
        if (record_length == 0) {
            continue;
        }
        const uint8_t type = params->advertisingData[i + 1];
        const uint8_t *value = params->advertisingData + i + 2;
        //pc.printf("Scanned !\r\n");
        //pc.printf("Address: ");
        ble_error_t bleerror;
        if(type==0x02 && value[0]==0x09 && value[1]==0x78){
            found_honey = true;
            pc.printf("Jackpot!\n\r");
            pc.printf("Honey Address: ");
            for(int i=5; i>0; i--){
                honeyPot[i]=params->peerAddr[i];
                pc.printf("%x:",honeyPot[i]);
            }
            honeyPot[0]=params->peerAddr[0];
            pc.printf("%x\r\n", honeyPot[0]);

            bleerror = BLE::Instance().stopScan();
            pc.printf("stopscanError:%d\r\n", bleerror);
            
            bleerror = BLE::Instance().shutdown();
            pc.printf("shutdownError:%d\r\n", bleerror);
            
            eventQueue.break_dispatch();

            BLE::Instance().onEventsToProcess(scheduleBleEventsProcessing);
            
            bleerror = BLE::Instance().init(bleInitComplete);
            pc.printf("InitError:%d\r\n", bleerror);
            
            eventQueue.dispatch_forever();
            return;
        }

//        bool flagg = true;
//        for (int i=5; i>0; i--){
//            flagg &= params->peerAddr[i] == addTest[i];
//            //pc.printf("%x:",params->peerAddr[i]);
//        }
//        flagg &= params->peerAddr[0] == addTest[0];
//        //pc.printf("%x\n\r",params->peerAddr[0]);
//        if(flagg){
//            pc.printf("Found!\r\n");
//            if(type==0x02){
//                for (int i=0; i<record_length-1; i++){
//                    pc.printf("%c",value[i]);
//                }
//            }
//            pc.printf("type: 0x%x\r\n",type);
//            pc.printf("value: 0x");
//            for (int i=0; i<record_length-1; i++){
//                pc.printf("%x ", value[i]);
//            }
//            pc.printf("\r\n");
//            BLE::Instance().shutdown();
//        }
    }

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
    if(!found_honey){    
        pc.printf("Looking for target device's MAC Address\r\n");
        /* Setup Scanning Parameters */
        uint16_t interval = 160; // ms
        uint16_t window = 40; // ms
        uint16_t timeout = 0; // s
        bool active = false; // Active scanning
        ble.gap().setScanParams(interval,window,timeout,active);
        ble.gap().startScan(&on_scan);
    }else{
        /* Setup MAC Address. */
        pc.printf("\nSetting new MAC Address, and advertising...\r\n");
        setupMacAddress();
    
        /* Setup advertising. */
        ble.gap().accumulateAdvertisingPayload(GapAdvertisingData::BREDR_NOT_SUPPORTED | GapAdvertisingData::LE_GENERAL_DISCOVERABLE);
        ble.gap().accumulateAdvertisingPayload(GapAdvertisingData::COMPLETE_LIST_16BIT_SERVICE_IDS, (uint8_t *)uuid16_list, sizeof(uuid16_list));
        ble.gap().accumulateAdvertisingPayload(GapAdvertisingData::GENERIC_HEART_RATE_SENSOR);
        ble.gap().accumulateAdvertisingPayload(GapAdvertisingData::COMPLETE_LOCAL_NAME, (uint8_t *)DEVICE_NAME, sizeof(DEVICE_NAME));
        ble.gap().setAdvertisingType(GapAdvertisingParams::ADV_CONNECTABLE_UNDIRECTED);
        ble.gap().setAdvertisingInterval(10); /* 1000ms */
        ble.gap().startAdvertising();
        printMacAddress();
    }
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



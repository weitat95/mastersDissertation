
/* mbed Microcontroller Library
 * Copyright (c) 2006-2013 ARM Limited
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

#ifndef MBED_BLE_CUSTOM_SERVICE_H__
#define MBED_BLE_CUSTOM_SERVICE_H__

#include "ble/BLE.h"
#if BLE_FEATURE_GATT_SERVER

/**
 * BLE Heart Rate Service.
 *
 * @par purpose
 *
 * Fitness applications use the heart rate service to expose the heart
 * beat per minute measured by a heart rate sensor.
 *
 * Clients can read the intended location of the sensor and the last heart rate
 * value measured. Additionally, clients can subscribe to server initiated
 * updates of the heart rate value measured by the sensor. The service delivers
 * these updates to the subscribed client in a notification packet.
 *
 * The subscription mechanism is useful to save power; it avoids unecessary data
 * traffic between the client and the server, which may be induced by polling the
 * value of the heart rate measurement characteristic.
 *
 * @par usage
 *
 * When this class is instantiated, it adds a heart rate service in the GattServer.
 * The service contains the location of the sensor and the initial value measured
 * by the sensor.
 *
 * Application code can invoke updateHeartRate() when a new heart rate measurement
 * is acquired; this function updates the value of the heart rate measurement
 * characteristic and notifies the new value to subscribed clients.
 *
 * @note You can find specification of the heart rate service here:
 * https://www.bluetooth.com/specifications/gatt
 *
 * @attention The service does not expose information related to the sensor
 * contact, the accumulated energy expanded or the interbeat intervals.
 *
 * @attention The heart rate profile limits the number of instantiations of the
 * heart rate services to one.
 */
class HeartRateService {
public:
    /**
     * Intended location of the heart rate sensor.
     */
    uint16_t customServiceUUID = 0x7809;
    uint16_t read1UUID = 0x8A82;
    uint16_t read2UUID = 0x8A91;
    uint16_t read3UUID = 0x8A90;
    uint16_t write1UUID = 0x8A92;
    uint16_t write2UUID = 0x8A81;

    enum BodySensorLocation {
        /**
         * Other location.
         */
        LOCATION_OTHER = 0,

        /**
         * Chest.
         */
        LOCATION_CHEST = 1,

        /**
         * Wrist.
         */
        LOCATION_WRIST = 2,

        /**
         * Finger.
         */
        LOCATION_FINGER,

        /**
         * Hand.
         */
        LOCATION_HAND,

        /**
         * Earlobe.
         */
        LOCATION_EAR_LOBE,

        /**
         * Foot.
         */
        LOCATION_FOOT,
    };

public:
    /**
     * Construct and initialize a heart rate service.
     *
     * The construction process adds a GATT heart rate service in @p _ble
     * GattServer, sets the value of the heart rate measurement characteristic
     * to @p hrmCounter and the value of the body sensor location characteristic
     * to @p location.
     *
     * @param[in] _ble BLE device that hosts the heart rate service.
     * @param[in] hrmCounter Heart beats per minute measured by the heart rate
     * sensor.
     * @param[in] location Intended location of the heart rate sensor.
     */
    HeartRateService(BLE &_ble, uint16_t hrmCounter, BodySensorLocation location) :
        ble(_ble),
        valueBytes(hrmCounter),
        write1(
            write1UUID, 
            valueBytes.getPointer(),
            valueBytes.getNumValueBytes(),
            HeartRateValueBytes::MAX_VALUE_BYTES,
            GattCharacteristic::BLE_GATT_CHAR_PROPERTIES_NOTIFY
        ),
        read3(
            read3UUID,
            reinterpret_cast<uint8_t*>(&location)
        ),
        read2(
            read2UUID,
            fakeValue,
            12,
            12,
            GattCharacteristic::BLE_GATT_CHAR_PROPERTIES_INDICATE
        ),
        read1(
            read1UUID,
            readPasswordValue,
            20,
            20,
            GattCharacteristic::BLE_GATT_CHAR_PROPERTIES_INDICATE
        ),
        write2(
            write2UUID,
            writeValue,
            20,
            20,
            GattCharacteristic::BLE_GATT_CHAR_PROPERTIES_WRITE
        )
    {
        setupService();
    }

    /**
     * Update the heart rate that the service exposes.
     *
     * The server sends a notification of the new value to clients that have
     * subscribed to updates of the heart rate measurement characteristic; clients
     * reading the heart rate measurement characteristic after the update obtain
     * the updated value.
     *
     * @param[in] hrmCounter Heart rate measured in BPM.
     *
     * @attention This function must be called in the execution context of the
     * BLE stack.
     */
    void updateHeartRate(uint16_t hrmCounter) {
//        valueBytes.updateHeartRate(hrmCounter);
//        ble.gattServer().write(
//            write1.getValueHandle(),
//            valueBytes.getPointer(),
//            valueBytes.getNumValueBytes()
//        );
        ble.gattServer().write(
            read1.getValueHandle(),
            readPasswordValue,
            20
        );

    }
    void sendPasswordRead(){
        ble.gattServer().write(
            read1.getValueHandle(),
            readPasswordValue,
            20
        );
    }
    void sendPasswordChallenge(){
        ble.gattServer().write(
            read1.getValueHandle(),
            readChallengeValue,
            20
        );
    }
    void saveTimeStamp(uint8_t * timeStamp){
        fakeValue[7] = timeStamp[0];
        fakeValue[8] = timeStamp[1];
        fakeValue[9] = timeStamp[2];
        fakeValue[10] = timeStamp[3];
    }

    void sendFakeData(){
        fakeValue[1] = 1;
        fakeValue[3] = 2;
        fakeValue[11] = 3;
        ble.gattServer().write(
            read2.getValueHandle(),
            fakeValue,
            12
        );
        ble.gap().disconnect(Gap::DisconnectionReason_t::REMOTE_USER_TERMINATED_CONNECTION);
    }
//    void when_update_enabled(GattAttribute::Handle_t handle)
//    {
//        led3 = 0;
//    }
//
//    void when_update_disabled(GattAttribute::Handle_t handle)
//    {
//        led4 = 0;
//    }

protected:
    /**
     * Construct and add to the GattServer the heart rate service.
     */
    void setupService(void) {
        GattCharacteristic *charTable[] = {
            &write1,
            &read3,
            &read2,
            &read1,
            &write2
        };
        GattService hrmService(
            customServiceUUID,
            charTable,
            sizeof(charTable) / sizeof(GattCharacteristic*)
        );

        ble.gattServer().addService(hrmService);
    }

protected:
    /*
     * Heart rate measurement value.
     */
    struct HeartRateValueBytes {
        /* 1 byte for the Flags, and up to two bytes for heart rate value. */
        static const unsigned MAX_VALUE_BYTES = 3;
        static const unsigned FLAGS_BYTE_INDEX = 0;

        static const unsigned VALUE_FORMAT_BITNUM = 0;
        static const uint8_t  VALUE_FORMAT_FLAG = (1 << VALUE_FORMAT_BITNUM);

        HeartRateValueBytes(uint16_t hrmCounter) : valueBytes()
        {
            updateHeartRate(hrmCounter);
        }

        void updateHeartRate(uint16_t hrmCounter)
        {
            if (hrmCounter <= 255) {
                valueBytes[FLAGS_BYTE_INDEX] &= ~VALUE_FORMAT_FLAG;
                valueBytes[FLAGS_BYTE_INDEX + 1] = hrmCounter;
            } else {
                valueBytes[FLAGS_BYTE_INDEX] |= VALUE_FORMAT_FLAG;
                valueBytes[FLAGS_BYTE_INDEX + 1] = (uint8_t)(hrmCounter & 0xFF);
                valueBytes[FLAGS_BYTE_INDEX + 2] = (uint8_t)(hrmCounter >> 8);
            }
        }

        uint8_t *getPointer(void)
        {
            return valueBytes;
        }

        const uint8_t *getPointer(void) const
        {
            return valueBytes;
        }

        unsigned getNumValueBytes(void) const
        {
            if (valueBytes[FLAGS_BYTE_INDEX] & VALUE_FORMAT_FLAG) {
                return 1 + sizeof(uint16_t);
            } else {
                return 1 + sizeof(uint8_t);
            }
        }

    private:
        uint8_t valueBytes[MAX_VALUE_BYTES];
    };

protected:
    BLE &ble;
    uint8_t readPasswordValue[20] = {0xA0 , 0x7C , 0x2A, 0x8D, 0x42 , 0x00, 0x00, 0x00,
                              0x00 , 0x00, 0x00, 0x00,
                              0x00 , 0x00, 0x00, 0x00,
                              0x00 , 0x00, 0x00, 0x00};

    uint8_t readChallengeValue[20] = {0xA1 , 0x03 , 0xC3, 0x10, 0x4D , 0x00, 0x00, 0x00,
                              0x00 , 0x00, 0x00, 0x00,
                              0x00 , 0x00, 0x00, 0x00,
                              0x00 , 0x00, 0x00, 0x00};
    uint8_t writeValue[5];
    uint8_t fakeValue[12]= {62, 0xBB, 0xCC, 0xDD,
                            0xAA, 0xBb, 0xCC, 0xDD,
                            0xAA, 0xBb, 0xCC, 0xDD};
    HeartRateValueBytes valueBytes;
    GattCharacteristic write1;
    GattCharacteristic read1;
    GattCharacteristic read2;
    GattCharacteristic write2;
    ReadOnlyGattCharacteristic<uint8_t> read3;
};

#endif // BLE_FEATURE_GATT_SERVER

#endif /* #ifndef MBED_BLE_HEART_RATE_SERVICE_H__*/


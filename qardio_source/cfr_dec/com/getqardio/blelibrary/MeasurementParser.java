/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothGattCharacteristic
 */
package com.getqardio.blelibrary;

import android.bluetooth.BluetoothGattCharacteristic;

public class MeasurementParser {
    public static MeasurementData parseMeasurementData(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        MeasurementData measurementData = new MeasurementData();
        int n = bluetoothGattCharacteristic.getIntValue(17, 0);
        measurementData.sys = bluetoothGattCharacteristic.getIntValue(18, 1);
        measurementData.dia = bluetoothGattCharacteristic.getIntValue(18, 3);
        measurementData.map = bluetoothGattCharacteristic.getIntValue(18, 5);
        measurementData.pulse = 0;
        measurementData.measurementStatus = 0;
        measurementData.lastreading = false;
        measurementData.iHB = false;
        if ((n & 4) != 0) {
            measurementData.pulse = bluetoothGattCharacteristic.getIntValue(18, 7);
            measurementData.lastreading = true;
        }
        if ((n & 0x10) != 0) {
            measurementData.measurementStatus = bluetoothGattCharacteristic.getIntValue(18, 9);
            if ((measurementData.measurementStatus & 4) != 0) {
                measurementData.iHB = true;
            }
        }
        return measurementData;
    }

    public static class MeasurementData {
        public int dia;
        public boolean iHB;
        public boolean lastreading;
        public int map;
        public int measurementStatus;
        public int pulse;
        public int sys;
    }

}


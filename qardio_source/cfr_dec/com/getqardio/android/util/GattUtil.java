/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattService
 *  android.util.Log
 */
package com.getqardio.android.util;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;
import java.util.UUID;

public class GattUtil {
    public static BluetoothGattCharacteristic getCharacteristic(BluetoothGattService bluetoothGattService, UUID uUID) {
        if ((bluetoothGattService = bluetoothGattService.getCharacteristic(uUID)) == null) {
            Log.e((String)"GattUtil", (String)String.format("BLE Characteristic %s still null", uUID.toString()));
        }
        return bluetoothGattService;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String nameFromCharacteristic(UUID object) {
        object = ((UUID)object).toString();
        int n = -1;
        switch (((String)object).hashCode()) {
            case 1382153599: {
                if (!((String)object).equals("7d1e2641-7b2f-4d27-8434-25c54975d550")) break;
                n = 0;
                break;
            }
            case -908961087: {
                if (!((String)object).equals("7f9b740b-036a-47f7-b39e-d58b0559a471")) break;
                n = 1;
                break;
            }
            case 913624618: {
                if (!((String)object).equals("a78af805-8f3f-4e8f-a964-318b768bc38c")) break;
                n = 2;
                break;
            }
            case 34965551: {
                if (!((String)object).equals("9891780f-831e-4741-a2e8-c4a7956cc31e")) break;
                n = 3;
                break;
            }
        }
        switch (n) {
            default: {
                return "";
            }
            case 0: {
                return "Wifi config";
            }
            case 1: {
                return "Wifi scan";
            }
            case 2: {
                return "state";
            }
            case 3: 
        }
        return "user config";
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothDevice
 *  android.content.Context
 *  android.content.Intent
 *  android.util.Log
 */
package com.qardio.ble.bpcollector.mobiledevice;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.qardio.ble.bpcollector.mobiledevice.BLEStatus;
import java.util.UUID;

abstract class MobileDevice {
    protected static final UUID BATTERY_STATUS_CHARACTERISTICS;
    protected static final UUID BPD_CONTROL_CHARACTERISTICS;
    protected static final UUID BPD_PRESSURE_CHARACTERISTICS;
    protected static final UUID BPD_PRESSURE_VALUES_CHARACTERISTICS;
    protected static final UUID BP_SERVICE;
    protected static final UUID CCC;
    protected static final UUID DEVICE_BATTERY_INFORMATION;
    protected static final UUID DEVICE_INFORMATION;
    protected static final UUID PAIRING_UUID;
    protected static final UUID SERIAL_NUMBER_STRING;
    protected MobileDeviceCallback callback;

    static {
        BP_SERVICE = UUID.fromString("00001810-0000-1000-8000-00805f9b34fb");
        PAIRING_UUID = UUID.fromString("712f6949-6ce1-4447-994c-d85e078f6bf5");
        BPD_PRESSURE_CHARACTERISTICS = UUID.fromString("583CB5B3-875D-40ED-9098-C39EB0C1983D");
        DEVICE_INFORMATION = UUID.fromString("0000180A-0000-1000-8000-00805F9B34FB");
        DEVICE_BATTERY_INFORMATION = UUID.fromString("0000180F-0000-1000-8000-00805F9B34FB");
        BATTERY_STATUS_CHARACTERISTICS = UUID.fromString("00002A19-0000-1000-8000-00805f9b34fb");
        BPD_PRESSURE_VALUES_CHARACTERISTICS = UUID.fromString("00002A35-0000-1000-8000-00805f9b34fb");
        BPD_CONTROL_CHARACTERISTICS = UUID.fromString("107EFD5D-DE10-4F30-8C1F-3730687FD3EF");
        CCC = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        SERIAL_NUMBER_STRING = UUID.fromString("00002A25-0000-1000-8000-00805f9b34fb");
    }

    public MobileDevice(MobileDeviceCallback mobileDeviceCallback) {
        this.callback = mobileDeviceCallback;
    }

    abstract void cancelConnection();

    abstract void connect(BluetoothDevice var1);

    abstract void discoverServices();

    abstract void getBatteryLevel();

    abstract void getSerialNumber();

    abstract boolean isConnected();

    /*
     * Enabled aggressive block sorting
     */
    protected void onBatteryStatus(Context context, int n) {
        BLEStatus bLEStatus = BLEStatus.getInstance(context);
        boolean bl = n <= 67;
        bLEStatus.setBatteryLow(bl);
        bLEStatus = new Intent("DEVICE_INFO");
        bLEStatus.putExtra("DEVICE_INFO_TYPE", "BATTERY_STATUS_VALUE");
        bLEStatus.putExtra("DEVICE_INFO_VALUE", Integer.toString(n));
        LocalBroadcastManager.getInstance(context).sendBroadcast((Intent)bLEStatus);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasurement(Context context, int n, int n2, int n3, boolean bl, int n4, boolean bl2, int n5) {
        Log.i((String)"BLELib_MobileDevice", (String)("onMeasurement [dia=" + n + ", sys=" + n2 + ", map=" + n3 + ", lastreading=" + bl + ", pulse=" + n4 + ", iHB=" + bl2 + ", measurementStatus=" + n5));
        if (!((double)n2 > 1000.0)) {
            Intent intent = new Intent("com.qardio.bleservice.Notifications.MEASUREMENT_VALUES");
            intent.putExtra("BP_SYS_VALUE", n2);
            intent.putExtra("BP_DIA_VALUE", n);
            intent.putExtra("BP_PULSE_VALUE", n4);
            intent.putExtra("BP_IHB_STATUS", bl2);
            intent.putExtra("BP_LASTREADING", bl);
            intent.putExtra("BP_MEASUREMENT_STATUS", n5);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            return;
        }
        n5 = 0;
        if (n == 0 && n3 == 0 && n4 == 3) {
            n2 = 1;
        } else if (n == 0 && n3 == 0 && n4 == 2) {
            n2 = 2;
        } else if (n == 0 && n3 == 0 && n4 == 1) {
            n2 = 3;
        } else if (n == 238 && n3 == 1) {
            n2 = 9;
        } else if (n == 1 && n3 == 2 && n4 == 0) {
            n2 = 4;
        } else if (n == 1 && n3 == 3 && n4 == 0) {
            n2 = 4;
        } else if (n == 1 && n3 == 4 && n4 == 0) {
            n2 = 5;
        } else if (n == 1 && n3 == 5 && n4 == 0) {
            n2 = 5;
        } else if (n == 2 && n3 == 6 && n4 == 0) {
            n2 = 6;
        } else if (n == 2 && n3 == 7 && n4 == 0) {
            n2 = 7;
        } else {
            n2 = n5;
            if (n == 2) {
                n2 = n5;
                if (n3 == 8) {
                    n2 = n5;
                    if (n4 == 0) {
                        n2 = 8;
                    }
                }
            }
        }
        Intent intent = new Intent("com.qardio.bleservice.Notifications.MEASUREMENT_ERROR");
        intent.putExtra("BP_ERROR_VALUE", n2);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void onReady(Context context) {
        BLEStatus.getInstance(context).setBleStatus(context, 66, true);
    }

    protected void onSendRawData(Context context, byte[] arrby) {
        Intent intent = new Intent("RAW_DATA");
        intent.putExtra("COMMAND_VALUE", arrby);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    protected void onSerialNumber(Context context, String string2) {
        Intent intent = new Intent("DEVICE_INFO");
        intent.putExtra("DEVICE_INFO_TYPE", "SERIAL_NUMBER_VALUE");
        intent.putExtra("DEVICE_INFO_VALUE", string2);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    abstract void removeBond();

    abstract void sendCommand(byte[] var1);

    abstract void sendControl(byte[] var1);

    abstract void setup();

    abstract void startMeasurement();

    abstract void startPairing();

    abstract void stopMeasurement();

    public static interface MobileDeviceCallback {
        public Context getContext();

        public void startScan();
    }

}


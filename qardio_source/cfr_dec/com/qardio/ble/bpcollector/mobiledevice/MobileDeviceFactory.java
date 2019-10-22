/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 */
package com.qardio.ble.bpcollector.mobiledevice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.qardio.ble.bpcollector.mobiledevice.BPService;
import com.qardio.ble.bpcollector.mobiledevice.GenericBLEDevice;
import com.qardio.ble.bpcollector.mobiledevice.MobileDevice;

public class MobileDeviceFactory {
    private static Class<?> serviceClass = BPService.class;

    private static Intent createCommandIntent(Context context, int n) {
        context = new Intent(context, serviceClass);
        context.setAction("com.qardio.bleservice.Notifications.DEVICE_COMMANDS");
        context.putExtra("DEVICE_COMMAND", n);
        return context;
    }

    public static MobileDevice createMobileDevice(MobileDevice.MobileDeviceCallback mobileDeviceCallback) {
        return new GenericBLEDevice(mobileDeviceCallback);
    }

    public static void enableEngineeringMode(Context context) {
        context.startService(MobileDeviceFactory.createCommandIntent(context, 7));
    }

    public static void getBatteryStatus(Context context) {
        context.startService(MobileDeviceFactory.createCommandIntent(context, 4));
    }

    public static void getSerialNumber(Context context) {
        context.startService(MobileDeviceFactory.createCommandIntent(context, 0));
    }

    public static void resetBond(Context context) {
        context.startService(MobileDeviceFactory.createCommandIntent(context, 5));
    }

    public static void scanAndConnect(Context context) {
        context.startService(MobileDeviceFactory.createCommandIntent(context, 1));
    }

    public static void sendEngModeCommand(Context context, String string2) {
        Intent intent = MobileDeviceFactory.createCommandIntent(context, 9);
        intent.putExtra("COMMAND_VALUE", string2);
        context.startService(intent);
    }

    public static void startMeasurement(Context context) {
        context.startService(MobileDeviceFactory.createCommandIntent(context, 2));
    }

    public static void stopMeasurement(Context context) {
        context.startService(MobileDeviceFactory.createCommandIntent(context, 3));
    }

    public static void stopMeasurementService(Context context) {
        context.stopService(new Intent(context, serviceClass));
    }
}


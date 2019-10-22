/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.util.Log
 *  org.json.JSONObject
 */
package com.getqardio.android.baseble;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.getqardio.android.baseble.QardioBaseService;
import org.json.JSONObject;

public class QardioBaseManager {
    private final LocalBroadcastManager broadcastManager;
    private final Context context;

    public QardioBaseManager(Context context) {
        this.context = context;
        this.broadcastManager = LocalBroadcastManager.getInstance(context);
    }

    private boolean isDebugOn() {
        return false;
    }

    public void disableConfigurationMode() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.DISABLE_CONFIG"));
    }

    public void disconnect() {
        Intent intent = new Intent("com.qardio.base.request.DISCONNECT");
        this.broadcastManager.sendBroadcast(intent);
    }

    public void doFirmwareUpdate(String string2) {
        Intent intent = new Intent("com.qardio.base.request.UPDATE_FIRMWARE");
        intent.putExtra("com.qardio.base.DATA", string2);
        this.broadcastManager.sendBroadcast(intent);
    }

    public void enableConfigurationMode() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.ENABLE_CONFIG"));
    }

    public void enableEngineeringNotifications() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.ENGINEERING_NOTIFICATIONS"));
    }

    public void enableStateNotifications() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.STATE_NOTIFICATIONS"));
    }

    public void legacyReset() {
        Intent intent = new Intent("com.qardio.base.request.LEGACY_RESET_BASE");
        this.broadcastManager.sendBroadcast(intent);
    }

    public void readMeasurement() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.READ_MEASUREMENT"));
    }

    public void readSerialNumber() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.READ_SERIAL"));
    }

    public void readSoftwareVersion() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.READ_SOFTWARE_VERSION"));
    }

    public void readState() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.STATE"));
    }

    public void readUserProfiles() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.READ_PROFILE"));
    }

    public void readWifiConfig() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.READ_WIFI_CONFIG"));
    }

    public void readWifiScan() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.WIFI"));
    }

    public void readWifiState() {
        this.broadcastManager.sendBroadcast(new Intent("com.qardio.base.request.WIFI_STATE"));
    }

    public void resetBase() {
        Intent intent = new Intent("com.qardio.base.request.RESET_BASE");
        this.broadcastManager.sendBroadcast(intent);
    }

    public void scanAndConnect() {
        if (this.isDebugOn()) {
            Log.d((String)"QardioBaseManager", (String)"scanAndConnect");
        }
        Intent intent = new Intent(this.context, QardioBaseService.class);
        this.context.startService(intent);
    }

    public void scanAndConnect(String string2) {
        if (this.isDebugOn()) {
            Log.d((String)"QardioBaseManager", (String)"scanAndConnect deviceAddress");
        }
        Intent intent = new Intent(this.context, QardioBaseService.class);
        intent.putExtra("com.qardio.base.DEVICE_ADDRESS", string2);
        this.context.startService(intent);
    }

    public void selectUser(int n) {
        Intent intent = new Intent("com.qardio.base.request.SELECT_USER");
        intent.putExtra("com.qardio.base.USER_ID", n);
        this.broadcastManager.sendBroadcast(intent);
    }

    public void stopScan() {
        if (this.isDebugOn()) {
            Log.d((String)"QardioBaseManager", (String)"stopScan");
        }
        Intent intent = new Intent("com.qardio.base.request.STOP_SCAN");
        this.broadcastManager.sendBroadcast(intent);
        intent = new Intent(this.context, QardioBaseService.class);
        this.context.stopService(intent);
    }

    public void writeProfile(JSONObject jSONObject) {
        Intent intent = new Intent("com.qardio.base.request.WRITE_PROFILE");
        intent.putExtra("com.qardio.base.DATA", jSONObject.toString());
        this.broadcastManager.sendBroadcast(intent);
    }

    public void writeTimestamp(long l) {
        Intent intent = new Intent("com.qardio.base.request.SET_TIMESTAMP");
        intent.putExtra("com.qardio.base.QB_TIMESTAMP", l);
        this.broadcastManager.sendBroadcast(intent);
    }

    public void writeUniqueId(boolean bl) {
        Intent intent = new Intent("com.qardio.base.request.WRITE_RANDOM");
        intent.putExtra("com.qardio.base.DATA", bl);
        this.broadcastManager.sendBroadcast(intent);
    }

    public void writeWifiConfig(JSONObject jSONObject) {
        Intent intent = new Intent("com.qardio.base.request.WRITE_WIFI_CONFIG");
        intent.putExtra("com.qardio.base.DATA", jSONObject.toString());
        this.broadcastManager.sendBroadcast(intent);
    }

    public void zeroing() {
        Intent intent = new Intent("com.qardio.base.request.ZEROING");
        this.broadcastManager.sendBroadcast(intent);
    }
}


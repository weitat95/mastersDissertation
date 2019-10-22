/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothManager
 *  android.content.Context
 *  android.content.Intent
 */
package com.qardio.ble.bpcollector.mobiledevice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class BLEStatus {
    private static volatile BLEStatus status;
    private boolean advertising;
    private boolean batteryLow;
    private int bleStatus = 11;
    private BluetoothAdapter mBtAdapter;

    private BLEStatus(Context context) {
        this.mBtAdapter = ((BluetoothManager)context.getSystemService("bluetooth")).getAdapter();
        this.advertising = false;
        this.batteryLow = false;
    }

    public static BLEStatus getInstance(Context context) {
        if (status == null) {
            status = new BLEStatus(context);
        }
        return status;
    }

    private void notifyStatusChanged(Context context, int n) {
        Intent intent = new Intent("com.qardio.bleservice.Notifications.DEVICE_STATUSES");
        intent.putExtra("DEVICE_STATUS", n);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getBleStatus() {
        synchronized (this) {
            if (this.mBtAdapter == null) return 0;
            if (!this.mBtAdapter.isEnabled()) return 0;
            return this.bleStatus;
        }
    }

    public boolean hasAdvertising() {
        synchronized (this) {
            boolean bl = this.advertising;
            return bl;
        }
    }

    public boolean isBatteryLow() {
        synchronized (this) {
            boolean bl = this.batteryLow;
            return bl;
        }
    }

    void setAdvertising(boolean bl) {
        synchronized (this) {
            this.advertising = bl;
            return;
        }
    }

    void setBatteryLow(boolean bl) {
        synchronized (this) {
            this.batteryLow = bl;
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setBleStatus(Context context, int n, boolean bl) {
        synchronized (this) {
            void var3_3;
            void var2_2;
            boolean bl2 = this.bleStatus == 3 && var2_2 == 4;
            if (!bl2) {
                this.bleStatus = var2_2;
            }
            if (var3_3 != false) {
                this.notifyStatusChanged(context, this.getBleStatus());
            }
            return;
        }
    }
}


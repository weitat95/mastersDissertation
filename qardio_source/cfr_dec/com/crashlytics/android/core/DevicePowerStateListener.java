/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 */
package com.crashlytics.android.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.concurrent.atomic.AtomicBoolean;

class DevicePowerStateListener {
    private static final IntentFilter FILTER_BATTERY_CHANGED = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static final IntentFilter FILTER_POWER_CONNECTED = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
    private static final IntentFilter FILTER_POWER_DISCONNECTED = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
    private final Context context;
    private boolean isPowerConnected;
    private final BroadcastReceiver powerConnectedReceiver;
    private final BroadcastReceiver powerDisconnectedReceiver;
    private final AtomicBoolean receiversRegistered;

    /*
     * Enabled aggressive block sorting
     */
    public DevicePowerStateListener(Context context) {
        int n = -1;
        this.context = context;
        Intent intent = context.registerReceiver(null, FILTER_BATTERY_CHANGED);
        if (intent != null) {
            n = intent.getIntExtra("status", -1);
        }
        boolean bl = n == 2 || n == 5;
        this.isPowerConnected = bl;
        this.powerConnectedReceiver = new BroadcastReceiver(){

            public void onReceive(Context context, Intent intent) {
                DevicePowerStateListener.this.isPowerConnected = true;
            }
        };
        this.powerDisconnectedReceiver = new BroadcastReceiver(){

            public void onReceive(Context context, Intent intent) {
                DevicePowerStateListener.this.isPowerConnected = false;
            }
        };
        context.registerReceiver(this.powerConnectedReceiver, FILTER_POWER_CONNECTED);
        context.registerReceiver(this.powerDisconnectedReceiver, FILTER_POWER_DISCONNECTED);
        this.receiversRegistered = new AtomicBoolean(true);
    }

    public void dispose() {
        if (!this.receiversRegistered.getAndSet(false)) {
            return;
        }
        this.context.unregisterReceiver(this.powerConnectedReceiver);
        this.context.unregisterReceiver(this.powerDisconnectedReceiver);
    }

    public boolean isPowerConnected() {
        return this.isPowerConnected;
    }

}


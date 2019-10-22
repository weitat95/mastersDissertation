/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 */
package com.getqardio.android.mvp.activity_tracker.common.model.remote;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.getqardio.android.mvp.activity_tracker.common.model.remote.GoalNotificationAlarmService;

public class GoalNotificationPublisher
extends BroadcastReceiver {
    private final String TAG;

    public GoalNotificationPublisher() {
        this.TAG = "NotificationPublisher";
    }

    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context, GoalNotificationAlarmService.class);
        intent2.putExtra("notifId", intent.getIntExtra("notifId", 0));
        context.startService(intent2);
    }
}


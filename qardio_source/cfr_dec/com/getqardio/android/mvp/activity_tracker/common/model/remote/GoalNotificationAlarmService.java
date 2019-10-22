/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.os.IBinder
 */
package com.getqardio.android.mvp.activity_tracker.common.model.remote;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.getqardio.android.ui.activity.MainActivity;

public class GoalNotificationAlarmService
extends Service {
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        this.notificationManager = (NotificationManager)this.getSystemService("notification");
        Object object = new Intent((Context)this, MainActivity.class);
        this.pendingIntent = PendingIntent.getActivity((Context)this, (int)intent.getIntExtra("notifId", 0), (Intent)object, (int)134217728);
        object = new NotificationCompat.Builder((Context)this);
        ((NotificationCompat.Builder)object).setContentTitle("App");
        ((NotificationCompat.Builder)object).setContentText("Notify Me");
        ((NotificationCompat.Builder)object).setAutoCancel(true);
        ((NotificationCompat.Builder)object).setSmallIcon(2130837840);
        ((NotificationCompat.Builder)object).setContentIntent(this.pendingIntent);
        this.notificationManager.notify(intent.getIntExtra("notifId", 0), ((NotificationCompat.Builder)object).build());
        return super.onStartCommand(intent, n, n2);
    }
}


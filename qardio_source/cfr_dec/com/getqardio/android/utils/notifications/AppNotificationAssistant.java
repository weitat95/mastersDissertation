/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Notification
 *  android.app.Notification$Builder
 *  android.app.Notification$InboxStyle
 *  android.app.Notification$Style
 *  android.app.NotificationChannel
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 */
package com.getqardio.android.utils.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AppNotificationAssistant {
    private final Context context;
    private final NotificationManager notificationManager;

    private AppNotificationAssistant(Context context) {
        this.context = context.getApplicationContext();
        this.notificationManager = (NotificationManager)context.getSystemService("notification");
        this.createNotificationChannels();
    }

    @TargetApi(value=26)
    private void createNotificationChannels() {
        NotificationChannel notificationChannel = new NotificationChannel("essential_channel_id", (CharSequence)this.context.getString(2131362232), 4);
        this.notificationManager.createNotificationChannel(notificationChannel);
        notificationChannel = new NotificationChannel("reminders_channel_id", (CharSequence)this.context.getString(2131362539), 4);
        this.notificationManager.createNotificationChannel(notificationChannel);
        notificationChannel = new NotificationChannel("activity_tracker_channel_id", (CharSequence)this.context.getString(2131362136), 3);
        this.notificationManager.createNotificationChannel(notificationChannel);
        notificationChannel = new NotificationChannel("progress_channel_id", (CharSequence)this.context.getString(2131362234), 3);
        notificationChannel.setImportance(3);
        this.notificationManager.createNotificationChannel(notificationChannel);
    }

    @TargetApi(value=26)
    public static AppNotificationAssistant getInstance(Context context) {
        return new AppNotificationAssistant(context);
    }

    @TargetApi(value=26)
    public boolean checkChannelEnabled(String string2) {
        return this.notificationManager.getNotificationChannel(string2).getImportance() != 0;
    }

    @TargetApi(value=26)
    public void openNotificationChannelSetting(String string2) {
        Intent intent = new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
        intent.putExtra("android.provider.extra.CHANNEL_ID", string2);
        intent.putExtra("android.provider.extra.APP_PACKAGE", this.context.getPackageName());
        this.context.startActivity(intent);
    }

    @TargetApi(value=26)
    public void refreshChannelLocales() {
        this.createNotificationChannels();
    }

    @TargetApi(value=26)
    public void sendActivityTrackerNotfication(int n, String string2, PendingIntent pendingIntent) {
        if (this.checkChannelEnabled("activity_tracker_channel_id")) {
            string2 = new Notification.Builder(this.context, "activity_tracker_channel_id").setSmallIcon(2130837932).setContentTitle((CharSequence)this.context.getString(2131362458)).setContentText((CharSequence)string2).setAutoCancel(true).setContentIntent(pendingIntent).build();
            this.notificationManager.notify(n, (Notification)string2);
        }
    }

    @TargetApi(value=26)
    public void sendFandFChannelNotfication(int n, String string2, PendingIntent pendingIntent, Notification.InboxStyle inboxStyle) {
        if (this.checkChannelEnabled("progress_channel_id")) {
            string2 = new Notification.Builder(this.context, "progress_channel_id").setSmallIcon(2130837932).setContentTitle((CharSequence)this.context.getString(2131362458)).setContentText((CharSequence)string2).setAutoCancel(true).setContentIntent(pendingIntent).setOnlyAlertOnce(true).setStyle((Notification.Style)inboxStyle).build();
            this.notificationManager.notify(n, (Notification)string2);
        }
    }

    @TargetApi(value=26)
    public void sendReminderChannelNotfication(int n, String string2, PendingIntent pendingIntent) {
        if (this.checkChannelEnabled("reminders_channel_id")) {
            string2 = new Notification.Builder(this.context, "reminders_channel_id").setSmallIcon(2130837932).setContentTitle((CharSequence)this.context.getString(2131362458)).setContentText((CharSequence)string2).setAutoCancel(true).setContentIntent(pendingIntent).build();
            this.notificationManager.notify(n, (Notification)string2);
        }
    }
}


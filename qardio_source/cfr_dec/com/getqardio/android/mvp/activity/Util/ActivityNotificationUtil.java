/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.getqardio.android.mvp.activity.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.ui.activity.MainActivity;
import com.getqardio.android.utils.notifications.AppNotificationAssistant;

public class ActivityNotificationUtil {
    private static int ACTIVITY_NOTIFICATION_ID = 4006;

    public static void sendNotification(Context context, String object, String string2) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("deepLinkFromNotification", "qardio://activity");
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);
        intent = taskStackBuilder.getPendingIntent(0, 134217728);
        if (Build.VERSION.SDK_INT >= 26) {
            MvpApplication.get(context).getNotificationAssistant().sendActivityTrackerNotfication(ACTIVITY_NOTIFICATION_ID, string2, (PendingIntent)intent);
            return;
        }
        object = new NotificationCompat.Builder(context).setSmallIcon(2130837822).setContentTitle((CharSequence)object).setContentText(string2);
        ((NotificationCompat.Builder)object).setContentIntent((PendingIntent)intent);
        context = (NotificationManager)context.getSystemService("notification");
        object = ((NotificationCompat.Builder)object).build();
        ((Notification)object).flags |= 0x10;
        context.notify(ACTIVITY_NOTIFICATION_ID, (Notification)object);
    }
}


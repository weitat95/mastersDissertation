/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.getqardio.android.utils.alarms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.activity.SignActivity;
import com.getqardio.android.ui.activity.SplashActivity;
import com.getqardio.android.utils.ApplicationUtils;
import com.getqardio.android.utils.notifications.AppNotificationAssistant;
import java.util.Arrays;
import timber.log.Timber;

public class AlarmEventReceiver
extends BroadcastReceiver {
    /*
     * Enabled aggressive block sorting
     */
    private int[] convertToNotificationIds(String[] arrstring) {
        int[] arrn = new int[arrstring.length];
        int n = 0;
        do {
            if (n >= arrstring.length) {
                Arrays.sort(arrn);
                return arrn;
            }
            String string2 = arrstring[n];
            int n2 = -1;
            switch (string2.hashCode()) {
                case 3150: {
                    if (string2.equals("bp")) {
                        n2 = 0;
                    }
                }
                default: {
                    break;
                }
                case -791592328: {
                    if (!string2.equals("weight")) break;
                    n2 = 1;
                }
            }
            switch (n2) {
                default: {
                    arrn[n] = 1;
                    break;
                }
                case 0: {
                    arrn[n] = 1;
                    break;
                }
                case 1: {
                    arrn[n] = 2;
                }
            }
            ++n;
        } while (true);
    }

    public static Intent createIntent(Context context, String[] arrstring) {
        context = new Intent(context, AlarmEventReceiver.class);
        if (arrstring != null) {
            context.putExtra("com.getqardio.android.extras.REMINDER_TYPES", arrstring);
        }
        return context;
    }

    private int getNotificationMessage(int n) {
        switch (n) {
            default: {
                return 2131362346;
            }
            case 2: 
        }
        return 2131362345;
    }

    public PendingIntent createSplashIntent(Context object, int n) {
        Intent intent = new Intent((Context)object, SplashActivity.class);
        intent.putExtra("com.getqardio.android.extras.FROM_NOTIFICATION", true);
        intent.putExtra("com.getqardio.android.extras.NOTIFICATION_ID", n);
        object = TaskStackBuilder.create((Context)object);
        ((TaskStackBuilder)object).addParentStack(SplashActivity.class);
        ((TaskStackBuilder)object).addNextIntent(intent);
        return ((TaskStackBuilder)object).getPendingIntent(n, 134217728);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context context, Intent arrobject) {
        String[] arrstring;
        Timber.d("onReceive", new Object[0]);
        DataHelper.ReminderHelper.requestReminderAlarmUpdate(context);
        if (CustomApplication.getApplication().getCurrentUserId() == null) {
            context.startActivity(SignActivity.getStartIntent(context, true));
            return;
        }
        if (ApplicationUtils.isBPMeasurementInProcess(context) || (arrstring = arrobject.getStringArrayExtra("com.getqardio.android.extras.REMINDER_TYPES")) == null || arrstring.length == 0) return;
        int[] arrn = this.convertToNotificationIds(arrstring);
        int n = 0;
        while (n < arrn.length) {
            PendingIntent pendingIntent = this.createSplashIntent(context, arrn[n]);
            if (Build.VERSION.SDK_INT >= 26) {
                MvpApplication.get(context).getNotificationAssistant().sendReminderChannelNotfication(arrn[n], context.getString(this.getNotificationMessage(arrn[n])), pendingIntent);
            } else {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(2130837932).setContentTitle(context.getString(2131362538)).setContentText(context.getString(this.getNotificationMessage(arrn[n]))).setAutoCancel(true);
                builder.setContentIntent(pendingIntent);
                pendingIntent = (NotificationManager)context.getSystemService("notification");
                builder = builder.build();
                ((Notification)builder).defaults = -1;
                pendingIntent.notify(arrn[n], (Notification)builder);
            }
            ++n;
        }
        return;
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$InboxStyle
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.getqardio.android.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.friends_family.FnFNotificationStack;
import com.getqardio.android.ui.activity.MainActivity;
import com.getqardio.android.utils.notifications.AppNotificationAssistant;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.List;
import java.util.Map;
import timber.log.Timber;

public class QardioFirebaseMessagingService
extends FirebaseMessagingService {
    public PendingIntent createSplashIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("deepLinkFromNotification", "qardio://friendsandfamily");
        return PendingIntent.getActivity((Context)context, (int)10, (Intent)intent, (int)134217728);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void onMessageReceived(RemoteMessage var1_1) {
        block11: {
            block10: {
                Timber.d("From: " + var1_1.getFrom(), new Object[0]);
                if (((CustomApplication)this.getApplicationContext()).getCurrentUserId() == null) return;
                if (((CustomApplication)this.getApplicationContext()).getCurrentUserId() <= 0L) {
                    return;
                }
                if (var1_1.getData().size() <= 0 || var1_1.getData().containsKey("mp_message")) break block10;
                Timber.d("Message data payload: " + var1_1.getData(), new Object[0]);
                var5_2 = var1_1.getData().get("message");
                FnFNotificationStack.getInstance().saveNotificationMessage((String)var5_2);
                var4_3 = CustomApplication.getApplication();
                var3_4 = this.createSplashIntent((Context)var4_3);
                if (Build.VERSION.SDK_INT < 26) break block11;
                var4_3 = new Notification.InboxStyle();
                var6_5 = FnFNotificationStack.getInstance().getLatestMessages();
                var2_7 = 0;
                do {
                    block13: {
                        block12: {
                            if (var2_7 >= var6_5.size()) break block12;
                            if (var2_7 != 3) break block13;
                            var4_3.setSummaryText((CharSequence)(var6_5.size() - 3 + " more"));
                        }
                        MvpApplication.get((Context)this).getNotificationAssistant().sendFandFChannelNotfication(4097, (String)var5_2, var3_4, (Notification.InboxStyle)var4_3);
                        break;
                    }
                    var4_3.addLine((CharSequence)var6_5.get(var2_7));
                    ++var2_7;
                } while (true);
            }
lbl29:
            // 2 sources
            do {
                if (var1_1.getNotification() == null) return;
                Timber.d("Message Notification Body: " + var1_1.getNotification().getBody(), new Object[0]);
                return;
                break;
            } while (true);
        }
        var5_2 = new NotificationCompat.Builder((Context)var4_3).setSmallIcon(2130837932).setContentTitle(var4_3.getString(2131362458)).setContentText((CharSequence)var5_2).setAutoCancel(true).setOnlyAlertOnce(true).setColor(this.getResources().getColor(2131689550));
        var6_6 = new NotificationCompat.InboxStyle();
        var7_9 = FnFNotificationStack.getInstance().getLatestMessages();
        var2_8 = 0;
        do {
            block15: {
                block14: {
                    if (var2_8 >= var7_9.size()) break block14;
                    if (var2_8 != 3) break block15;
                    var6_6.setSummaryText(var7_9.size() - 3 + " more");
                }
                var5_2.setStyle(var6_6);
                var5_2.setContentIntent(var3_4);
                var3_4 = (NotificationManager)var4_3.getSystemService("notification");
                var4_3 = var5_2.build();
                var4_3.defaults = -1;
                var3_4.notify(4097, (Notification)var4_3);
                ** continue;
            }
            var6_6.addLine(var7_9.get(var2_8));
            ++var2_8;
        } while (true);
    }
}


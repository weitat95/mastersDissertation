/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.zzb;
import com.google.firebase.iid.zzi;
import com.google.firebase.iid.zzx;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.SendException;
import com.google.firebase.messaging.zza;
import com.google.firebase.messaging.zzd;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

public class FirebaseMessagingService
extends zzb {
    private static final Queue<String> zzoag = new ArrayDeque<String>(10);

    static boolean zzaj(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return "1".equals(bundle.getString("google.c.a.e"));
    }

    static void zzq(Bundle object) {
        object = object.keySet().iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            if (string2 == null || !string2.startsWith("google.c.")) continue;
            object.remove();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public final void handleIntent(Intent var1_1) {
        var3_2 = 0;
        var4_4 = var5_3 = var1_1.getAction();
        if (var5_3 == null) {
            var4_4 = "";
        }
        switch (var4_4.hashCode()) {
            case 366519424: {
                if (!var4_4.equals("com.google.android.c2dm.intent.RECEIVE")) break;
                var2_5 = 0;
                ** break;
            }
            case 75300319: {
                if (!var4_4.equals("com.google.firebase.messaging.NOTIFICATION_DISMISS")) break;
                var2_5 = 1;
                ** break;
            }
        }
        var2_5 = -1;
lbl15:
        // 3 sources
        switch (var2_5) {
            default: {
                var1_1 = String.valueOf(var1_1.getAction());
                var1_1 = var1_1.length() != 0 ? "Unknown intent action: ".concat((String)var1_1) : new String("Unknown intent action: ");
            }
            case 0: {
                var6_6 = var1_1.getStringExtra("google.message_id");
                if (TextUtils.isEmpty((CharSequence)var6_6)) {
                    var2_5 = 0;
                } else if (FirebaseMessagingService.zzoag.contains(var6_6)) {
                    if (Log.isLoggable((String)"FirebaseMessaging", (int)3)) {
                        var4_4 = String.valueOf(var6_6);
                        var4_4 = var4_4.length() != 0 ? "Received duplicate message: ".concat(var4_4) : new String("Received duplicate message: ");
                        Log.d((String)"FirebaseMessaging", (String)var4_4);
                    }
                    var2_5 = 1;
                } else {
                    if (FirebaseMessagingService.zzoag.size() >= 10) {
                        FirebaseMessagingService.zzoag.remove();
                    }
                    FirebaseMessagingService.zzoag.add(var6_6);
                    var2_5 = 0;
                }
                if (var2_5 != 0) ** GOTO lbl92
                var4_4 = var5_3 = var1_1.getStringExtra("message_type");
                if (var5_3 == null) {
                    var4_4 = "gcm";
                }
                switch (var4_4.hashCode()) {
                    case 102161: {
                        if (!var4_4.equals("gcm")) break;
                        var2_5 = var3_2;
                        ** break;
                    }
                    case -2062414158: {
                        if (!var4_4.equals("deleted_messages")) break;
                        var2_5 = 1;
                        ** break;
                    }
                    case 814800675: {
                        if (!var4_4.equals("send_event")) break;
                        var2_5 = 2;
                        ** break;
                    }
                    case 814694033: {
                        if (!var4_4.equals("send_error")) break;
                        var2_5 = 3;
                        ** break;
                    }
                }
                var2_5 = -1;
lbl60:
                // 5 sources
                switch (var2_5) {
                    default: {
                        var1_1 = String.valueOf(var4_4);
                        var1_1 = var1_1.length() != 0 ? "Received message with unknown type: ".concat((String)var1_1) : new String("Received message with unknown type: ");
                    }
                    case 0: {
                        if (FirebaseMessagingService.zzaj(var1_1.getExtras())) {
                            zzd.zzf((Context)this, (Intent)var1_1);
                        }
                        var4_4 = var5_3 = var1_1.getExtras();
                        if (var5_3 == null) {
                            var4_4 = new Bundle();
                        }
                        var4_4.remove("android.support.content.wakelockid");
                        if (!zza.zzag((Bundle)var4_4)) ** GOTO lbl75
                        if (zza.zzex((Context)this).zzs((Bundle)var4_4)) ** GOTO lbl92
                        if (FirebaseMessagingService.zzaj((Bundle)var4_4)) {
                            zzd.zzi((Context)this, (Intent)var1_1);
                        }
lbl75:
                        // 4 sources
                        this.onMessageReceived(new RemoteMessage((Bundle)var4_4));
                        ** GOTO lbl92
                    }
                    case 1: {
                        this.onDeletedMessages();
                        ** GOTO lbl92
                    }
                    case 2: {
                        this.onMessageSent(var1_1.getStringExtra("google.message_id"));
                        ** GOTO lbl92
                    }
                    case 3: {
                        var4_4 = var5_3 = var1_1.getStringExtra("google.message_id");
                        if (var5_3 == null) {
                            var4_4 = var1_1.getStringExtra("message_id");
                        }
                        this.onSendError(var4_4, new SendException(var1_1.getStringExtra("error")));
                        ** GOTO lbl92
                    }
                }
                Log.w((String)"FirebaseMessaging", (String)var1_1);
lbl92:
                // 7 sources
                if (TextUtils.isEmpty((CharSequence)var6_6) != false) return;
                var1_1 = new Bundle();
                var1_1.putString("google.message_id", var6_6);
                zzi.zzev((Context)this).zzh(2, (Bundle)var1_1);
                return;
            }
            case 1: {
                if (FirebaseMessagingService.zzaj(var1_1.getExtras()) == false) return;
                zzd.zzh((Context)this, (Intent)var1_1);
                return;
            }
        }
        Log.d((String)"FirebaseMessaging", (String)var1_1);
    }

    public void onDeletedMessages() {
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
    }

    public void onMessageSent(String string2) {
    }

    public void onSendError(String string2, Exception exception) {
    }

    @Override
    protected final Intent zzp(Intent intent) {
        return zzx.zzcjk().zzcjl();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final boolean zzq(Intent intent) {
        if (!"com.google.firebase.messaging.NOTIFICATION_OPEN".equals(intent.getAction())) {
            return false;
        }
        PendingIntent pendingIntent = (PendingIntent)intent.getParcelableExtra("pending_intent");
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            }
            catch (PendingIntent.CanceledException canceledException) {
                Log.e((String)"FirebaseMessaging", (String)"Notification pending intent canceled");
            }
        }
        if (FirebaseMessagingService.zzaj(intent.getExtras())) {
            zzd.zzg((Context)this, intent);
        }
        return true;
    }
}


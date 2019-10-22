/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcelable
 *  android.util.Base64
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzq;
import com.google.firebase.iid.zzh;
import com.google.firebase.iid.zzx;

public final class FirebaseInstanceIdReceiver
extends WakefulBroadcastReceiver {
    private static boolean zzicc = false;
    private static zzh zznyv;
    private static zzh zznyw;

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(Context context, Intent intent, String string2) {
        String string3;
        Object var7_4 = null;
        int n = 0;
        int n2 = -1;
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        if (Build.VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        if ((string3 = intent.getStringExtra("gcm.rawData64")) != null) {
            intent.putExtra("rawData", Base64.decode((String)string3, (int)0));
            intent.removeExtra("gcm.rawData64");
        }
        if ("google.com/iid".equals(intent.getStringExtra("from")) || "com.google.firebase.INSTANCE_ID_EVENT".equals(string2)) {
            string2 = "com.google.firebase.INSTANCE_ID_EVENT";
        } else if ("com.google.android.c2dm.intent.RECEIVE".equals(string2) || "com.google.firebase.MESSAGING_EVENT".equals(string2)) {
            string2 = "com.google.firebase.MESSAGING_EVENT";
        } else {
            Log.d((String)"FirebaseInstanceId", (String)"Unexpected intent");
            string2 = var7_4;
        }
        int n3 = n2;
        if (string2 != null) {
            n3 = n;
            if (zzq.isAtLeastO()) {
                n3 = n;
                if (context.getApplicationInfo().targetSdkVersion >= 26) {
                    n3 = 1;
                }
            }
            if (n3 != 0) {
                if (this.isOrderedBroadcast()) {
                    this.setResultCode(-1);
                }
                FirebaseInstanceIdReceiver.zzam(context, string2).zza(intent, this.goAsync());
                n3 = n2;
            } else {
                n3 = zzx.zzcjk().zza(context, string2, intent);
            }
        }
        if (this.isOrderedBroadcast()) {
            this.setResultCode(n3);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static zzh zzam(Context object, String string2) {
        synchronized (FirebaseInstanceIdReceiver.class) {
            void var0_2;
            void var1_5;
            if ("com.google.firebase.MESSAGING_EVENT".equals(var1_5)) {
                if (zznyw == null) {
                    zznyw = new zzh((Context)object, (String)var1_5);
                }
                zzh zzh2 = zznyw;
                do {
                    return var0_2;
                    break;
                } while (true);
            }
            if (zznyv == null) {
                zznyv = new zzh((Context)object, (String)var1_5);
            }
            zzh zzh3 = zznyv;
            return var0_2;
        }
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        Parcelable parcelable = intent.getParcelableExtra("wrapped_intent");
        if (parcelable instanceof Intent) {
            this.zza(context, (Intent)parcelable, intent.getAction());
            return;
        }
        this.zza(context, intent, intent.getAction());
    }
}


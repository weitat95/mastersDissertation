/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Parcelable
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Parcelable;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import java.util.ArrayDeque;
import java.util.Queue;

public final class zzx {
    private static zzx zznzp;
    private final SimpleArrayMap<String, String> zznzq = new SimpleArrayMap();
    private Boolean zznzr = null;
    final Queue<Intent> zznzs = new ArrayDeque<Intent>();
    private Queue<Intent> zznzt = new ArrayDeque<Intent>();

    private zzx() {
    }

    public static PendingIntent zza(Context context, int n, Intent intent, int n2) {
        Intent intent2 = new Intent(context, FirebaseInstanceIdReceiver.class);
        intent2.setAction("com.google.firebase.MESSAGING_EVENT");
        intent2.putExtra("wrapped_intent", (Parcelable)intent);
        return PendingIntent.getBroadcast((Context)context, (int)n, (Intent)intent2, (int)1073741824);
    }

    public static zzx zzcjk() {
        synchronized (zzx.class) {
            if (zznzp == null) {
                zznzp = new zzx();
            }
            zzx zzx2 = zznzp;
            return zzx2;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private final int zze(Context var1_1, Intent var2_5) {
        var4_6 = this.zznzq;
        // MONITORENTER : var4_6
        var5_7 = this.zznzq.get(var2_5.getAction());
        // MONITOREXIT : var4_6
        var4_6 = var5_7;
        if (var5_7 != null) ** GOTO lbl28
        var4_6 = var1_1.getPackageManager().resolveService(var2_5, 0);
        if (var4_6 == null || var4_6.serviceInfo == null) {
            Log.e((String)"FirebaseInstanceId", (String)"Failed to resolve target intent service, skipping classname enforcement");
        } else {
            var5_7 = var4_6.serviceInfo;
            if (!var1_1.getPackageName().equals(var5_7.packageName) || var5_7.name == null) {
                var4_6 = var5_7.packageName;
                var5_7 = var5_7.name;
                Log.e((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf(var4_6).length() + 94 + String.valueOf(var5_7).length()).append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ").append((String)var4_6).append("/").append((String)var5_7).toString());
            } else {
                var4_6 = var5_7 = var5_7.name;
                if (var5_7.startsWith(".")) {
                    var4_6 = String.valueOf(var1_1.getPackageName());
                    var4_6 = (var5_7 = String.valueOf(var5_7)).length() != 0 ? var4_6.concat((String)var5_7) : new String((String)var4_6);
                }
                var5_7 = this.zznzq;
                // MONITORENTER : var5_7
                this.zznzq.put(var2_5.getAction(), (String)var4_6);
                // MONITOREXIT : var5_7
lbl28:
                // 2 sources
                if (Log.isLoggable((String)"FirebaseInstanceId", (int)3)) {
                    var5_7 = String.valueOf(var4_6);
                    var5_7 = var5_7.length() != 0 ? "Restricting intent to a specific service: ".concat((String)var5_7) : new String("Restricting intent to a specific service: ");
                    Log.d((String)"FirebaseInstanceId", (String)var5_7);
                }
                var2_5.setClassName(var1_1.getPackageName(), (String)var4_6);
            }
        }
        try {
            if (this.zznzr == null) {
                var3_8 = var1_1.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0;
                this.zznzr = var3_8;
            }
            if (this.zznzr.booleanValue()) {
                var1_1 = WakefulBroadcastReceiver.startWakefulService(var1_1, var2_5);
            } else {
                var1_1 = var1_1.startService(var2_5);
                Log.d((String)"FirebaseInstanceId", (String)"Missing wake lock permission, service start may be delayed");
            }
            if (var1_1 != null) return -1;
            Log.e((String)"FirebaseInstanceId", (String)"Error while delivering the message: ServiceIntent not found.");
            return 404;
        }
        catch (SecurityException var1_2) {
            Log.e((String)"FirebaseInstanceId", (String)"Error while delivering the message to the serviceIntent", (Throwable)var1_2);
            return 401;
        }
        catch (IllegalStateException var1_3) {
            var1_4 = String.valueOf(var1_3);
            Log.e((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf(var1_4).length() + 45).append("Failed to start service while in background: ").append(var1_4).toString());
            return 402;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int zza(Context object, String string2, Intent intent) {
        int n = -1;
        switch (string2.hashCode()) {
            case -842411455: {
                if (!string2.equals("com.google.firebase.INSTANCE_ID_EVENT")) break;
                n = 0;
                break;
            }
            case 41532704: {
                if (!string2.equals("com.google.firebase.MESSAGING_EVENT")) break;
                n = 1;
                break;
            }
        }
        switch (n) {
            default: {
                object = String.valueOf(string2);
                object = ((String)object).length() != 0 ? "Unknown service action: ".concat((String)object) : new String("Unknown service action: ");
            }
            case 0: {
                this.zznzs.offer(intent);
                break;
            }
            case 1: {
                this.zznzt.offer(intent);
            }
        }
        string2 = new Intent(string2);
        string2.setPackage(object.getPackageName());
        return this.zze((Context)object, (Intent)string2);
        Log.w((String)"FirebaseInstanceId", (String)object);
        return 500;
    }

    public final Intent zzcjl() {
        return this.zznzt.poll();
    }
}


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
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Base64
 *  android.util.Log
 */
package com.google.android.gms.gcm;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.iid.zzh;

public class GcmReceiver
extends WakefulBroadcastReceiver {
    private static boolean zzicc = false;
    private static zzh zzicd;
    private static zzh zzice;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void doStartService(Context context, Intent intent) {
        Object object;
        if (this.isOrderedBroadcast()) {
            this.setResultCode(500);
        }
        if ((object = context.getPackageManager().resolveService(intent, 0)) == null || ((ResolveInfo)object).serviceInfo == null) {
            Log.e((String)"GcmReceiver", (String)"Failed to resolve target intent service, skipping classname enforcement");
        } else {
            Object object2 = ((ResolveInfo)object).serviceInfo;
            if (!context.getPackageName().equals(((ServiceInfo)object2).packageName) || ((ServiceInfo)object2).name == null) {
                object = ((ServiceInfo)object2).packageName;
                object2 = ((ServiceInfo)object2).name;
                Log.e((String)"GcmReceiver", (String)new StringBuilder(String.valueOf(object).length() + 94 + String.valueOf(object2).length()).append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ").append((String)object).append("/").append((String)object2).toString());
            } else {
                object = object2 = ((ServiceInfo)object2).name;
                if (((String)object2).startsWith(".")) {
                    object = String.valueOf(context.getPackageName());
                    object = ((String)(object2 = String.valueOf(object2))).length() != 0 ? ((String)object).concat((String)object2) : new String((String)object);
                }
                if (Log.isLoggable((String)"GcmReceiver", (int)3)) {
                    object2 = String.valueOf(object);
                    object2 = ((String)object2).length() != 0 ? "Restricting intent to a specific service: ".concat((String)object2) : new String("Restricting intent to a specific service: ");
                    Log.d((String)"GcmReceiver", (String)object2);
                }
                intent.setClassName(context.getPackageName(), (String)object);
            }
        }
        try {
            if (context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0) {
                context = GcmReceiver.startWakefulService(context, intent);
            } else {
                context = context.startService(intent);
                Log.d((String)"GcmReceiver", (String)"Missing wake lock permission, service start may be delayed");
            }
            if (context == null) {
                Log.e((String)"GcmReceiver", (String)"Error while delivering the message: ServiceIntent not found.");
                if (!this.isOrderedBroadcast()) return;
                {
                    this.setResultCode(404);
                    return;
                }
            } else {
                if (!this.isOrderedBroadcast()) return;
                {
                    this.setResultCode(-1);
                    return;
                }
            }
        }
        catch (SecurityException securityException) {
            Log.e((String)"GcmReceiver", (String)"Error while delivering the message to the serviceIntent", (Throwable)securityException);
            if (!this.isOrderedBroadcast()) return;
            this.setResultCode(401);
            return;
        }
    }

    private final zzh zzae(Context object, String string2) {
        synchronized (this) {
            if ("com.google.android.c2dm.intent.RECEIVE".equals(string2)) {
                if (zzice == null) {
                    zzice = new zzh((Context)object, string2);
                }
                object = zzice;
                return object;
            }
            if (zzicd == null) {
                zzicd = new zzh((Context)object, string2);
            }
            object = zzicd;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context context, Intent intent) {
        String string2;
        boolean bl = false;
        if (Log.isLoggable((String)"GcmReceiver", (int)3)) {
            Log.d((String)"GcmReceiver", (String)"received new intent");
        }
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        if (Build.VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        if ("google.com/iid".equals(string2 = intent.getStringExtra("from")) || "gcm.googleapis.com/refresh".equals(string2)) {
            intent.setAction("com.google.android.gms.iid.InstanceID");
        }
        if ((string2 = intent.getStringExtra("gcm.rawData64")) != null) {
            intent.putExtra("rawData", Base64.decode((String)string2, (int)0));
            intent.removeExtra("gcm.rawData64");
        }
        boolean bl2 = bl;
        if (zzq.isAtLeastO()) {
            bl2 = bl;
            if (context.getApplicationInfo().targetSdkVersion > 25) {
                bl2 = true;
            }
        }
        if (bl2) {
            if (this.isOrderedBroadcast()) {
                this.setResultCode(-1);
            }
            this.zzae(context, intent.getAction()).zza(intent, this.goAsync());
            return;
        } else {
            if ("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
                this.doStartService(context, intent);
            } else {
                this.doStartService(context, intent);
            }
            if (!this.isOrderedBroadcast() || this.getResultCode() != 0) return;
            {
                this.setResultCode(-1);
                return;
            }
        }
    }
}


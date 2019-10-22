/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzasd;
import com.google.android.gms.internal.zzasl;
import com.google.android.gms.internal.zzcxt;

public final class zzasc {
    static Object sLock = new Object();
    private static Boolean zzdoo;
    static zzcxt zzdyt;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static void onReceive(Context object, Intent object2) {
        zzarv zzarv2 = zzaqc.zzbm((Context)object).zzwt();
        if (object2 == null) {
            zzarv2.zzdx("AnalyticsReceiver called with null intent");
            return;
        }
        object2 = object2.getAction();
        zzarv2.zza("Local AnalyticsReceiver got", object2);
        if (!"com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(object2)) return;
        boolean bl = zzasd.zzbo((Context)object);
        Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        intent.setComponent(new ComponentName((Context)object, "com.google.android.gms.analytics.AnalyticsService"));
        intent.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        object2 = sLock;
        // MONITORENTER : object2
        object.startService(intent);
        if (!bl) {
            // MONITOREXIT : object2
            return;
        }
        try {
            if (zzdyt == null) {
                zzdyt = object = new zzcxt((Context)object, 1, "Analytics WakeLock");
                ((zzcxt)object).setReferenceCounted(false);
            }
            zzdyt.acquire(1000L);
            // MONITOREXIT : object2
            return;
        }
        catch (SecurityException securityException) {
            zzarv2.zzdx("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
            return;
        }
    }

    public static boolean zzbk(Context context) {
        zzbq.checkNotNull(context);
        if (zzdoo != null) {
            return zzdoo;
        }
        boolean bl = zzasl.zzb(context, "com.google.android.gms.analytics.AnalyticsReceiver", false);
        zzdoo = bl;
        return bl;
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.gms.common.util.zzq;

public final class zzbhd {
    private static Context zzgfe;
    private static Boolean zzgff;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean zzcz(Context context) {
        synchronized (zzbhd.class) {
            Context context2 = context.getApplicationContext();
            if (zzgfe != null && zzgff != null && zzgfe == context2) {
                return zzgff;
            }
            zzgff = null;
            if (zzq.isAtLeastO()) {
                zzgff = context2.getPackageManager().isInstantApp();
            } else {
                try {
                    context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                    zzgff = true;
                }
                catch (ClassNotFoundException classNotFoundException) {
                    zzgff = false;
                }
            }
            zzgfe = context2;
            return zzgff;
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.util.Log
 */
package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.zzq;
import com.google.android.gms.internal.zzbhf;

public final class zzx {
    @TargetApi(value=19)
    public static boolean zzb(Context context, int n, String string2) {
        return zzbhf.zzdb(context).zzf(n, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean zzf(Context context, int n) {
        block3: {
            if (zzx.zzb(context, n, "com.google.android.gms")) {
                PackageManager packageManager = context.getPackageManager();
                try {
                    packageManager = packageManager.getPackageInfo("com.google.android.gms", 64);
                    return zzq.zzci(context).zza((PackageInfo)packageManager);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    if (!Log.isLoggable((String)"UidVerifier", (int)3)) break block3;
                    Log.d((String)"UidVerifier", (String)"Package manager can't find google play services package, defaulting to false");
                    return false;
                }
            }
        }
        return false;
    }
}


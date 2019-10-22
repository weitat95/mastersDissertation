/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.text.TextUtils
 */
package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.common.zzp;
import com.google.android.gms.internal.zzbhf;

public class zzf {
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zzp.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final zzf zzfkx = new zzf();

    zzf() {
    }

    public static Intent zza(Context context, int n, String string2) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
            case 2: {
                if (context != null && zzi.zzct(context)) {
                    return zzak.zzaln();
                }
                return zzak.zzt("com.google.android.gms", zzf.zzu(context, string2));
            }
            case 3: 
        }
        return zzak.zzgk("com.google.android.gms");
    }

    public static zzf zzafy() {
        return zzfkx;
    }

    public static void zzce(Context context) {
        zzp.zzce(context);
    }

    public static int zzcf(Context context) {
        return zzp.zzcf(context);
    }

    public static boolean zze(Context context, int n) {
        return zzp.zze(context, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String zzu(Context context, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("gcore_");
        stringBuilder.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        stringBuilder.append("-");
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            stringBuilder.append(string2);
        }
        stringBuilder.append("-");
        if (context != null) {
            stringBuilder.append(context.getPackageName());
        }
        stringBuilder.append("-");
        if (context == null) return stringBuilder.toString();
        try {
            stringBuilder.append(zzbhf.zzdb((Context)context).getPackageInfo((String)context.getPackageName(), (int)0).versionCode);
            return stringBuilder.toString();
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return stringBuilder.toString();
        }
    }

    public PendingIntent getErrorResolutionPendingIntent(Context context, int n, int n2) {
        return this.zza(context, n, n2, null);
    }

    public String getErrorString(int n) {
        return zzp.getErrorString(n);
    }

    public int isGooglePlayServicesAvailable(Context context) {
        int n;
        int n2 = n = zzp.isGooglePlayServicesAvailable(context);
        if (zzp.zze(context, n)) {
            n2 = 18;
        }
        return n2;
    }

    public boolean isUserResolvableError(int n) {
        return zzp.isUserRecoverableError(n);
    }

    public final PendingIntent zza(Context context, int n, int n2, String string2) {
        if ((string2 = zzf.zza(context, n, string2)) == null) {
            return null;
        }
        return PendingIntent.getActivity((Context)context, (int)n2, (Intent)string2, (int)268435456);
    }

    @Deprecated
    public final Intent zzbp(int n) {
        return zzf.zza(null, n, null);
    }
}


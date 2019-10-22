/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.pm.PackageManager
 */
package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.gms.common.util.zzq;

public final class zzi {
    private static Boolean zzgei;
    private static Boolean zzgej;
    private static Boolean zzgek;

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=20)
    public static boolean zzcs(Context context) {
        if (zzgei == null) {
            boolean bl = zzq.zzamm() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
            zzgei = bl;
        }
        return zzgei;
    }

    @TargetApi(value=24)
    public static boolean zzct(Context context) {
        return (!zzq.isAtLeastN() || zzi.zzcu(context)) && zzi.zzcs(context);
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=21)
    public static boolean zzcu(Context context) {
        if (zzgej == null) {
            boolean bl = zzq.zzamn() && context.getPackageManager().hasSystemFeature("cn.google");
            zzgej = bl;
        }
        return zzgej;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean zzcv(Context context) {
        if (zzgek == null) {
            boolean bl = context.getPackageManager().hasSystemFeature("android.hardware.type.iot") || context.getPackageManager().hasSystemFeature("android.hardware.type.embedded");
            zzgek = bl;
        }
        return zzgek;
    }
}


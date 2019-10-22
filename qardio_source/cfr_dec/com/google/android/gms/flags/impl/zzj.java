/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.flags.impl.zzk;
import com.google.android.gms.internal.zzcbc;

public final class zzj {
    private static SharedPreferences zzhje = null;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SharedPreferences zzdi(Context context) throws Exception {
        synchronized (SharedPreferences.class) {
            if (zzhje != null) return zzhje;
            zzhje = zzcbc.zzb(new zzk(context));
            return zzhje;
        }
    }
}


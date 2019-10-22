/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.util.Log
 */
package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.flags.impl.zza;
import com.google.android.gms.flags.impl.zzg;
import com.google.android.gms.internal.zzcbc;

public final class zzf
extends zza<Long> {
    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Long zza(SharedPreferences object, String string2, Long l) {
        void var2_8;
        try {
            void var1_7;
            return zzcbc.zzb(new zzg((SharedPreferences)object, (String)var1_7, (Long)var2_8));
        }
        catch (Exception exception) {
            void var0_5;
            String string3 = String.valueOf(exception.getMessage());
            if (string3.length() != 0) {
                String string4 = "Flag value not available, returning default: ".concat(string3);
            } else {
                String string5 = new String("Flag value not available, returning default: ");
            }
            Log.w((String)"FlagDataUtils", (String)var0_5);
            return var2_8;
        }
    }
}


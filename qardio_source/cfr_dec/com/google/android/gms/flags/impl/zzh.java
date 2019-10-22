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
import com.google.android.gms.flags.impl.zzi;
import com.google.android.gms.internal.zzcbc;

public final class zzh
extends zza<String> {
    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String zza(SharedPreferences object, String string2, String string3) {
        void var2_8;
        try {
            void var1_7;
            return zzcbc.zzb(new zzi((SharedPreferences)object, (String)var1_7, (String)var2_8));
        }
        catch (Exception exception) {
            void var0_5;
            String string4 = String.valueOf(exception.getMessage());
            if (string4.length() != 0) {
                String string5 = "Flag value not available, returning default: ".concat(string4);
            } else {
                String string6 = new String("Flag value not available, returning default: ");
            }
            Log.w((String)"FlagDataUtils", (String)var0_5);
            return var2_8;
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.Process
 *  android.text.TextUtils
 */
package com.google.android.gms.common.stats;

import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;

public final class zzc {
    public static String zza(PowerManager.WakeLock object, String string2) {
        String string3 = String.valueOf(String.valueOf((long)Process.myPid() << 32 | (long)System.identityHashCode(object)));
        object = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            object = "";
        }
        if (((String)(object = String.valueOf(object))).length() != 0) {
            return string3.concat((String)object);
        }
        return new String(string3);
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.zzbhf;

public final class zzc {
    public static boolean zzz(Context context, String string2) {
        boolean bl = false;
        "com.google.android.gms".equals(string2);
        try {
            int n = zzbhf.zzdb((Context)context).getApplicationInfo((String)string2, (int)0).flags;
            if ((n & 0x200000) != 0) {
                bl = true;
            }
            return bl;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.os.Build;
import com.google.android.gms.internal.zzaru;

public final class zzark {
    public static int version() {
        try {
            int n = Integer.parseInt(Build.VERSION.SDK);
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            zzaru.zzf("Invalid version number", Build.VERSION.SDK);
            return 0;
        }
    }
}


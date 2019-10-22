/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.google.android.gms.common.util;

import android.os.SystemClock;
import com.google.android.gms.common.util.zzd;

public final class zzh
implements zzd {
    private static zzh zzgef = new zzh();

    private zzh() {
    }

    public static zzd zzamg() {
        return zzgef;
    }

    @Override
    public final long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public final long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    @Override
    public final long nanoTime() {
        return System.nanoTime();
    }
}


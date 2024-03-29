/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.SystemClock
 */
package com.bumptech.glide.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.SystemClock;

public final class LogTime {
    private static final double MILLIS_MULTIPLIER;

    static {
        double d = 1.0;
        if (17 <= Build.VERSION.SDK_INT) {
            d = 1.0 / Math.pow(10.0, 6.0);
        }
        MILLIS_MULTIPLIER = d;
    }

    public static double getElapsedMillis(long l) {
        return (double)(LogTime.getLogTime() - l) * MILLIS_MULTIPLIER;
    }

    @TargetApi(value=17)
    public static long getLogTime() {
        if (17 <= Build.VERSION.SDK_INT) {
            return SystemClock.elapsedRealtimeNanos();
        }
        return System.currentTimeMillis();
    }
}


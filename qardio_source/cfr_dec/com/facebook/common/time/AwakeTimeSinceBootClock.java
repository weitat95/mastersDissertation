/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.facebook.common.time;

import android.os.SystemClock;
import com.facebook.common.internal.DoNotStrip;

@DoNotStrip
public class AwakeTimeSinceBootClock {
    @DoNotStrip
    private static final AwakeTimeSinceBootClock INSTANCE = new AwakeTimeSinceBootClock();

    private AwakeTimeSinceBootClock() {
    }

    @DoNotStrip
    public static AwakeTimeSinceBootClock get() {
        return INSTANCE;
    }

    @DoNotStrip
    public long now() {
        return SystemClock.uptimeMillis();
    }
}


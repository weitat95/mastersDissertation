/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.time;

import com.facebook.common.time.Clock;

public class SystemClock
implements Clock {
    private static final SystemClock INSTANCE = new SystemClock();

    private SystemClock() {
    }

    public static SystemClock get() {
        return INSTANCE;
    }

    @Override
    public long now() {
        return System.currentTimeMillis();
    }
}


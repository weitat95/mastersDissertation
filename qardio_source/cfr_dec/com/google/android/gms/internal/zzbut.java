/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import java.util.concurrent.TimeUnit;

public final class zzbut {
    public static long zza(long l, TimeUnit timeUnit, TimeUnit timeUnit2) {
        return timeUnit.convert(timeUnit2.convert(l, timeUnit), timeUnit2);
    }
}


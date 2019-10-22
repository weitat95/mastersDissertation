/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.util;

import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;

public final class BackpressureHelper {
    public static long add(AtomicLong atomicLong, long l) {
        long l2;
        do {
            if ((l2 = atomicLong.get()) != Long.MAX_VALUE) continue;
            return Long.MAX_VALUE;
        } while (!atomicLong.compareAndSet(l2, BackpressureHelper.addCap(l2, l)));
        return l2;
    }

    public static long addCap(long l, long l2) {
        l = l2 = l + l2;
        if (l2 < 0L) {
            l = Long.MAX_VALUE;
        }
        return l;
    }

    public static long produced(AtomicLong atomicLong, long l) {
        long l2;
        long l3;
        do {
            long l4;
            if ((l2 = atomicLong.get()) == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            l3 = l4 = l2 - l;
            if (l4 >= 0L) continue;
            RxJavaPlugins.onError(new IllegalStateException("More produced than requested: " + l4));
            l3 = 0L;
        } while (!atomicLong.compareAndSet(l2, l3));
        return l3;
    }
}


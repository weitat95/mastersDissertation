/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.util;

import io.reactivex.internal.schedulers.NonBlockingThread;
import io.reactivex.plugins.RxJavaPlugins;

public final class BlockingHelper {
    public static void verifyNonBlocking() {
        if (RxJavaPlugins.isFailOnNonBlockingScheduler() && (Thread.currentThread() instanceof NonBlockingThread || RxJavaPlugins.onBeforeBlocking())) {
            throw new IllegalStateException("Attempt to block on a Scheduler " + Thread.currentThread().getName() + " that doesn't support blocking operators as they may lead to deadlock");
        }
    }
}


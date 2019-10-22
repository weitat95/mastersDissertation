/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.internal.schedulers.NewThreadWorker;
import io.reactivex.internal.schedulers.RxThreadFactory;
import java.util.concurrent.ThreadFactory;

public final class NewThreadScheduler
extends Scheduler {
    private static final RxThreadFactory THREAD_FACTORY = new RxThreadFactory("RxNewThreadScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.newthread-priority", 5))));
    final ThreadFactory threadFactory;

    public NewThreadScheduler() {
        this(THREAD_FACTORY);
    }

    public NewThreadScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new NewThreadWorker(this.threadFactory);
    }
}


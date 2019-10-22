/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.executors;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class CallerThreadExecutor
extends AbstractExecutorService {
    private static final CallerThreadExecutor sInstance = new CallerThreadExecutor();

    private CallerThreadExecutor() {
    }

    public static CallerThreadExecutor getInstance() {
        return sInstance;
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        return true;
    }

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public void shutdown() {
    }

    @Override
    public List<Runnable> shutdownNow() {
        this.shutdown();
        return Collections.emptyList();
    }
}


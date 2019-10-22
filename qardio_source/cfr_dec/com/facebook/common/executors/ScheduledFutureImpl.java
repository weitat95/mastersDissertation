/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  javax.annotation.Nullable
 */
package com.facebook.common.executors;

import android.os.Handler;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;

public class ScheduledFutureImpl<V>
implements RunnableFuture<V>,
ScheduledFuture<V> {
    private final Handler mHandler;
    private final FutureTask<V> mListenableFuture;

    public ScheduledFutureImpl(Handler handler, Runnable runnable, @Nullable V v) {
        this.mHandler = handler;
        this.mListenableFuture = new FutureTask<V>(runnable, v);
    }

    public ScheduledFutureImpl(Handler handler, Callable<V> callable) {
        this.mHandler = handler;
        this.mListenableFuture = new FutureTask<V>(callable);
    }

    @Override
    public boolean cancel(boolean bl) {
        return this.mListenableFuture.cancel(bl);
    }

    @Override
    public int compareTo(Delayed delayed) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return this.mListenableFuture.get();
    }

    @Override
    public V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.mListenableFuture.get(l, timeUnit);
    }

    @Override
    public long getDelay(TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCancelled() {
        return this.mListenableFuture.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.mListenableFuture.isDone();
    }

    @Override
    public void run() {
        this.mListenableFuture.run();
    }
}


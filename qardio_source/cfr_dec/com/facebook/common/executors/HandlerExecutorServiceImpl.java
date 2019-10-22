/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  javax.annotation.Nullable
 */
package com.facebook.common.executors;

import android.os.Handler;
import android.os.Looper;
import com.facebook.common.executors.HandlerExecutorService;
import com.facebook.common.executors.ScheduledFutureImpl;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class HandlerExecutorServiceImpl
extends AbstractExecutorService
implements HandlerExecutorService {
    private final Handler mHandler;

    public HandlerExecutorServiceImpl(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void execute(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    public boolean isHandlerThread() {
        return Thread.currentThread() == this.mHandler.getLooper().getThread();
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    protected <T> ScheduledFutureImpl<T> newTaskFor(Runnable runnable, T t) {
        return new ScheduledFutureImpl<T>(this.mHandler, runnable, t);
    }

    protected <T> ScheduledFutureImpl<T> newTaskFor(Callable<T> callable) {
        return new ScheduledFutureImpl<T>(this.mHandler, callable);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        runnable = this.newTaskFor(runnable, (Object)null);
        this.mHandler.postDelayed(runnable, timeUnit.toMillis(l));
        return runnable;
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> object, long l, TimeUnit timeUnit) {
        object = this.newTaskFor((Callable)object);
        this.mHandler.postDelayed((Runnable)object, timeUnit.toMillis(l));
        return object;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException();
    }

    public ScheduledFuture<?> submit(Runnable runnable) {
        return this.submit(runnable, (Object)null);
    }

    public <T> ScheduledFuture<T> submit(Runnable runnable, @Nullable T t) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        runnable = this.newTaskFor(runnable, (Object)t);
        this.execute(runnable);
        return runnable;
    }

    public <T> ScheduledFuture<T> submit(Callable<T> object) {
        if (object == null) {
            throw new NullPointerException();
        }
        object = this.newTaskFor((Callable)object);
        this.execute((Runnable)object);
        return object;
    }
}


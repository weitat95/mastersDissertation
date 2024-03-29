/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 */
package io.fabric.sdk.android.services.concurrency;

import android.annotation.TargetApi;
import io.fabric.sdk.android.services.concurrency.Dependency;
import io.fabric.sdk.android.services.concurrency.DependencyPriorityBlockingQueue;
import io.fabric.sdk.android.services.concurrency.PriorityFutureTask;
import io.fabric.sdk.android.services.concurrency.PriorityProvider;
import io.fabric.sdk.android.services.concurrency.PriorityTask;
import io.fabric.sdk.android.services.concurrency.Task;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityThreadPoolExecutor
extends ThreadPoolExecutor {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    private static final int MAXIMUM_POOL_SIZE;

    static {
        CPU_COUNT = Runtime.getRuntime().availableProcessors();
        CORE_POOL_SIZE = CPU_COUNT + 1;
        MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    }

    <T extends Runnable & Dependency & PriorityProvider> PriorityThreadPoolExecutor(int n, int n2, long l, TimeUnit timeUnit, DependencyPriorityBlockingQueue<T> dependencyPriorityBlockingQueue, ThreadFactory threadFactory) {
        super(n, n2, l, timeUnit, dependencyPriorityBlockingQueue, threadFactory);
        this.prestartAllCoreThreads();
    }

    public static PriorityThreadPoolExecutor create() {
        return PriorityThreadPoolExecutor.create(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE);
    }

    public static <T extends Runnable & Dependency & PriorityProvider> PriorityThreadPoolExecutor create(int n, int n2) {
        return new PriorityThreadPoolExecutor(n, n2, 1L, TimeUnit.SECONDS, new DependencyPriorityBlockingQueue(), new PriorityThreadFactory(10));
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        Task task = (Task)((Object)runnable);
        task.setFinished(true);
        task.setError(throwable);
        this.getQueue().recycleBlockedQueue();
        super.afterExecute(runnable, throwable);
    }

    @TargetApi(value=9)
    @Override
    public void execute(Runnable runnable) {
        if (PriorityTask.isProperDelegate(runnable)) {
            super.execute(runnable);
            return;
        }
        super.execute(this.newTaskFor(runnable, null));
    }

    public DependencyPriorityBlockingQueue getQueue() {
        return (DependencyPriorityBlockingQueue)super.getQueue();
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new PriorityFutureTask<T>(runnable, t);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new PriorityFutureTask<T>(callable);
    }

    protected static final class PriorityThreadFactory
    implements ThreadFactory {
        private final int threadPriority;

        public PriorityThreadFactory(int n) {
            this.threadPriority = n;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            runnable = new Thread(runnable);
            ((Thread)runnable).setPriority(this.threadPriority);
            ((Thread)runnable).setName("Queue");
            return runnable;
        }
    }

}


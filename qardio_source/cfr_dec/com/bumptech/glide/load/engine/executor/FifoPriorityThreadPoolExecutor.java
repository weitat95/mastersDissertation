/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Process
 *  android.util.Log
 */
package com.bumptech.glide.load.engine.executor;

import android.os.Process;
import android.util.Log;
import com.bumptech.glide.load.engine.executor.Prioritized;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FifoPriorityThreadPoolExecutor
extends ThreadPoolExecutor {
    private final AtomicInteger ordering = new AtomicInteger();
    private final UncaughtThrowableStrategy uncaughtThrowableStrategy;

    public FifoPriorityThreadPoolExecutor(int n) {
        this(n, UncaughtThrowableStrategy.LOG);
    }

    public FifoPriorityThreadPoolExecutor(int n, int n2, long l, TimeUnit timeUnit, ThreadFactory threadFactory, UncaughtThrowableStrategy uncaughtThrowableStrategy) {
        super(n, n2, l, timeUnit, new PriorityBlockingQueue<Runnable>(), threadFactory);
        this.uncaughtThrowableStrategy = uncaughtThrowableStrategy;
    }

    public FifoPriorityThreadPoolExecutor(int n, UncaughtThrowableStrategy uncaughtThrowableStrategy) {
        this(n, n, 0L, TimeUnit.MILLISECONDS, new DefaultThreadFactory(), uncaughtThrowableStrategy);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void afterExecute(Runnable object, Throwable throwable) {
        super.afterExecute((Runnable)object, throwable);
        if (throwable != null || !(object instanceof Future) || !(object = (Future)object).isDone() || object.isCancelled()) return;
        try {
            object.get();
            return;
        }
        catch (InterruptedException interruptedException) {
            this.uncaughtThrowableStrategy.handle(interruptedException);
            return;
        }
        catch (ExecutionException executionException) {
            this.uncaughtThrowableStrategy.handle(executionException);
            return;
        }
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new LoadTask<T>(runnable, t, this.ordering.getAndIncrement());
    }

    public static class DefaultThreadFactory
    implements ThreadFactory {
        int threadNum = 0;

        @Override
        public Thread newThread(Runnable runnable) {
            runnable = new Thread(runnable, "fifo-pool-thread-" + this.threadNum){

                @Override
                public void run() {
                    Process.setThreadPriority((int)10);
                    super.run();
                }
            };
            ++this.threadNum;
            return runnable;
        }

    }

    static class LoadTask<T>
    extends FutureTask<T>
    implements Comparable<LoadTask<?>> {
        private final int order;
        private final int priority;

        public LoadTask(Runnable runnable, T t, int n) {
            super(runnable, t);
            if (!(runnable instanceof Prioritized)) {
                throw new IllegalArgumentException("FifoPriorityThreadPoolExecutor must be given Runnables that implement Prioritized");
            }
            this.priority = ((Prioritized)((Object)runnable)).getPriority();
            this.order = n;
        }

        @Override
        public int compareTo(LoadTask<?> loadTask) {
            int n;
            int n2 = n = this.priority - loadTask.priority;
            if (n == 0) {
                n2 = this.order - loadTask.order;
            }
            return n2;
        }

        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = bl = false;
            if (object instanceof LoadTask) {
                object = (LoadTask)object;
                bl2 = bl;
                if (this.order == ((LoadTask)object).order) {
                    bl2 = bl;
                    if (this.priority == ((LoadTask)object).priority) {
                        bl2 = true;
                    }
                }
            }
            return bl2;
        }

        public int hashCode() {
            return this.priority * 31 + this.order;
        }
    }

    public static enum UncaughtThrowableStrategy {
        IGNORE,
        LOG{

            @Override
            protected void handle(Throwable throwable) {
                if (Log.isLoggable((String)"PriorityExecutor", (int)6)) {
                    Log.e((String)"PriorityExecutor", (String)"Request threw uncaught throwable", (Throwable)throwable);
                }
            }
        }
        ,
        THROW{

            @Override
            protected void handle(Throwable throwable) {
                super.handle(throwable);
                throw new RuntimeException(throwable);
            }
        };


        protected void handle(Throwable throwable) {
        }

    }

}


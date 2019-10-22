/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.NewThreadWorker;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;

public abstract class Scheduler {
    static final long CLOCK_DRIFT_TOLERANCE_NANOSECONDS = TimeUnit.MINUTES.toNanos(Long.getLong("rx2.scheduler.drift-tolerance", 15L));

    public abstract Worker createWorker();

    public Disposable scheduleDirect(Runnable runnable) {
        return this.scheduleDirect(runnable, 0L, TimeUnit.NANOSECONDS);
    }

    public Disposable scheduleDirect(Runnable runnable, long l, TimeUnit timeUnit) {
        Worker worker = this.createWorker();
        runnable = new DisposeTask(RxJavaPlugins.onSchedule(runnable), worker);
        worker.schedule(runnable, l, timeUnit);
        return runnable;
    }

    public void start() {
    }

    static final class DisposeTask
    implements Disposable,
    Runnable {
        final Runnable decoratedRun;
        Thread runner;
        final Worker w;

        DisposeTask(Runnable runnable, Worker worker) {
            this.decoratedRun = runnable;
            this.w = worker;
        }

        @Override
        public void dispose() {
            if (this.runner == Thread.currentThread() && this.w instanceof NewThreadWorker) {
                ((NewThreadWorker)this.w).shutdown();
                return;
            }
            this.w.dispose();
        }

        @Override
        public boolean isDisposed() {
            return this.w.isDisposed();
        }

        @Override
        public void run() {
            this.runner = Thread.currentThread();
            try {
                this.decoratedRun.run();
                return;
            }
            finally {
                this.dispose();
                this.runner = null;
            }
        }
    }

    public static abstract class Worker
    implements Disposable {
        public long now(TimeUnit timeUnit) {
            return timeUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        public Disposable schedule(Runnable runnable) {
            return this.schedule(runnable, 0L, TimeUnit.NANOSECONDS);
        }

        public abstract Disposable schedule(Runnable var1, long var2, TimeUnit var4);
    }

}


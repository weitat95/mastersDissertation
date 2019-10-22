/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;
import io.reactivex.internal.schedulers.NewThreadWorker;
import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.internal.schedulers.ScheduledRunnable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ComputationScheduler
extends Scheduler {
    static final int MAX_THREADS = ComputationScheduler.cap(Runtime.getRuntime().availableProcessors(), Integer.getInteger("rx2.computation-threads", 0));
    static final FixedSchedulerPool NONE;
    static final PoolWorker SHUTDOWN_WORKER;
    static final RxThreadFactory THREAD_FACTORY;
    final AtomicReference<FixedSchedulerPool> pool;
    final ThreadFactory threadFactory;

    static {
        SHUTDOWN_WORKER = new PoolWorker(new RxThreadFactory("RxComputationShutdown"));
        SHUTDOWN_WORKER.dispose();
        THREAD_FACTORY = new RxThreadFactory("RxComputationThreadPool", Math.max(1, Math.min(10, Integer.getInteger("rx2.computation-priority", 5))), true);
        NONE = new FixedSchedulerPool(0, THREAD_FACTORY);
        NONE.shutdown();
    }

    public ComputationScheduler() {
        this(THREAD_FACTORY);
    }

    public ComputationScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        this.pool = new AtomicReference<FixedSchedulerPool>(NONE);
        this.start();
    }

    static int cap(int n, int n2) {
        int n3;
        block3: {
            block2: {
                if (n2 <= 0) break block2;
                n3 = n2;
                if (n2 <= n) break block3;
            }
            n3 = n;
        }
        return n3;
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new EventLoopWorker(this.pool.get().getEventLoop());
    }

    @Override
    public Disposable scheduleDirect(Runnable runnable, long l, TimeUnit timeUnit) {
        return this.pool.get().getEventLoop().scheduleDirect(runnable, l, timeUnit);
    }

    @Override
    public void start() {
        FixedSchedulerPool fixedSchedulerPool = new FixedSchedulerPool(MAX_THREADS, this.threadFactory);
        if (!this.pool.compareAndSet(NONE, fixedSchedulerPool)) {
            fixedSchedulerPool.shutdown();
        }
    }

    static final class EventLoopWorker
    extends Scheduler.Worker {
        private final ListCompositeDisposable both;
        volatile boolean disposed;
        private final PoolWorker poolWorker;
        private final ListCompositeDisposable serial;
        private final CompositeDisposable timed;

        EventLoopWorker(PoolWorker poolWorker) {
            this.poolWorker = poolWorker;
            this.serial = new ListCompositeDisposable();
            this.timed = new CompositeDisposable();
            this.both = new ListCompositeDisposable();
            this.both.add(this.serial);
            this.both.add(this.timed);
        }

        @Override
        public void dispose() {
            if (!this.disposed) {
                this.disposed = true;
                this.both.dispose();
            }
        }

        @Override
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override
        public Disposable schedule(Runnable runnable) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            return this.poolWorker.scheduleActual(runnable, 0L, TimeUnit.MILLISECONDS, this.serial);
        }

        @Override
        public Disposable schedule(Runnable runnable, long l, TimeUnit timeUnit) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            return this.poolWorker.scheduleActual(runnable, l, timeUnit, this.timed);
        }
    }

    static final class FixedSchedulerPool {
        final int cores;
        final PoolWorker[] eventLoops;
        long n;

        FixedSchedulerPool(int n, ThreadFactory threadFactory) {
            this.cores = n;
            this.eventLoops = new PoolWorker[n];
            for (int i = 0; i < n; ++i) {
                this.eventLoops[i] = new PoolWorker(threadFactory);
            }
        }

        public PoolWorker getEventLoop() {
            int n = this.cores;
            if (n == 0) {
                return SHUTDOWN_WORKER;
            }
            PoolWorker[] arrpoolWorker = this.eventLoops;
            long l = this.n;
            this.n = 1L + l;
            return arrpoolWorker[(int)(l % (long)n)];
        }

        public void shutdown() {
            PoolWorker[] arrpoolWorker = this.eventLoops;
            int n = arrpoolWorker.length;
            for (int i = 0; i < n; ++i) {
                arrpoolWorker[i].dispose();
            }
        }
    }

    static final class PoolWorker
    extends NewThreadWorker {
        PoolWorker(ThreadFactory threadFactory) {
            super(threadFactory);
        }
    }

}


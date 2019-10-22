/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.schedulers.NewThreadWorker;
import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.internal.schedulers.ScheduledRunnable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class IoScheduler
extends Scheduler {
    static final RxThreadFactory EVICTOR_THREAD_FACTORY;
    private static final TimeUnit KEEP_ALIVE_UNIT;
    static final CachedWorkerPool NONE;
    static final ThreadWorker SHUTDOWN_THREAD_WORKER;
    static final RxThreadFactory WORKER_THREAD_FACTORY;
    final AtomicReference<CachedWorkerPool> pool;
    final ThreadFactory threadFactory;

    static {
        KEEP_ALIVE_UNIT = TimeUnit.SECONDS;
        SHUTDOWN_THREAD_WORKER = new ThreadWorker(new RxThreadFactory("RxCachedThreadSchedulerShutdown"));
        SHUTDOWN_THREAD_WORKER.dispose();
        int n = Math.max(1, Math.min(10, Integer.getInteger("rx2.io-priority", 5)));
        WORKER_THREAD_FACTORY = new RxThreadFactory("RxCachedThreadScheduler", n);
        EVICTOR_THREAD_FACTORY = new RxThreadFactory("RxCachedWorkerPoolEvictor", n);
        NONE = new CachedWorkerPool(0L, null, WORKER_THREAD_FACTORY);
        NONE.shutdown();
    }

    public IoScheduler() {
        this(WORKER_THREAD_FACTORY);
    }

    public IoScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        this.pool = new AtomicReference<CachedWorkerPool>(NONE);
        this.start();
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new EventLoopWorker(this.pool.get());
    }

    @Override
    public void start() {
        CachedWorkerPool cachedWorkerPool = new CachedWorkerPool(60L, KEEP_ALIVE_UNIT, this.threadFactory);
        if (!this.pool.compareAndSet(NONE, cachedWorkerPool)) {
            cachedWorkerPool.shutdown();
        }
    }

    static final class CachedWorkerPool
    implements Runnable {
        final CompositeDisposable allWorkers;
        private final ScheduledExecutorService evictorService;
        private final Future<?> evictorTask;
        private final ConcurrentLinkedQueue<ThreadWorker> expiringWorkerQueue;
        private final long keepAliveTime;
        private final ThreadFactory threadFactory;

        /*
         * Enabled aggressive block sorting
         */
        CachedWorkerPool(long l, TimeUnit timeUnit, ThreadFactory object) {
            l = timeUnit != null ? timeUnit.toNanos(l) : 0L;
            this.keepAliveTime = l;
            this.expiringWorkerQueue = new ConcurrentLinkedQueue();
            this.allWorkers = new CompositeDisposable();
            this.threadFactory = object;
            object = null;
            ScheduledFuture<?> scheduledFuture = null;
            if (timeUnit != null) {
                object = Executors.newScheduledThreadPool(1, EVICTOR_THREAD_FACTORY);
                scheduledFuture = object.scheduleWithFixedDelay(this, this.keepAliveTime, this.keepAliveTime, TimeUnit.NANOSECONDS);
            }
            this.evictorService = object;
            this.evictorTask = scheduledFuture;
        }

        void evictExpiredWorkers() {
            if (!this.expiringWorkerQueue.isEmpty()) {
                long l = this.now();
                for (ThreadWorker threadWorker : this.expiringWorkerQueue) {
                    if (threadWorker.getExpirationTime() > l) break;
                    if (!this.expiringWorkerQueue.remove(threadWorker)) continue;
                    this.allWorkers.remove(threadWorker);
                }
            }
        }

        ThreadWorker get() {
            ThreadWorker threadWorker;
            if (this.allWorkers.isDisposed()) {
                return SHUTDOWN_THREAD_WORKER;
            }
            while (!this.expiringWorkerQueue.isEmpty()) {
                threadWorker = this.expiringWorkerQueue.poll();
                if (threadWorker == null) continue;
                return threadWorker;
            }
            threadWorker = new ThreadWorker(this.threadFactory);
            this.allWorkers.add(threadWorker);
            return threadWorker;
        }

        long now() {
            return System.nanoTime();
        }

        void release(ThreadWorker threadWorker) {
            threadWorker.setExpirationTime(this.now() + this.keepAliveTime);
            this.expiringWorkerQueue.offer(threadWorker);
        }

        @Override
        public void run() {
            this.evictExpiredWorkers();
        }

        void shutdown() {
            this.allWorkers.dispose();
            if (this.evictorTask != null) {
                this.evictorTask.cancel(true);
            }
            if (this.evictorService != null) {
                this.evictorService.shutdownNow();
            }
        }
    }

    static final class EventLoopWorker
    extends Scheduler.Worker {
        final AtomicBoolean once = new AtomicBoolean();
        private final CachedWorkerPool pool;
        private final CompositeDisposable tasks;
        private final ThreadWorker threadWorker;

        EventLoopWorker(CachedWorkerPool cachedWorkerPool) {
            this.pool = cachedWorkerPool;
            this.tasks = new CompositeDisposable();
            this.threadWorker = cachedWorkerPool.get();
        }

        @Override
        public void dispose() {
            if (this.once.compareAndSet(false, true)) {
                this.tasks.dispose();
                this.pool.release(this.threadWorker);
            }
        }

        @Override
        public boolean isDisposed() {
            return this.once.get();
        }

        @Override
        public Disposable schedule(Runnable runnable, long l, TimeUnit timeUnit) {
            if (this.tasks.isDisposed()) {
                return EmptyDisposable.INSTANCE;
            }
            return this.threadWorker.scheduleActual(runnable, l, timeUnit, this.tasks);
        }
    }

    static final class ThreadWorker
    extends NewThreadWorker {
        private long expirationTime = 0L;

        ThreadWorker(ThreadFactory threadFactory) {
            super(threadFactory);
        }

        public long getExpirationTime() {
            return this.expirationTime;
        }

        public void setExpirationTime(long l) {
            this.expirationTime = l;
        }
    }

}


/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.internal.schedulers.ScheduledDirectTask;
import io.reactivex.internal.schedulers.ScheduledRunnable;
import io.reactivex.internal.schedulers.SchedulerPoolFactory;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleScheduler
extends Scheduler {
    static final ScheduledExecutorService SHUTDOWN = Executors.newScheduledThreadPool(0);
    static final RxThreadFactory SINGLE_THREAD_FACTORY;
    final AtomicReference<ScheduledExecutorService> executor = new AtomicReference();
    final ThreadFactory threadFactory;

    static {
        SHUTDOWN.shutdown();
        SINGLE_THREAD_FACTORY = new RxThreadFactory("RxSingleScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.single-priority", 5))), true);
    }

    public SingleScheduler() {
        this(SINGLE_THREAD_FACTORY);
    }

    public SingleScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        this.executor.lazySet(SingleScheduler.createExecutor(threadFactory));
    }

    static ScheduledExecutorService createExecutor(ThreadFactory threadFactory) {
        return SchedulerPoolFactory.create(threadFactory);
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new ScheduledWorker(this.executor.get());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Disposable scheduleDirect(Runnable var1_1, long var2_3, TimeUnit var4_4) {
        var5_5 = new ScheduledDirectTask(RxJavaPlugins.onSchedule(var1_1 /* !! */ ));
        if (var2_3 > 0L) ** GOTO lbl6
        try {
            block2: {
                var1_1 /* !! */  = this.executor.get().submit(var5_5);
                break block2;
lbl6:
                // 1 sources
                var1_1 /* !! */  = this.executor.get().schedule(var5_5, var2_3, var4_4);
            }
            var5_5.setFuture(var1_1 /* !! */ );
            return var5_5;
        }
        catch (RejectedExecutionException var1_2) {
            RxJavaPlugins.onError(var1_2);
            return EmptyDisposable.INSTANCE;
        }
    }

    @Override
    public void start() {
        ScheduledExecutorService scheduledExecutorService;
        ExecutorService executorService;
        ExecutorService executorService2 = null;
        do {
            if ((scheduledExecutorService = this.executor.get()) != SHUTDOWN) {
                if (executorService2 != null) {
                    executorService2.shutdown();
                }
                return;
            }
            executorService = executorService2;
            if (executorService2 == null) {
                executorService = SingleScheduler.createExecutor(this.threadFactory);
            }
            executorService2 = executorService;
        } while (!this.executor.compareAndSet(scheduledExecutorService, (ScheduledExecutorService)executorService));
    }

    static final class ScheduledWorker
    extends Scheduler.Worker {
        volatile boolean disposed;
        final ScheduledExecutorService executor;
        final CompositeDisposable tasks;

        ScheduledWorker(ScheduledExecutorService scheduledExecutorService) {
            this.executor = scheduledExecutorService;
            this.tasks = new CompositeDisposable();
        }

        @Override
        public void dispose() {
            if (!this.disposed) {
                this.disposed = true;
                this.tasks.dispose();
            }
        }

        @Override
        public boolean isDisposed() {
            return this.disposed;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public Disposable schedule(Runnable var1_1, long var2_3, TimeUnit var4_4) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            var5_5 = new ScheduledRunnable(RxJavaPlugins.onSchedule(var1_1 /* !! */ ), this.tasks);
            this.tasks.add(var5_5);
            if (var2_3 > 0L) ** GOTO lbl10
            try {
                block4: {
                    var1_1 /* !! */  = this.executor.submit(var5_5);
                    break block4;
lbl10:
                    // 1 sources
                    var1_1 /* !! */  = this.executor.schedule(var5_5, var2_3, var4_4);
                }
                var5_5.setFuture(var1_1 /* !! */ );
                return var5_5;
            }
            catch (RejectedExecutionException var1_2) {
                this.dispose();
                RxJavaPlugins.onError(var1_2);
                return EmptyDisposable.INSTANCE;
            }
        }
    }

}


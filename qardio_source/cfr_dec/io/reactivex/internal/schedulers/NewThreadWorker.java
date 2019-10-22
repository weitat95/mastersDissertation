/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.schedulers.ScheduledDirectTask;
import io.reactivex.internal.schedulers.ScheduledRunnable;
import io.reactivex.internal.schedulers.SchedulerPoolFactory;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class NewThreadWorker
extends Scheduler.Worker
implements Disposable {
    volatile boolean disposed;
    private final ScheduledExecutorService executor;

    public NewThreadWorker(ThreadFactory threadFactory) {
        this.executor = SchedulerPoolFactory.create(threadFactory);
    }

    @Override
    public void dispose() {
        if (!this.disposed) {
            this.disposed = true;
            this.executor.shutdownNow();
        }
    }

    @Override
    public boolean isDisposed() {
        return this.disposed;
    }

    @Override
    public Disposable schedule(Runnable runnable) {
        return this.schedule(runnable, 0L, null);
    }

    @Override
    public Disposable schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        if (this.disposed) {
            return EmptyDisposable.INSTANCE;
        }
        return this.scheduleActual(runnable, l, timeUnit, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public ScheduledRunnable scheduleActual(Runnable var1_1, long var2_3, TimeUnit var4_4, DisposableContainer var5_5) {
        var6_6 = new ScheduledRunnable(RxJavaPlugins.onSchedule(var1_1 /* !! */ ), var5_5);
        if (var5_5 != null && !var5_5.add(var6_6)) {
            return var6_6;
        }
        if (var2_3 > 0L) ** GOTO lbl8
        try {
            block5: {
                var1_1 /* !! */  = this.executor.submit(var6_6);
                break block5;
lbl8:
                // 1 sources
                var1_1 /* !! */  = this.executor.schedule(var6_6, var2_3, var4_4);
            }
            var6_6.setFuture(var1_1 /* !! */ );
            return var6_6;
        }
        catch (RejectedExecutionException var1_2) {
            if (var5_5 != null) {
                var5_5.remove(var6_6);
            }
            RxJavaPlugins.onError(var1_2);
            return var6_6;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Disposable scheduleDirect(Runnable var1_1, long var2_3, TimeUnit var4_4) {
        var5_5 = new ScheduledDirectTask(RxJavaPlugins.onSchedule(var1_1 /* !! */ ));
        if (var2_3 > 0L) ** GOTO lbl6
        try {
            block2: {
                var1_1 /* !! */  = this.executor.submit(var5_5);
                break block2;
lbl6:
                // 1 sources
                var1_1 /* !! */  = this.executor.schedule(var5_5, var2_3, var4_4);
            }
            var5_5.setFuture(var1_1 /* !! */ );
            return var5_5;
        }
        catch (RejectedExecutionException var1_2) {
            RxJavaPlugins.onError(var1_2);
            return EmptyDisposable.INSTANCE;
        }
    }

    public void shutdown() {
        if (!this.disposed) {
            this.disposed = true;
            this.executor.shutdown();
        }
    }
}


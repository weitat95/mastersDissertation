/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Message
 */
package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Message;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;

final class HandlerScheduler
extends Scheduler {
    private final Handler handler;

    HandlerScheduler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new HandlerWorker(this.handler);
    }

    @Override
    public Disposable scheduleDirect(Runnable runnable, long l, TimeUnit timeUnit) {
        if (runnable == null) {
            throw new NullPointerException("run == null");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit == null");
        }
        runnable = RxJavaPlugins.onSchedule(runnable);
        runnable = new ScheduledRunnable(this.handler, runnable);
        this.handler.postDelayed(runnable, Math.max(0L, timeUnit.toMillis(l)));
        return runnable;
    }

    private static final class HandlerWorker
    extends Scheduler.Worker {
        private volatile boolean disposed;
        private final Handler handler;

        HandlerWorker(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void dispose() {
            this.disposed = true;
            this.handler.removeCallbacksAndMessages((Object)this);
        }

        @Override
        public boolean isDisposed() {
            return this.disposed;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public Disposable schedule(Runnable object, long l, TimeUnit timeUnit) {
            if (object == null) {
                throw new NullPointerException("run == null");
            }
            if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            }
            if (this.disposed) {
                return Disposables.disposed();
            }
            object = RxJavaPlugins.onSchedule((Runnable)object);
            ScheduledRunnable scheduledRunnable = new ScheduledRunnable(this.handler, (Runnable)object);
            object = Message.obtain((Handler)this.handler, (Runnable)scheduledRunnable);
            ((Message)object).obj = this;
            this.handler.sendMessageDelayed((Message)object, Math.max(0L, timeUnit.toMillis(l)));
            object = scheduledRunnable;
            if (!this.disposed) return object;
            this.handler.removeCallbacks((Runnable)scheduledRunnable);
            return Disposables.disposed();
        }
    }

    private static final class ScheduledRunnable
    implements Disposable,
    Runnable {
        private final Runnable delegate;
        private volatile boolean disposed;
        private final Handler handler;

        ScheduledRunnable(Handler handler, Runnable runnable) {
            this.handler = handler;
            this.delegate = runnable;
        }

        @Override
        public void dispose() {
            this.disposed = true;
            this.handler.removeCallbacks((Runnable)this);
        }

        @Override
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override
        public void run() {
            try {
                this.delegate.run();
                return;
            }
            catch (Throwable throwable) {
                IllegalStateException illegalStateException = new IllegalStateException("Fatal Exception thrown on Scheduler.", throwable);
                RxJavaPlugins.onError(illegalStateException);
                Thread thread = Thread.currentThread();
                thread.getUncaughtExceptionHandler().uncaughtException(thread, illegalStateException);
                return;
            }
        }
    }

}


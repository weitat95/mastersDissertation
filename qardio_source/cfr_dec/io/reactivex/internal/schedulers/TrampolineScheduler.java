/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class TrampolineScheduler
extends Scheduler {
    private static final TrampolineScheduler INSTANCE = new TrampolineScheduler();

    TrampolineScheduler() {
    }

    public static TrampolineScheduler instance() {
        return INSTANCE;
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new TrampolineWorker();
    }

    @Override
    public Disposable scheduleDirect(Runnable runnable) {
        runnable.run();
        return EmptyDisposable.INSTANCE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Disposable scheduleDirect(Runnable runnable, long l, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(l);
            runnable.run();
            do {
                return EmptyDisposable.INSTANCE;
                break;
            } while (true);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            RxJavaPlugins.onError(interruptedException);
            return EmptyDisposable.INSTANCE;
        }
    }

    static final class SleepingRunnable
    implements Runnable {
        private final long execTime;
        private final Runnable run;
        private final TrampolineWorker worker;

        SleepingRunnable(Runnable runnable, TrampolineWorker trampolineWorker, long l) {
            this.run = runnable;
            this.worker = trampolineWorker;
            this.execTime = l;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            if (!this.worker.disposed) {
                long l = this.worker.now(TimeUnit.MILLISECONDS);
                if (this.execTime > l && (l = this.execTime - l) > 0L) {
                    try {
                        Thread.sleep(l);
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        RxJavaPlugins.onError(interruptedException);
                        return;
                    }
                }
                if (!this.worker.disposed) {
                    this.run.run();
                }
            }
        }
    }

    static final class TimedRunnable
    implements Comparable<TimedRunnable> {
        final int count;
        volatile boolean disposed;
        final long execTime;
        final Runnable run;

        TimedRunnable(Runnable runnable, Long l, int n) {
            this.run = runnable;
            this.execTime = l;
            this.count = n;
        }

        @Override
        public int compareTo(TimedRunnable timedRunnable) {
            int n;
            int n2 = n = ObjectHelper.compare(this.execTime, timedRunnable.execTime);
            if (n == 0) {
                n2 = ObjectHelper.compare(this.count, timedRunnable.count);
            }
            return n2;
        }
    }

    static final class TrampolineWorker
    extends Scheduler.Worker
    implements Disposable {
        final AtomicInteger counter;
        volatile boolean disposed;
        final PriorityBlockingQueue<TimedRunnable> queue = new PriorityBlockingQueue();
        private final AtomicInteger wip = new AtomicInteger();

        TrampolineWorker() {
            this.counter = new AtomicInteger();
        }

        @Override
        public void dispose() {
            this.disposed = true;
        }

        Disposable enqueue(Runnable object, long l) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            object = new TimedRunnable((Runnable)object, l, this.counter.incrementAndGet());
            this.queue.add((TimedRunnable)object);
            if (this.wip.getAndIncrement() == 0) {
                int n = 1;
                do {
                    if (this.disposed) {
                        this.queue.clear();
                        return EmptyDisposable.INSTANCE;
                    }
                    object = this.queue.poll();
                    if (object == null) {
                        int n2;
                        n = n2 = this.wip.addAndGet(-n);
                        if (n2 != 0) continue;
                        return EmptyDisposable.INSTANCE;
                    }
                    if (((TimedRunnable)object).disposed) continue;
                    ((TimedRunnable)object).run.run();
                } while (true);
            }
            return Disposables.fromRunnable(new AppendToQueueTask((TimedRunnable)object));
        }

        @Override
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override
        public Disposable schedule(Runnable runnable) {
            return this.enqueue(runnable, this.now(TimeUnit.MILLISECONDS));
        }

        @Override
        public Disposable schedule(Runnable runnable, long l, TimeUnit timeUnit) {
            l = this.now(TimeUnit.MILLISECONDS) + timeUnit.toMillis(l);
            return this.enqueue(new SleepingRunnable(runnable, this, l), l);
        }

        final class AppendToQueueTask
        implements Runnable {
            final TimedRunnable timedRunnable;

            AppendToQueueTask(TimedRunnable timedRunnable) {
                this.timedRunnable = timedRunnable;
            }

            @Override
            public void run() {
                this.timedRunnable.disposed = true;
                TrampolineWorker.this.queue.remove(this.timedRunnable);
            }
        }

    }

}


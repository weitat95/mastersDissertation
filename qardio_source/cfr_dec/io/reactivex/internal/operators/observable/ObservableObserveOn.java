/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableObserveOn<T>
extends AbstractObservableWithUpstream<T, T> {
    final int bufferSize;
    final boolean delayError;
    final Scheduler scheduler;

    public ObservableObserveOn(ObservableSource<T> observableSource, Scheduler scheduler, boolean bl, int n) {
        super(observableSource);
        this.scheduler = scheduler;
        this.delayError = bl;
        this.bufferSize = n;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        if (this.scheduler instanceof TrampolineScheduler) {
            this.source.subscribe(observer);
            return;
        }
        Scheduler.Worker worker = this.scheduler.createWorker();
        this.source.subscribe(new ObserveOnObserver<T>(observer, worker, this.delayError, this.bufferSize));
    }

    static final class ObserveOnObserver<T>
    extends BasicIntQueueDisposable<T>
    implements Observer<T>,
    Runnable {
        final Observer<? super T> actual;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        boolean outputFused;
        SimpleQueue<T> queue;
        Disposable s;
        int sourceMode;
        final Scheduler.Worker worker;

        ObserveOnObserver(Observer<? super T> observer, Scheduler.Worker worker, boolean bl, int n) {
            this.actual = observer;
            this.worker = worker;
            this.delayError = bl;
            this.bufferSize = n;
        }

        /*
         * Enabled aggressive block sorting
         */
        boolean checkTerminated(boolean bl, boolean bl2, Observer<? super T> observer) {
            if (this.cancelled) {
                this.queue.clear();
                return true;
            }
            if (!bl) return false;
            {
                Throwable throwable = this.error;
                if (this.delayError) {
                    if (!bl2) return false;
                    {
                        if (throwable != null) {
                            observer.onError(throwable);
                        } else {
                            observer.onComplete();
                        }
                        this.worker.dispose();
                        return true;
                    }
                } else {
                    if (throwable != null) {
                        this.queue.clear();
                        observer.onError(throwable);
                        this.worker.dispose();
                        return true;
                    }
                    if (!bl2) return false;
                    {
                        observer.onComplete();
                        this.worker.dispose();
                        return true;
                    }
                }
            }
        }

        @Override
        public void clear() {
            this.queue.clear();
        }

        @Override
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.s.dispose();
                this.worker.dispose();
                if (this.getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void drainFused() {
            int n;
            int n2 = 1;
            do {
                if (this.cancelled) {
                    return;
                }
                boolean bl = this.done;
                Throwable throwable = this.error;
                if (!this.delayError && bl && throwable != null) {
                    this.actual.onError(this.error);
                    this.worker.dispose();
                    return;
                }
                this.actual.onNext(null);
                if (bl) {
                    throwable = this.error;
                    if (throwable != null) {
                        this.actual.onError(throwable);
                    } else {
                        this.actual.onComplete();
                    }
                    this.worker.dispose();
                    return;
                }
                n2 = n = this.addAndGet(-n2);
            } while (n != 0);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        void drainNormal() {
            var1_1 = 1;
            var5_2 = this.queue;
            var6_3 = this.actual;
            do {
                if (!this.checkTerminated(this.done, var5_2.isEmpty(), var6_3)) ** GOTO lbl8
                return;
lbl-1000:
                // 1 sources
                {
                    var6_3.onNext(var7_7);
lbl8:
                    // 2 sources
                    var4_6 = this.done;
                    var7_7 = var5_2.poll();
                    var3_5 = var7_7 == null;
                    if (this.checkTerminated(var4_6, var3_5, var6_3) != false) return;
                    ** while (!var3_5)
                }
lbl14:
                // 1 sources
                var1_1 = var2_4 = this.addAndGet(-var1_1);
            } while (var2_4 != 0);
            return;
            catch (Throwable var7_8) {
                Exceptions.throwIfFatal(var7_8);
                this.s.dispose();
                var5_2.clear();
                var6_3.onError(var7_8);
                this.worker.dispose();
                return;
            }
        }

        @Override
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.schedule();
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.done) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            this.error = throwable;
            this.done = true;
            this.schedule();
        }

        @Override
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 2) {
                this.queue.offer(t);
            }
            this.schedule();
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            block5: {
                int n;
                block6: {
                    block4: {
                        if (!DisposableHelper.validate(this.s, disposable)) break block4;
                        this.s = disposable;
                        if (!(disposable instanceof QueueDisposable)) break block5;
                        n = (disposable = (QueueDisposable)disposable).requestFusion(7);
                        if (n != 1) break block6;
                        this.sourceMode = n;
                        this.queue = disposable;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        this.schedule();
                    }
                    return;
                }
                if (n == 2) {
                    this.sourceMode = n;
                    this.queue = disposable;
                    this.actual.onSubscribe(this);
                    return;
                }
            }
            this.queue = new SpscLinkedArrayQueue(this.bufferSize);
            this.actual.onSubscribe(this);
        }

        @Override
        public T poll() throws Exception {
            return this.queue.poll();
        }

        @Override
        public int requestFusion(int n) {
            if ((n & 2) != 0) {
                this.outputFused = true;
                return 2;
            }
            return 0;
        }

        @Override
        public void run() {
            if (this.outputFused) {
                this.drainFused();
                return;
            }
            this.drainNormal();
        }

        void schedule() {
            if (this.getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }
    }

}


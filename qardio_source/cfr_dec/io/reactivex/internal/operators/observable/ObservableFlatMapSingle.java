/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  io.reactivex.internal.operators.observable.ObservableFlatMapSingle$FlatMapSingleObserver.io.reactivex.internal.operators.observable.ObservableFlatMapSingle
 *  io.reactivex.internal.operators.observable.ObservableFlatMapSingle$FlatMapSingleObserver.io.reactivex.internal.operators.observable.ObservableFlatMapSingle$FlatMapSingleObserver
 *  io.reactivex.internal.operators.observable.ObservableFlatMapSingle$FlatMapSingleObserver.io.reactivex.internal.operators.observable.ObservableFlatMapSingle$FlatMapSingleObserver$InnerObserver
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;
import io.reactivex.internal.operators.observable.ObservableFlatMapSingle;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMapSingle<T, R>
extends AbstractObservableWithUpstream<T, R> {
    final boolean delayErrors;
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;

    public ObservableFlatMapSingle(ObservableSource<T> observableSource, Function<? super T, ? extends SingleSource<? extends R>> function, boolean bl) {
        super(observableSource);
        this.mapper = function;
        this.delayErrors = bl;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new FlatMapSingleObserver<T, R>(observer, this.mapper, this.delayErrors));
    }

    static final class FlatMapSingleObserver<T, R>
    extends AtomicInteger
    implements Observer<T>,
    Disposable {
        final AtomicInteger active;
        final Observer<? super R> actual;
        volatile boolean cancelled;
        Disposable d;
        final boolean delayErrors;
        final AtomicThrowable errors;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        final AtomicReference<SpscLinkedArrayQueue<R>> queue;
        final CompositeDisposable set;

        FlatMapSingleObserver(Observer<? super R> observer, Function<? super T, ? extends SingleSource<? extends R>> function, boolean bl) {
            this.actual = observer;
            this.mapper = function;
            this.delayErrors = bl;
            this.set = new CompositeDisposable();
            this.errors = new AtomicThrowable();
            this.active = new AtomicInteger(1);
            this.queue = new AtomicReference();
        }

        void clear() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
            if (spscLinkedArrayQueue != null) {
                spscLinkedArrayQueue.clear();
            }
        }

        @Override
        public void dispose() {
            this.cancelled = true;
            this.d.dispose();
            this.set.dispose();
        }

        void drain() {
            if (this.getAndIncrement() == 0) {
                this.drainLoop();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void drainLoop() {
            int n = 1;
            Observer<R> observer = this.actual;
            AtomicInteger atomicInteger = this.active;
            AtomicReference<SpscLinkedArrayQueue<R>> atomicReference = this.queue;
            do {
                Object object;
                if (this.cancelled) {
                    this.clear();
                    return;
                }
                if (!this.delayErrors && (Throwable)this.errors.get() != null) {
                    object = this.errors.terminate();
                    this.clear();
                    observer.onError((Throwable)object);
                    return;
                }
                int n2 = atomicInteger.get() == 0 ? 1 : 0;
                object = atomicReference.get();
                object = object != null ? ((SpscLinkedArrayQueue)object).poll() : null;
                boolean bl = object == null;
                if (n2 != 0 && bl) {
                    object = this.errors.terminate();
                    if (object != null) {
                        observer.onError((Throwable)object);
                        return;
                    }
                    observer.onComplete();
                    return;
                }
                if (bl) {
                    n = n2 = this.addAndGet(-n);
                    if (n2 != 0) continue;
                    return;
                }
                observer.onNext(object);
            } while (true);
        }

        SpscLinkedArrayQueue<R> getOrCreateQueue() {
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue;
            do {
                if ((spscLinkedArrayQueue = this.queue.get()) == null) continue;
                return spscLinkedArrayQueue;
            } while (!this.queue.compareAndSet(null, spscLinkedArrayQueue = new SpscLinkedArrayQueue(Observable.bufferSize())));
            return spscLinkedArrayQueue;
        }

        void innerError(ObservableFlatMapSingle.FlatMapSingleObserver.InnerObserver innerObserver, Throwable throwable) {
            this.set.delete((Disposable)innerObserver);
            if (this.errors.addThrowable(throwable)) {
                if (!this.delayErrors) {
                    this.d.dispose();
                    this.set.dispose();
                }
                this.active.decrementAndGet();
                this.drain();
                return;
            }
            RxJavaPlugins.onError(throwable);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void innerSuccess(ObservableFlatMapSingle.FlatMapSingleObserver.InnerObserver object, R r) {
            boolean bl = true;
            this.set.delete((Disposable)object);
            if (this.get() == 0 && this.compareAndSet(0, 1)) {
                this.actual.onNext(r);
                if (this.active.decrementAndGet() != 0) {
                    bl = false;
                }
                object = this.queue.get();
                if (bl && (object == null || ((SpscLinkedArrayQueue)object).isEmpty())) {
                    object = this.errors.terminate();
                    if (object == null) {
                        this.actual.onComplete();
                        return;
                    }
                    this.actual.onError((Throwable)object);
                    return;
                }
                if (this.decrementAndGet() == 0) {
                    return;
                }
            } else {
                object = this.getOrCreateQueue();
                synchronized (object) {
                    ((SpscLinkedArrayQueue)object).offer(r);
                }
                this.active.decrementAndGet();
                if (this.getAndIncrement() != 0) return;
            }
            this.drainLoop();
        }

        @Override
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override
        public void onComplete() {
            this.active.decrementAndGet();
            this.drain();
        }

        @Override
        public void onError(Throwable throwable) {
            this.active.decrementAndGet();
            if (this.errors.addThrowable(throwable)) {
                if (!this.delayErrors) {
                    this.set.dispose();
                }
                this.drain();
                return;
            }
            RxJavaPlugins.onError(throwable);
        }

        @Override
        public void onNext(T object) {
            try {
                object = ObjectHelper.requireNonNull(this.mapper.apply(object), "The mapper returned a null SingleSource");
                this.active.getAndIncrement();
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.d.dispose();
                this.onError(throwable);
                return;
            }
            InnerObserver innerObserver = new InnerObserver();
            if (!this.cancelled && this.set.add(innerObserver)) {
                object.subscribe(innerObserver);
            }
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        final class InnerObserver
        extends AtomicReference<Disposable>
        implements SingleObserver<R>,
        Disposable {
            InnerObserver() {
            }

            @Override
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override
            public boolean isDisposed() {
                return DisposableHelper.isDisposed((Disposable)this.get());
            }

            @Override
            public void onError(Throwable throwable) {
                FlatMapSingleObserver.this.innerError(this, throwable);
            }

            @Override
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override
            public void onSuccess(R r) {
                FlatMapSingleObserver.this.innerSuccess(this, r);
            }
        }

    }

}


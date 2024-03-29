/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableRetryPredicate<T>
extends AbstractObservableWithUpstream<T, T> {
    final long count;
    final Predicate<? super Throwable> predicate;

    public ObservableRetryPredicate(Observable<T> observable, long l, Predicate<? super Throwable> predicate) {
        super(observable);
        this.predicate = predicate;
        this.count = l;
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        observer.onSubscribe(sequentialDisposable);
        new RepeatObserver<T>(observer, this.count, this.predicate, sequentialDisposable, this.source).subscribeNext();
    }

    static final class RepeatObserver<T>
    extends AtomicInteger
    implements Observer<T> {
        final Observer<? super T> actual;
        final Predicate<? super Throwable> predicate;
        long remaining;
        final SequentialDisposable sa;
        final ObservableSource<? extends T> source;

        RepeatObserver(Observer<? super T> observer, long l, Predicate<? super Throwable> predicate, SequentialDisposable sequentialDisposable, ObservableSource<? extends T> observableSource) {
            this.actual = observer;
            this.sa = sequentialDisposable;
            this.source = observableSource;
            this.predicate = predicate;
            this.remaining = l;
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            long l = this.remaining;
            if (l != Long.MAX_VALUE) {
                this.remaining = l - 1L;
            }
            if (l == 0L) {
                this.actual.onError(throwable);
                return;
            }
            try {
                boolean bl = this.predicate.test(throwable);
                if (!bl) {
                    this.actual.onError(throwable);
                    return;
                }
            }
            catch (Throwable throwable2) {
                Exceptions.throwIfFatal(throwable2);
                this.actual.onError(new CompositeException(throwable, throwable2));
                return;
            }
            this.subscribeNext();
        }

        @Override
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.sa.update(disposable);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void subscribeNext() {
            int n;
            if (this.getAndIncrement() != 0) return;
            int n2 = 1;
            do {
                if (this.sa.isDisposed()) {
                    return;
                }
                this.source.subscribe(this);
                n2 = n = this.addAndGet(-n2);
            } while (n != 0);
        }
    }

}


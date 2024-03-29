/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;

public final class ObservableIgnoreElements<T>
extends AbstractObservableWithUpstream<T, T> {
    public ObservableIgnoreElements(ObservableSource<T> observableSource) {
        super(observableSource);
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new IgnoreObservable<T>(observer));
    }

    static final class IgnoreObservable<T>
    implements Observer<T>,
    Disposable {
        final Observer<? super T> actual;
        Disposable d;

        IgnoreObservable(Observer<? super T> observer) {
            this.actual = observer;
        }

        @Override
        public void dispose() {
            this.d.dispose();
        }

        @Override
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            this.actual.onError(throwable);
        }

        @Override
        public void onNext(T t) {
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.d = disposable;
            this.actual.onSubscribe(this);
        }
    }

}


/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.single;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class SingleToObservable<T>
extends Observable<T> {
    final SingleSource<? extends T> source;

    public SingleToObservable(SingleSource<? extends T> singleSource) {
        this.source = singleSource;
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new SingleToObservableObserver<T>(observer));
    }

    static final class SingleToObservableObserver<T>
    implements SingleObserver<T>,
    Disposable {
        final Observer<? super T> actual;
        Disposable d;

        SingleToObservableObserver(Observer<? super T> observer) {
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
        public void onError(Throwable throwable) {
            this.actual.onError(throwable);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onSuccess(T t) {
            this.actual.onNext(t);
            this.actual.onComplete();
        }
    }

}


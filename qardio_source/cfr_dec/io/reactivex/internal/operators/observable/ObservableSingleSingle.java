/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;

public final class ObservableSingleSingle<T>
extends Single<T> {
    final T defaultValue;
    final ObservableSource<? extends T> source;

    public ObservableSingleSingle(ObservableSource<? extends T> observableSource, T t) {
        this.source = observableSource;
        this.defaultValue = t;
    }

    @Override
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new SingleElementObserver<T>(singleObserver, (T)this.defaultValue));
    }

    static final class SingleElementObserver<T>
    implements Observer<T>,
    Disposable {
        final SingleObserver<? super T> actual;
        final T defaultValue;
        boolean done;
        Disposable s;
        T value;

        SingleElementObserver(SingleObserver<? super T> singleObserver, T t) {
            this.actual = singleObserver;
            this.defaultValue = t;
        }

        @Override
        public void dispose() {
            this.s.dispose();
        }

        @Override
        public boolean isDisposed() {
            return this.s.isDisposed();
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            T t = this.value;
            this.value = null;
            T t2 = t;
            if (t == null) {
                t2 = this.defaultValue;
            }
            if (t2 != null) {
                this.actual.onSuccess(t2);
                return;
            }
            this.actual.onError(new NoSuchElementException());
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.done) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            this.done = true;
            this.actual.onError(throwable);
        }

        @Override
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            if (this.value != null) {
                this.done = true;
                this.s.dispose();
                this.actual.onError(new IllegalArgumentException("Sequence contains more than one element!"));
                return;
            }
            this.value = t;
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.s, disposable)) {
                this.s = disposable;
                this.actual.onSubscribe(this);
            }
        }
    }

}


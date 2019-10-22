/*
 * Decompiled with CFR 0.147.
 */
package com.jakewharton.retrofit2.adapter.rxjava2;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Response;

final class BodyObservable<T>
extends Observable<T> {
    private final Observable<Response<T>> upstream;

    BodyObservable(Observable<Response<T>> observable) {
        this.upstream = observable;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        this.upstream.subscribe(new BodyObserver<T>(observer));
    }

    private static class BodyObserver<R>
    implements Observer<Response<R>> {
        private final Observer<? super R> observer;
        private boolean terminated;

        BodyObserver(Observer<? super R> observer) {
            this.observer = observer;
        }

        @Override
        public void onComplete() {
            if (!this.terminated) {
                this.observer.onComplete();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!this.terminated) {
                this.observer.onError(throwable);
                return;
            }
            AssertionError assertionError = new AssertionError((Object)"This should never happen! Report as a bug with the full stacktrace.");
            ((Throwable)((Object)assertionError)).initCause(throwable);
            RxJavaPlugins.onError((Throwable)((Object)assertionError));
        }

        @Override
        public void onNext(Response<R> object) {
            if (((Response)object).isSuccessful()) {
                this.observer.onNext(((Response)object).body());
                return;
            }
            this.terminated = true;
            object = new HttpException((Response<?>)object);
            try {
                this.observer.onError((Throwable)object);
                return;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                RxJavaPlugins.onError(new CompositeException(new Throwable[]{object, throwable}));
                return;
            }
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.observer.onSubscribe(disposable);
        }
    }

}


/*
 * Decompiled with CFR 0.147.
 */
package com.jakewharton.retrofit2.adapter.rxjava2;

import com.jakewharton.retrofit2.adapter.rxjava2.Result;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Response;

final class ResultObservable<T>
extends Observable<Result<T>> {
    private final Observable<Response<T>> upstream;

    ResultObservable(Observable<Response<T>> observable) {
        this.upstream = observable;
    }

    @Override
    protected void subscribeActual(Observer<? super Result<T>> observer) {
        this.upstream.subscribe(new ResultObserver(observer));
    }

    private static class ResultObserver<R>
    implements Observer<Response<R>> {
        private final Observer<? super Result<R>> observer;

        ResultObserver(Observer<? super Result<R>> observer) {
            this.observer = observer;
        }

        @Override
        public void onComplete() {
            this.observer.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            try {
                this.observer.onNext(Result.error(throwable));
                this.observer.onComplete();
                return;
            }
            catch (Throwable throwable2) {
                try {
                    this.observer.onError(throwable2);
                    return;
                }
                catch (Throwable throwable3) {
                    Exceptions.throwIfFatal(throwable3);
                    RxJavaPlugins.onError(new CompositeException(throwable2, throwable3));
                    return;
                }
            }
        }

        @Override
        public void onNext(Response<R> response) {
            this.observer.onNext(Result.response(response));
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.observer.onSubscribe(disposable);
        }
    }

}


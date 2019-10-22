/*
 * Decompiled with CFR 0.147.
 */
package com.jakewharton.retrofit2.adapter.rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Call;
import retrofit2.Response;

final class CallObservable<T>
extends Observable<Response<T>> {
    private final Call<T> originalCall;

    CallObservable(Call<T> call) {
        this.originalCall = call;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void subscribeActual(Observer<? super Response<T>> observer) {
        boolean bl;
        Call<T> call = this.originalCall.clone();
        observer.onSubscribe(new CallDisposable(call));
        boolean bl2 = bl = false;
        try {
            Response<T> response = call.execute();
            bl2 = bl;
            if (!call.isCanceled()) {
                bl2 = bl;
                observer.onNext(response);
            }
            bl2 = bl;
            if (call.isCanceled()) return;
            {
                bl2 = true;
                observer.onComplete();
                return;
            }
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            if (bl2) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            if (call.isCanceled()) return;
            try {
                observer.onError(throwable);
                return;
            }
            catch (Throwable throwable2) {
                Exceptions.throwIfFatal(throwable2);
                RxJavaPlugins.onError(new CompositeException(throwable, throwable2));
                return;
            }
        }
    }

    private static final class CallDisposable
    implements Disposable {
        private final Call<?> call;

        CallDisposable(Call<?> call) {
            this.call = call;
        }

        @Override
        public void dispose() {
            this.call.cancel();
        }

        @Override
        public boolean isDisposed() {
            return this.call.isCanceled();
        }
    }

}


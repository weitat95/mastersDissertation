/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;

public final class SingleDoOnError<T>
extends Single<T> {
    final Consumer<? super Throwable> onError;
    final SingleSource<T> source;

    public SingleDoOnError(SingleSource<T> singleSource, Consumer<? super Throwable> consumer) {
        this.source = singleSource;
        this.onError = consumer;
    }

    @Override
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new DoOnError(singleObserver));
    }

    final class DoOnError
    implements SingleObserver<T> {
        private final SingleObserver<? super T> s;

        DoOnError(SingleObserver<? super T> singleObserver) {
            this.s = singleObserver;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onError(Throwable throwable) {
            try {
                SingleDoOnError.this.onError.accept(throwable);
            }
            catch (Throwable throwable2) {
                Exceptions.throwIfFatal(throwable2);
                throwable = new CompositeException(throwable, throwable2);
            }
            this.s.onError(throwable);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.s.onSubscribe(disposable);
        }

        @Override
        public void onSuccess(T t) {
            this.s.onSuccess(t);
        }
    }

}


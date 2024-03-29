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
import io.reactivex.functions.Function;

public final class SingleOnErrorReturn<T>
extends Single<T> {
    final SingleSource<? extends T> source;
    final T value;
    final Function<? super Throwable, ? extends T> valueSupplier;

    public SingleOnErrorReturn(SingleSource<? extends T> singleSource, Function<? super Throwable, ? extends T> function, T t) {
        this.source = singleSource;
        this.valueSupplier = function;
        this.value = t;
    }

    @Override
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new OnErrorReturn(singleObserver));
    }

    final class OnErrorReturn
    implements SingleObserver<T> {
        private final SingleObserver<? super T> observer;

        OnErrorReturn(SingleObserver<? super T> singleObserver) {
            this.observer = singleObserver;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onError(Throwable throwable) {
            Object object;
            if (SingleOnErrorReturn.this.valueSupplier != null) {
                try {
                    object = SingleOnErrorReturn.this.valueSupplier.apply(throwable);
                }
                catch (Throwable throwable2) {
                    Exceptions.throwIfFatal(throwable2);
                    this.observer.onError(new CompositeException(throwable, throwable2));
                    return;
                }
            } else {
                object = SingleOnErrorReturn.this.value;
            }
            if (object == null) {
                object = new NullPointerException("Value supplied was null");
                ((Throwable)object).initCause(throwable);
                this.observer.onError((Throwable)object);
                return;
            }
            this.observer.onSuccess(object);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.observer.onSubscribe(disposable);
        }

        @Override
        public void onSuccess(T t) {
            this.observer.onSuccess(t);
        }
    }

}


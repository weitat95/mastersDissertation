/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;

public final class SingleMap<T, R>
extends Single<R> {
    final Function<? super T, ? extends R> mapper;
    final SingleSource<? extends T> source;

    public SingleMap(SingleSource<? extends T> singleSource, Function<? super T, ? extends R> function) {
        this.source = singleSource;
        this.mapper = function;
    }

    @Override
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.source.subscribe(new MapSingleObserver<T, R>(singleObserver, this.mapper));
    }

    static final class MapSingleObserver<T, R>
    implements SingleObserver<T> {
        final Function<? super T, ? extends R> mapper;
        final SingleObserver<? super R> t;

        MapSingleObserver(SingleObserver<? super R> singleObserver, Function<? super T, ? extends R> function) {
            this.t = singleObserver;
            this.mapper = function;
        }

        @Override
        public void onError(Throwable throwable) {
            this.t.onError(throwable);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.t.onSubscribe(disposable);
        }

        @Override
        public void onSuccess(T object) {
            try {
                object = ObjectHelper.requireNonNull(this.mapper.apply(object), "The mapper function returned a null value.");
                this.t.onSuccess(object);
                return;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.onError(throwable);
                return;
            }
        }
    }

}


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
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleResumeNext<T>
extends Single<T> {
    final Function<? super Throwable, ? extends SingleSource<? extends T>> nextFunction;
    final SingleSource<? extends T> source;

    public SingleResumeNext(SingleSource<? extends T> singleSource, Function<? super Throwable, ? extends SingleSource<? extends T>> function) {
        this.source = singleSource;
        this.nextFunction = function;
    }

    @Override
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new ResumeMainSingleObserver<T>(singleObserver, this.nextFunction));
    }

    static final class ResumeMainSingleObserver<T>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>,
    Disposable {
        final SingleObserver<? super T> actual;
        final Function<? super Throwable, ? extends SingleSource<? extends T>> nextFunction;

        ResumeMainSingleObserver(SingleObserver<? super T> singleObserver, Function<? super Throwable, ? extends SingleSource<? extends T>> function) {
            this.actual = singleObserver;
            this.nextFunction = function;
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable)this.get());
        }

        @Override
        public void onError(Throwable throwable) {
            try {
                SingleSource<? super T> singleSource = ObjectHelper.requireNonNull(this.nextFunction.apply(throwable), "The nextFunction returned a null SingleSource.");
                singleSource.subscribe(new ResumeSingleObserver<T>(this, this.actual));
                return;
            }
            catch (Throwable throwable2) {
                Exceptions.throwIfFatal(throwable2);
                this.actual.onError(new CompositeException(throwable, throwable2));
                return;
            }
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onSuccess(T t) {
            this.actual.onSuccess(t);
        }
    }

}


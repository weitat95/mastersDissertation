/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.concurrent.Callable;

public final class SingleFromCallable<T>
extends Single<T> {
    final Callable<? extends T> callable;

    public SingleFromCallable(Callable<? extends T> callable) {
        this.callable = callable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        singleObserver.onSubscribe(EmptyDisposable.INSTANCE);
        try {
            T t = this.callable.call();
            if (t != null) {
                singleObserver.onSuccess(t);
                return;
            }
            singleObserver.onError(new NullPointerException("The callable returned a null value"));
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            singleObserver.onError(throwable);
            return;
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class SingleError<T>
extends Single<T> {
    final Callable<? extends Throwable> errorSupplier;

    public SingleError(Callable<? extends Throwable> callable) {
        this.errorSupplier = callable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        Throwable throwable;
        try {
            throwable = ObjectHelper.requireNonNull(this.errorSupplier.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources.");
        }
        catch (Throwable throwable2) {
            Exceptions.throwIfFatal(throwable2);
        }
        EmptyDisposable.error(throwable, singleObserver);
    }
}


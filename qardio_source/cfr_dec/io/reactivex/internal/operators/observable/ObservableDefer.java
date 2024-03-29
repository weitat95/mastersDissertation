/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class ObservableDefer<T>
extends Observable<T> {
    final Callable<? extends ObservableSource<? extends T>> supplier;

    public ObservableDefer(Callable<? extends ObservableSource<? extends T>> callable) {
        this.supplier = callable;
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        try {
            ObservableSource<? super T> observableSource = ObjectHelper.requireNonNull(this.supplier.call(), "null ObservableSource supplied");
            observableSource.subscribe(observer);
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            EmptyDisposable.error(throwable, observer);
            return;
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.operators.observable.ObservableScalarXMap;

public final class ObservableJust<T>
extends Observable<T>
implements ScalarCallable<T> {
    private final T value;

    public ObservableJust(T t) {
        this.value = t;
    }

    @Override
    public T call() {
        return this.value;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        ObservableScalarXMap.ScalarDisposable<T> scalarDisposable = new ObservableScalarXMap.ScalarDisposable<T>(observer, this.value);
        observer.onSubscribe(scalarDisposable);
        scalarDisposable.run();
    }
}


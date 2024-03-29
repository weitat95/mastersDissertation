/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.fuseable.ScalarCallable;

public final class MaybeJust<T>
extends Maybe<T>
implements ScalarCallable<T> {
    final T value;

    public MaybeJust(T t) {
        this.value = t;
    }

    @Override
    public T call() {
        return this.value;
    }

    @Override
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        maybeObserver.onSubscribe(Disposables.disposed());
        maybeObserver.onSuccess(this.value);
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.internal.disposables.EmptyDisposable;

public final class CompletableError
extends Completable {
    final Throwable error;

    public CompletableError(Throwable throwable) {
        this.error = throwable;
    }

    @Override
    protected void subscribeActual(CompletableObserver completableObserver) {
        EmptyDisposable.error(this.error, completableObserver);
    }
}


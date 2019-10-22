/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;

public final class CompletableFromUnsafeSource
extends Completable {
    final CompletableSource source;

    public CompletableFromUnsafeSource(CompletableSource completableSource) {
        this.source = completableSource;
    }

    @Override
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(completableObserver);
    }
}


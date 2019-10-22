/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;

public final class CompletableFromRunnable
extends Completable {
    final Runnable runnable;

    public CompletableFromRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void subscribeActual(CompletableObserver completableObserver) {
        Disposable disposable = Disposables.empty();
        completableObserver.onSubscribe(disposable);
        try {
            this.runnable.run();
            if (disposable.isDisposed()) return;
            {
                completableObserver.onComplete();
                return;
            }
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            if (disposable.isDisposed()) return;
            completableObserver.onError(throwable);
            return;
        }
    }
}


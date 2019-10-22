/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class EmptyCompletableObserver
extends AtomicReference<Disposable>
implements CompletableObserver,
Disposable {
    @Override
    public void dispose() {
        DisposableHelper.dispose(this);
    }

    @Override
    public boolean isDisposed() {
        return this.get() == DisposableHelper.DISPOSED;
    }

    @Override
    public void onComplete() {
        this.lazySet(DisposableHelper.DISPOSED);
    }

    @Override
    public void onError(Throwable throwable) {
        this.lazySet(DisposableHelper.DISPOSED);
        RxJavaPlugins.onError(new OnErrorNotImplementedException(throwable));
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        DisposableHelper.setOnce(this, disposable);
    }
}


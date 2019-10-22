/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Cancellable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class CancellableDisposable
extends AtomicReference<Cancellable>
implements Disposable {
    public CancellableDisposable(Cancellable cancellable) {
        super(cancellable);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void dispose() {
        Cancellable cancellable;
        if (this.get() == null || (cancellable = (Cancellable)this.getAndSet(null)) == null) return;
        try {
            cancellable.cancel();
            return;
        }
        catch (Exception exception) {
            Exceptions.throwIfFatal(exception);
            RxJavaPlugins.onError(exception);
            return;
        }
    }

    @Override
    public boolean isDisposed() {
        return this.get() == null;
    }
}


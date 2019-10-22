/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

abstract class ReferenceDisposable<T>
extends AtomicReference<T>
implements Disposable {
    ReferenceDisposable(T t) {
        super(ObjectHelper.requireNonNull(t, "value is null"));
    }

    @Override
    public final void dispose() {
        T t;
        if (this.get() != null && (t = this.getAndSet(null)) != null) {
            this.onDisposed(t);
        }
    }

    @Override
    public final boolean isDisposed() {
        return this.get() == null;
    }

    protected abstract void onDisposed(T var1);
}


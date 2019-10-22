/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.functions.Cancellable;

public interface SingleEmitter<T> {
    public boolean isDisposed();

    public void onError(Throwable var1);

    public void onSuccess(T var1);

    public void setCancellable(Cancellable var1);
}


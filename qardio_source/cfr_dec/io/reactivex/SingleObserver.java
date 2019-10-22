/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.disposables.Disposable;

public interface SingleObserver<T> {
    public void onError(Throwable var1);

    public void onSubscribe(Disposable var1);

    public void onSuccess(T var1);
}


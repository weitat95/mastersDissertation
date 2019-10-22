/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.disposables.Disposable;

public interface CompletableObserver {
    public void onComplete();

    public void onError(Throwable var1);

    public void onSubscribe(Disposable var1);
}


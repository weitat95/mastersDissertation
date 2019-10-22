/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.MaybeObserver;

public interface MaybeSource<T> {
    public void subscribe(MaybeObserver<? super T> var1);
}


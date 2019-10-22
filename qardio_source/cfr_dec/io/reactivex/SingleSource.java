/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.SingleObserver;

public interface SingleSource<T> {
    public void subscribe(SingleObserver<? super T> var1);
}


/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.ObservableEmitter;

public interface ObservableOnSubscribe<T> {
    public void subscribe(ObservableEmitter<T> var1) throws Exception;
}


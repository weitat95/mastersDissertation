/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.SingleEmitter;

public interface SingleOnSubscribe<T> {
    public void subscribe(SingleEmitter<T> var1) throws Exception;
}


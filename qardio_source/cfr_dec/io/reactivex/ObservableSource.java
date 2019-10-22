/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.Observer;

public interface ObservableSource<T> {
    public void subscribe(Observer<? super T> var1);
}


/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public interface ObservableTransformer<Upstream, Downstream> {
    public ObservableSource<Downstream> apply(Observable<Upstream> var1);
}


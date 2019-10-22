/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.Single;
import io.reactivex.SingleSource;

public interface SingleTransformer<Upstream, Downstream> {
    public SingleSource<Downstream> apply(Single<Upstream> var1);
}


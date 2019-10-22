/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;

public interface MaybeTransformer<Upstream, Downstream> {
    public MaybeSource<Downstream> apply(Maybe<Upstream> var1);
}


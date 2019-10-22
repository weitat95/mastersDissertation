/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;

public interface CompletableTransformer {
    public CompletableSource apply(Completable var1);
}


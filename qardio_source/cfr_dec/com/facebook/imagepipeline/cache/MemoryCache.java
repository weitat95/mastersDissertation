/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.cache;

import com.facebook.common.references.CloseableReference;
import javax.annotation.Nullable;

public interface MemoryCache<K, V> {
    @Nullable
    public CloseableReference<V> cache(K var1, CloseableReference<V> var2);

    @Nullable
    public CloseableReference<V> get(K var1);
}


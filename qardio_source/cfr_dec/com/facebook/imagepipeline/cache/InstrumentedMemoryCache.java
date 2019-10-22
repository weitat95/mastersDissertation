/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.cache.MemoryCacheTracker;

public class InstrumentedMemoryCache<K, V>
implements MemoryCache<K, V> {
    private final MemoryCache<K, V> mDelegate;
    private final MemoryCacheTracker mTracker;

    public InstrumentedMemoryCache(MemoryCache<K, V> memoryCache, MemoryCacheTracker memoryCacheTracker) {
        this.mDelegate = memoryCache;
        this.mTracker = memoryCacheTracker;
    }

    @Override
    public CloseableReference<V> cache(K k, CloseableReference<V> closeableReference) {
        this.mTracker.onCachePut();
        return this.mDelegate.cache(k, closeableReference);
    }

    @Override
    public CloseableReference<V> get(K k) {
        CloseableReference<V> closeableReference = this.mDelegate.get(k);
        if (closeableReference == null) {
            this.mTracker.onCacheMiss();
            return closeableReference;
        }
        this.mTracker.onCacheHit(k);
        return closeableReference;
    }
}


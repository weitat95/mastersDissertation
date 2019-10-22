/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

public interface MemoryCacheTracker<K> {
    public void onCacheHit(K var1);

    public void onCacheMiss();

    public void onCachePut();
}


/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.CountingMemoryCache;

public interface ImageCacheStatsTracker {
    public void onBitmapCacheHit(CacheKey var1);

    public void onBitmapCacheMiss();

    public void onBitmapCachePut();

    public void onDiskCacheGetFail();

    public void onDiskCacheHit();

    public void onDiskCacheMiss();

    public void onMemoryCacheHit(CacheKey var1);

    public void onMemoryCacheMiss();

    public void onMemoryCachePut();

    public void onStagingAreaHit(CacheKey var1);

    public void onStagingAreaMiss();

    public void registerBitmapMemoryCache(CountingMemoryCache<?, ?> var1);

    public void registerEncodedMemoryCache(CountingMemoryCache<?, ?> var1);
}


/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;

public class NoOpImageCacheStatsTracker
implements ImageCacheStatsTracker {
    private static NoOpImageCacheStatsTracker sInstance = null;

    private NoOpImageCacheStatsTracker() {
    }

    public static NoOpImageCacheStatsTracker getInstance() {
        synchronized (NoOpImageCacheStatsTracker.class) {
            if (sInstance == null) {
                sInstance = new NoOpImageCacheStatsTracker();
            }
            NoOpImageCacheStatsTracker noOpImageCacheStatsTracker = sInstance;
            return noOpImageCacheStatsTracker;
        }
    }

    @Override
    public void onBitmapCacheHit(CacheKey cacheKey) {
    }

    @Override
    public void onBitmapCacheMiss() {
    }

    @Override
    public void onBitmapCachePut() {
    }

    @Override
    public void onDiskCacheGetFail() {
    }

    @Override
    public void onDiskCacheHit() {
    }

    @Override
    public void onDiskCacheMiss() {
    }

    @Override
    public void onMemoryCacheHit(CacheKey cacheKey) {
    }

    @Override
    public void onMemoryCacheMiss() {
    }

    @Override
    public void onMemoryCachePut() {
    }

    @Override
    public void onStagingAreaHit(CacheKey cacheKey) {
    }

    @Override
    public void onStagingAreaMiss() {
    }

    @Override
    public void registerBitmapMemoryCache(CountingMemoryCache<?, ?> countingMemoryCache) {
    }

    @Override
    public void registerEncodedMemoryCache(CountingMemoryCache<?, ?> countingMemoryCache) {
    }
}


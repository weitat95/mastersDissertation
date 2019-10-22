/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.cache.InstrumentedMemoryCache;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.cache.MemoryCacheTracker;
import com.facebook.imagepipeline.image.CloseableImage;

public class BitmapMemoryCacheFactory {
    public static MemoryCache<CacheKey, CloseableImage> get(CountingMemoryCache<CacheKey, CloseableImage> countingMemoryCache, final ImageCacheStatsTracker imageCacheStatsTracker) {
        imageCacheStatsTracker.registerBitmapMemoryCache(countingMemoryCache);
        return new InstrumentedMemoryCache<CacheKey, CloseableImage>(countingMemoryCache, new MemoryCacheTracker<CacheKey>(){

            @Override
            public void onCacheHit(CacheKey cacheKey) {
                imageCacheStatsTracker.onBitmapCacheHit(cacheKey);
            }

            @Override
            public void onCacheMiss() {
                imageCacheStatsTracker.onBitmapCacheMiss();
            }

            @Override
            public void onCachePut() {
                imageCacheStatsTracker.onBitmapCachePut();
            }
        });
    }

}


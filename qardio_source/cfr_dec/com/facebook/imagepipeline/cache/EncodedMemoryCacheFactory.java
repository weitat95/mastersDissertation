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
import com.facebook.imagepipeline.memory.PooledByteBuffer;

public class EncodedMemoryCacheFactory {
    public static MemoryCache<CacheKey, PooledByteBuffer> get(CountingMemoryCache<CacheKey, PooledByteBuffer> countingMemoryCache, final ImageCacheStatsTracker imageCacheStatsTracker) {
        imageCacheStatsTracker.registerEncodedMemoryCache(countingMemoryCache);
        return new InstrumentedMemoryCache<CacheKey, PooledByteBuffer>(countingMemoryCache, new MemoryCacheTracker<CacheKey>(){

            @Override
            public void onCacheHit(CacheKey cacheKey) {
                imageCacheStatsTracker.onMemoryCacheHit(cacheKey);
            }

            @Override
            public void onCacheMiss() {
                imageCacheStatsTracker.onMemoryCacheMiss();
            }

            @Override
            public void onCachePut() {
                imageCacheStatsTracker.onMemoryCachePut();
            }
        });
    }

}


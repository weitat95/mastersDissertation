/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.cache.NativeMemoryCacheTrimStrategy;
import com.facebook.imagepipeline.cache.ValueDescriptor;
import com.facebook.imagepipeline.memory.PooledByteBuffer;

public class EncodedCountingMemoryCacheFactory {
    public static CountingMemoryCache<CacheKey, PooledByteBuffer> get(Supplier<MemoryCacheParams> object, MemoryTrimmableRegistry memoryTrimmableRegistry, PlatformBitmapFactory platformBitmapFactory) {
        object = new CountingMemoryCache(new ValueDescriptor<PooledByteBuffer>(){

            @Override
            public int getSizeInBytes(PooledByteBuffer pooledByteBuffer) {
                return pooledByteBuffer.size();
            }
        }, new NativeMemoryCacheTrimStrategy(), (Supplier<MemoryCacheParams>)object, platformBitmapFactory, false);
        memoryTrimmableRegistry.registerMemoryTrimmable((MemoryTrimmable)object);
        return object;
    }

}


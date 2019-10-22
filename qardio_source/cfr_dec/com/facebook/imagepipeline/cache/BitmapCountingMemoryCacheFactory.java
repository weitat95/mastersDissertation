/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheTrimStrategy;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.cache.ValueDescriptor;
import com.facebook.imagepipeline.image.CloseableImage;

public class BitmapCountingMemoryCacheFactory {
    public static CountingMemoryCache<CacheKey, CloseableImage> get(Supplier<MemoryCacheParams> object, MemoryTrimmableRegistry memoryTrimmableRegistry, PlatformBitmapFactory platformBitmapFactory, boolean bl) {
        object = new CountingMemoryCache(new ValueDescriptor<CloseableImage>(){

            @Override
            public int getSizeInBytes(CloseableImage closeableImage) {
                return closeableImage.getSizeInBytes();
            }
        }, new BitmapMemoryCacheTrimStrategy(), (Supplier<MemoryCacheParams>)object, platformBitmapFactory, bl);
        memoryTrimmableRegistry.registerMemoryTrimmable((MemoryTrimmable)object);
        return object;
    }

}


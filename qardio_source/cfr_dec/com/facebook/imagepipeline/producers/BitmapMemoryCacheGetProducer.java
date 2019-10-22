/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.Producer;

public class BitmapMemoryCacheGetProducer
extends BitmapMemoryCacheProducer {
    public BitmapMemoryCacheGetProducer(MemoryCache<CacheKey, CloseableImage> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<CloseableReference<CloseableImage>> producer) {
        super(memoryCache, cacheKeyFactory, producer);
    }

    @Override
    protected String getProducerName() {
        return "BitmapMemoryCacheGetProducer";
    }

    @Override
    protected Consumer<CloseableReference<CloseableImage>> wrapConsumer(Consumer<CloseableReference<CloseableImage>> consumer, CacheKey cacheKey) {
        return consumer;
    }
}


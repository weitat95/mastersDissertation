/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 */
package com.facebook.imagepipeline.producers;

import android.util.Pair;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.producers.MultiplexProducer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.Closeable;

public class BitmapMemoryCacheKeyMultiplexProducer
extends MultiplexProducer<Pair<CacheKey, ImageRequest.RequestLevel>, CloseableReference<CloseableImage>> {
    private final CacheKeyFactory mCacheKeyFactory;

    public BitmapMemoryCacheKeyMultiplexProducer(CacheKeyFactory cacheKeyFactory, Producer producer) {
        super(producer);
        this.mCacheKeyFactory = cacheKeyFactory;
    }

    @Override
    public CloseableReference<CloseableImage> cloneOrNull(CloseableReference<CloseableImage> closeableReference) {
        return CloseableReference.cloneOrNull(closeableReference);
    }

    @Override
    protected Pair<CacheKey, ImageRequest.RequestLevel> getKey(ProducerContext producerContext) {
        return Pair.create((Object)this.mCacheKeyFactory.getBitmapCacheKey(producerContext.getImageRequest(), producerContext.getCallerContext()), (Object)((Object)producerContext.getLowestPermittedRequestLevel()));
    }
}


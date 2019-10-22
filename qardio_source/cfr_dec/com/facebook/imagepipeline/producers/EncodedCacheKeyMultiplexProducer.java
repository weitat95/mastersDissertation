/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 */
package com.facebook.imagepipeline.producers;

import android.util.Pair;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.MultiplexProducer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.Closeable;

public class EncodedCacheKeyMultiplexProducer
extends MultiplexProducer<Pair<CacheKey, ImageRequest.RequestLevel>, EncodedImage> {
    private final CacheKeyFactory mCacheKeyFactory;

    public EncodedCacheKeyMultiplexProducer(CacheKeyFactory cacheKeyFactory, Producer producer) {
        super(producer);
        this.mCacheKeyFactory = cacheKeyFactory;
    }

    @Override
    public EncodedImage cloneOrNull(EncodedImage encodedImage) {
        return EncodedImage.cloneOrNull(encodedImage);
    }

    @Override
    protected Pair<CacheKey, ImageRequest.RequestLevel> getKey(ProducerContext producerContext) {
        return Pair.create((Object)this.mCacheKeyFactory.getEncodedCacheKey(producerContext.getImageRequest(), producerContext.getCallerContext()), (Object)((Object)producerContext.getLowestPermittedRequestLevel()));
    }
}


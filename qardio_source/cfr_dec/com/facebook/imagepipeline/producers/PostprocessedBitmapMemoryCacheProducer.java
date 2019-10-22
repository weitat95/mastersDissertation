/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import java.util.Map;

public class PostprocessedBitmapMemoryCacheProducer
implements Producer<CloseableReference<CloseableImage>> {
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;
    private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

    public PostprocessedBitmapMemoryCacheProducer(MemoryCache<CacheKey, CloseableImage> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<CloseableReference<CloseableImage>> producer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = producer;
    }

    protected String getProducerName() {
        return "PostprocessedBitmapMemoryCacheProducer";
    }

    @Override
    public void produceResults(Consumer<CloseableReference<CloseableImage>> map, ProducerContext object) {
        String string2 = null;
        CachedPostprocessorConsumer cachedPostprocessorConsumer = null;
        ProducerListener producerListener = object.getListener();
        String string3 = object.getId();
        Object object2 = object.getImageRequest();
        Object object3 = object.getCallerContext();
        Postprocessor postprocessor = ((ImageRequest)object2).getPostprocessor();
        if (postprocessor == null || postprocessor.getPostprocessorCacheKey() == null) {
            this.mInputProducer.produceResults((Consumer<CloseableReference<CloseableImage>>)((Object)map), (ProducerContext)object);
            return;
        }
        producerListener.onProducerStart(string3, this.getProducerName());
        object3 = this.mCacheKeyFactory.getPostprocessedBitmapCacheKey((ImageRequest)object2, object3);
        object2 = this.mMemoryCache.get((CacheKey)object3);
        if (object2 != null) {
            string2 = this.getProducerName();
            object = cachedPostprocessorConsumer;
            if (producerListener.requiresExtraMap(string3)) {
                object = ImmutableMap.of("cached_value_found", "true");
            }
            producerListener.onProducerFinishWithSuccess(string3, string2, (Map<String, String>)object);
            map.onProgressUpdate(1.0f);
            map.onNewResult(object2, true);
            ((CloseableReference)object2).close();
            return;
        }
        cachedPostprocessorConsumer = new CachedPostprocessorConsumer((Consumer<CloseableReference<CloseableImage>>)((Object)map), (CacheKey)object3, postprocessor instanceof RepeatedPostprocessor, this.mMemoryCache);
        object2 = this.getProducerName();
        map = string2;
        if (producerListener.requiresExtraMap(string3)) {
            map = ImmutableMap.of("cached_value_found", "false");
        }
        producerListener.onProducerFinishWithSuccess(string3, (String)object2, map);
        this.mInputProducer.produceResults(cachedPostprocessorConsumer, (ProducerContext)object);
    }

    public static class CachedPostprocessorConsumer
    extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private final CacheKey mCacheKey;
        private final boolean mIsRepeatedProcessor;
        private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

        public CachedPostprocessorConsumer(Consumer<CloseableReference<CloseableImage>> consumer, CacheKey cacheKey, boolean bl, MemoryCache<CacheKey, CloseableImage> memoryCache) {
            super(consumer);
            this.mCacheKey = cacheKey;
            this.mIsRepeatedProcessor = bl;
            this.mMemoryCache = memoryCache;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected void onNewResultImpl(CloseableReference<CloseableImage> closeableReference, boolean bl) {
            if (closeableReference == null) {
                if (!bl) return;
                this.getConsumer().onNewResult(null, true);
                return;
            }
            if (!bl) {
                if (!this.mIsRepeatedProcessor) return;
            }
            CloseableReference<CloseableImage> closeableReference2 = this.mMemoryCache.cache(this.mCacheKey, closeableReference);
            try {
                this.getConsumer().onProgressUpdate(1.0f);
                Consumer consumer = this.getConsumer();
                if (closeableReference2 != null) {
                    closeableReference = closeableReference2;
                }
                consumer.onNewResult(closeableReference, bl);
                return;
            }
            finally {
                CloseableReference.closeSafely(closeableReference2);
            }
        }
    }

}


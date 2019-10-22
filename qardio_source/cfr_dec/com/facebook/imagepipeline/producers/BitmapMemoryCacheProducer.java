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
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Map;

public class BitmapMemoryCacheProducer
implements Producer<CloseableReference<CloseableImage>> {
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;
    private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

    public BitmapMemoryCacheProducer(MemoryCache<CacheKey, CloseableImage> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<CloseableReference<CloseableImage>> producer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = producer;
    }

    static /* synthetic */ MemoryCache access$000(BitmapMemoryCacheProducer bitmapMemoryCacheProducer) {
        return bitmapMemoryCacheProducer.mMemoryCache;
    }

    protected String getProducerName() {
        return "BitmapMemoryCacheProducer";
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void produceResults(Consumer<CloseableReference<CloseableImage>> map, ProducerContext map2) {
        Object var5_3 = null;
        ProducerListener producerListener = map2.getListener();
        String string2 = map2.getId();
        producerListener.onProducerStart(string2, this.getProducerName());
        Map<String, String> map3 = map2.getImageRequest();
        Object object = map2.getCallerContext();
        object = this.mCacheKeyFactory.getBitmapCacheKey((ImageRequest)((Object)map3), object);
        CloseableReference<CloseableImage> closeableReference = this.mMemoryCache.get((CacheKey)object);
        if (closeableReference != null) {
            boolean bl = closeableReference.get().getQualityInfo().isOfFullQuality();
            if (bl) {
                String string3 = this.getProducerName();
                map3 = producerListener.requiresExtraMap(string2) ? ImmutableMap.of("cached_value_found", "true") : null;
                producerListener.onProducerFinishWithSuccess(string2, string3, map3);
                map.onProgressUpdate(1.0f);
            }
            map.onNewResult(closeableReference, bl);
            closeableReference.close();
            if (bl) {
                return;
            }
        }
        if (map2.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE.getValue()) {
            map3 = this.getProducerName();
            map2 = producerListener.requiresExtraMap(string2) ? ImmutableMap.of("cached_value_found", "false") : null;
            producerListener.onProducerFinishWithSuccess(string2, (String)((Object)map3), map2);
            map.onNewResult(null, true);
            return;
        }
        map3 = this.wrapConsumer((Consumer<CloseableReference<CloseableImage>>)((Object)map), (CacheKey)object);
        object = this.getProducerName();
        map = var5_3;
        if (producerListener.requiresExtraMap(string2)) {
            map = ImmutableMap.of("cached_value_found", "false");
        }
        producerListener.onProducerFinishWithSuccess(string2, (String)object, map);
        this.mInputProducer.produceResults((Consumer<CloseableReference<CloseableImage>>)((Object)map3), (ProducerContext)((Object)map2));
    }

    protected Consumer<CloseableReference<CloseableImage>> wrapConsumer(Consumer<CloseableReference<CloseableImage>> consumer, final CacheKey cacheKey) {
        return new DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>>(consumer){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onNewResultImpl(CloseableReference<CloseableImage> var1_1, boolean var2_4) {
                block10: {
                    if (var1_1 == null) {
                        if (var2_6 == false) return;
                        this.getConsumer().onNewResult(null, true);
                        return;
                    }
                    if (var1_1.get().isStateful()) {
                        this.getConsumer().onNewResult(var1_1, (boolean)var2_6);
                        return;
                    }
                    if (var2_6 == false && (var3_7 = BitmapMemoryCacheProducer.access$000(BitmapMemoryCacheProducer.this).get(cacheKey)) != null) {
                        var4_8 = var1_1.get().getQualityInfo();
                        var5_9 = ((CloseableImage)var3_7.get()).getQualityInfo();
                        if (!var5_9.isOfFullQuality() && var5_9.getQuality() < var4_8.getQuality()) break block10;
                        this.getConsumer().onNewResult(var3_7, false);
                        return;
                    }
                }
                var3_7 = BitmapMemoryCacheProducer.access$000(BitmapMemoryCacheProducer.this).cache(cacheKey, var1_1);
                if (var2_6 == false) ** GOTO lbl20
                try {
                    this.getConsumer().onProgressUpdate(1.0f);
lbl20:
                    // 2 sources
                    var4_8 = this.getConsumer();
                    if (var3_7 != null) {
                        var1_2 = var3_7;
                    }
                    var4_8.onNewResult(var1_3, (boolean)var2_6);
                    return;
                }
                finally {
                    CloseableReference.closeSafely(var3_7);
                }
            }
        };
    }

}


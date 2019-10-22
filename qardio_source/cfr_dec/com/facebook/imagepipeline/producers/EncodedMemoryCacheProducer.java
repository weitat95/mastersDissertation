/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.Closeable;
import java.util.Map;

public class EncodedMemoryCacheProducer
implements Producer<EncodedImage> {
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<EncodedImage> mInputProducer;
    private final MemoryCache<CacheKey, PooledByteBuffer> mMemoryCache;

    public EncodedMemoryCacheProducer(MemoryCache<CacheKey, PooledByteBuffer> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<EncodedImage> producer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = producer;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void produceResults(Consumer<EncodedImage> var1_1, ProducerContext var2_4) {
        var5_5 = null;
        var4_6 = null;
        var3_7 = null;
        var7_8 = var2_4.getId();
        var8_9 = var2_4.getListener();
        var8_9.onProducerStart(var7_8, "EncodedMemoryCacheProducer");
        var6_10 = var2_4.getImageRequest();
        var9_11 = this.mCacheKeyFactory.getEncodedCacheKey((ImageRequest)var6_10, var2_4.getCallerContext());
        var6_10 = this.mMemoryCache.get(var9_11);
        if (var6_10 == null) ** GOTO lbl33
        var4_6 = new EncodedImage((CloseableReference<PooledByteBuffer>)var6_10);
        var2_4 = var3_7;
        {
            catch (Throwable var1_3) {
                throw var1_3;
            }
        }
        if (var8_9.requiresExtraMap(var7_8)) {
            var2_4 = ImmutableMap.of("cached_value_found", "true");
        }
        var8_9.onProducerFinishWithSuccess(var7_8, "EncodedMemoryCacheProducer", var2_4);
        var1_1.onProgressUpdate(1.0f);
        var1_1.onNewResult(var4_6, true);
        EncodedImage.closeSafely(var4_6);
        return;
        catch (Throwable var1_2) {
            block12: {
                try {
                    EncodedImage.closeSafely(var4_6);
                    throw var1_2;
                }
                finally {
                    CloseableReference.closeSafely(var6_10);
                }
lbl33:
                // 2 sources
                if (var2_4.getLowestPermittedRequestLevel().getValue() < ImageRequest.RequestLevel.ENCODED_MEMORY_CACHE.getValue()) break block12;
                var2_4 = var5_5;
                if (var8_9.requiresExtraMap(var7_8)) {
                    var2_4 = ImmutableMap.of("cached_value_found", "false");
                }
                var8_9.onProducerFinishWithSuccess(var7_8, "EncodedMemoryCacheProducer", var2_4);
                var1_1.onNewResult(null, true);
                CloseableReference.closeSafely(var6_10);
                return;
            }
            var3_7 = new DelegatingConsumer<EncodedImage, EncodedImage>(var1_1){

                @Override
                public void onNewResultImpl(EncodedImage encodedImage, boolean bl) {
                    block11: {
                        if (!bl || encodedImage == null) {
                            this.getConsumer().onNewResult(encodedImage, bl);
                            return;
                        }
                        Closeable closeable = encodedImage.getByteBufferRef();
                        if (closeable != null) {
                            CloseableReference<PooledByteBuffer> closeableReference = EncodedMemoryCacheProducer.this.mMemoryCache.cache(var9_11, closeable);
                            if (closeableReference == null) break block11;
                            try {
                                closeable = new EncodedImage(closeableReference);
                                ((EncodedImage)closeable).copyMetaDataFrom(encodedImage);
                                this.getConsumer().onProgressUpdate(1.0f);
                                this.getConsumer().onNewResult(closeable, true);
                                return;
                            }
                            finally {
                                CloseableReference.closeSafely(closeableReference);
                            }
                            {
                                finally {
                                    EncodedImage.closeSafely((EncodedImage)closeable);
                                }
                            }
                        }
                    }
                    this.getConsumer().onNewResult(encodedImage, true);
                }
            };
            var1_1 = var4_6;
            if (var8_9.requiresExtraMap(var7_8)) {
                var1_1 = ImmutableMap.of("cached_value_found", "false");
            }
            var8_9.onProducerFinishWithSuccess(var7_8, "EncodedMemoryCacheProducer", var1_1);
            this.mInputProducer.produceResults((Consumer<EncodedImage>)var3_7, (ProducerContext)var2_4);
        }
        CloseableReference.closeSafely(var6_10);
    }

}


/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import bolts.Continuation;
import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

public class DiskCacheProducer
implements Producer<EncodedImage> {
    private final CacheKeyFactory mCacheKeyFactory;
    private final boolean mChooseCacheByImageSize;
    private final BufferedDiskCache mDefaultBufferedDiskCache;
    private final int mForceSmallCacheThresholdBytes;
    private final Producer<EncodedImage> mInputProducer;
    private final BufferedDiskCache mSmallImageBufferedDiskCache;

    /*
     * Enabled aggressive block sorting
     */
    public DiskCacheProducer(BufferedDiskCache bufferedDiskCache, BufferedDiskCache bufferedDiskCache2, CacheKeyFactory cacheKeyFactory, Producer<EncodedImage> producer, int n) {
        this.mDefaultBufferedDiskCache = bufferedDiskCache;
        this.mSmallImageBufferedDiskCache = bufferedDiskCache2;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = producer;
        this.mForceSmallCacheThresholdBytes = n;
        boolean bl = n > 0;
        this.mChooseCacheByImageSize = bl;
    }

    static Map<String, String> getExtraMap(ProducerListener producerListener, String string2, boolean bl) {
        if (!producerListener.requiresExtraMap(string2)) {
            return null;
        }
        return ImmutableMap.of("cached_value_found", String.valueOf(bl));
    }

    private static boolean isTaskCancelled(Task<?> task) {
        return task.isCancelled() || task.isFaulted() && task.getError() instanceof CancellationException;
    }

    private void maybeStartInputProducer(Consumer<EncodedImage> consumer, Consumer<EncodedImage> consumer2, ProducerContext producerContext) {
        if (producerContext.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.DISK_CACHE.getValue()) {
            consumer.onNewResult(null, true);
            return;
        }
        this.mInputProducer.produceResults(consumer2, producerContext);
    }

    private Continuation<EncodedImage, Void> onFinishDiskReads(Consumer<EncodedImage> consumer, BufferedDiskCache bufferedDiskCache, CacheKey cacheKey, ProducerContext producerContext) {
        String string2 = producerContext.getId();
        return new Continuation<EncodedImage, Void>(producerContext.getListener(), string2, consumer, bufferedDiskCache, cacheKey, producerContext){
            final /* synthetic */ Consumer val$consumer;
            final /* synthetic */ ProducerListener val$listener;
            final /* synthetic */ BufferedDiskCache val$preferredCache;
            final /* synthetic */ CacheKey val$preferredCacheKey;
            final /* synthetic */ ProducerContext val$producerContext;
            final /* synthetic */ String val$requestId;
            {
                this.val$listener = producerListener;
                this.val$requestId = string2;
                this.val$consumer = consumer;
                this.val$preferredCache = bufferedDiskCache;
                this.val$preferredCacheKey = cacheKey;
                this.val$producerContext = producerContext;
            }

            @Override
            public Void then(Task<EncodedImage> object) throws Exception {
                if (DiskCacheProducer.isTaskCancelled(object)) {
                    this.val$listener.onProducerFinishWithCancellation(this.val$requestId, "DiskCacheProducer", null);
                    this.val$consumer.onCancellation();
                    return null;
                }
                if (((Task)object).isFaulted()) {
                    this.val$listener.onProducerFinishWithFailure(this.val$requestId, "DiskCacheProducer", ((Task)object).getError(), null);
                    DiskCacheProducer.this.maybeStartInputProducer(this.val$consumer, new DiskCacheConsumer(this.val$consumer, this.val$preferredCache, this.val$preferredCacheKey), this.val$producerContext);
                    return null;
                }
                if ((object = ((Task)object).getResult()) != null) {
                    this.val$listener.onProducerFinishWithSuccess(this.val$requestId, "DiskCacheProducer", DiskCacheProducer.getExtraMap(this.val$listener, this.val$requestId, true));
                    this.val$consumer.onProgressUpdate(1.0f);
                    this.val$consumer.onNewResult(object, true);
                    ((EncodedImage)object).close();
                    return null;
                }
                this.val$listener.onProducerFinishWithSuccess(this.val$requestId, "DiskCacheProducer", DiskCacheProducer.getExtraMap(this.val$listener, this.val$requestId, false));
                DiskCacheProducer.this.maybeStartInputProducer(this.val$consumer, new DiskCacheConsumer(this.val$consumer, this.val$preferredCache, this.val$preferredCacheKey), this.val$producerContext);
                return null;
            }
        };
    }

    private void subscribeTaskForRequestCancellation(final AtomicBoolean atomicBoolean, ProducerContext producerContext) {
        producerContext.addCallbacks(new BaseProducerContextCallbacks(){

            @Override
            public void onCancellationRequested() {
                atomicBoolean.set(true);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        Task<EncodedImage> task;
        Object object = producerContext.getImageRequest();
        if (!((ImageRequest)object).isDiskCacheEnabled()) {
            this.maybeStartInputProducer(consumer, consumer, producerContext);
            return;
        }
        producerContext.getListener().onProducerStart(producerContext.getId(), "DiskCacheProducer");
        final CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey((ImageRequest)object, producerContext.getCallerContext());
        boolean bl = ((ImageRequest)object).getCacheChoice() == ImageRequest.CacheChoice.SMALL;
        object = bl ? this.mSmallImageBufferedDiskCache : this.mDefaultBufferedDiskCache;
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        if (this.mChooseCacheByImageSize) {
            BufferedDiskCache bufferedDiskCache;
            boolean bl2 = this.mSmallImageBufferedDiskCache.containsSync(cacheKey);
            boolean bl3 = this.mDefaultBufferedDiskCache.containsSync(cacheKey);
            if (bl2 || !bl3) {
                task = this.mSmallImageBufferedDiskCache;
                bufferedDiskCache = this.mDefaultBufferedDiskCache;
            } else {
                task = this.mDefaultBufferedDiskCache;
                bufferedDiskCache = this.mSmallImageBufferedDiskCache;
            }
            task = ((BufferedDiskCache)((Object)task)).get(cacheKey, atomicBoolean).continueWithTask(new Continuation<EncodedImage, Task<EncodedImage>>(){

                @Override
                public Task<EncodedImage> then(Task<EncodedImage> task) throws Exception {
                    if (DiskCacheProducer.isTaskCancelled(task) || !task.isFaulted() && task.getResult() != null) {
                        return task;
                    }
                    return bufferedDiskCache.get(cacheKey, atomicBoolean);
                }
            });
        } else {
            task = ((BufferedDiskCache)object).get(cacheKey, atomicBoolean);
        }
        task.continueWith(this.onFinishDiskReads(consumer, (BufferedDiskCache)object, cacheKey, producerContext));
        this.subscribeTaskForRequestCancellation(atomicBoolean, producerContext);
    }

    private class DiskCacheConsumer
    extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final BufferedDiskCache mCache;
        private final CacheKey mCacheKey;

        private DiskCacheConsumer(Consumer<EncodedImage> consumer, BufferedDiskCache bufferedDiskCache, CacheKey cacheKey) {
            super(consumer);
            this.mCache = bufferedDiskCache;
            this.mCacheKey = cacheKey;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onNewResultImpl(EncodedImage encodedImage, boolean bl) {
            if (encodedImage != null && bl) {
                if (DiskCacheProducer.this.mChooseCacheByImageSize) {
                    int n = encodedImage.getSize();
                    if (n > 0 && n < DiskCacheProducer.this.mForceSmallCacheThresholdBytes) {
                        DiskCacheProducer.this.mSmallImageBufferedDiskCache.put(this.mCacheKey, encodedImage);
                    } else {
                        DiskCacheProducer.this.mDefaultBufferedDiskCache.put(this.mCacheKey, encodedImage);
                    }
                } else {
                    this.mCache.put(this.mCacheKey, encodedImage);
                }
            }
            this.getConsumer().onNewResult(encodedImage, bl);
        }
    }

}


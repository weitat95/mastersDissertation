/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.core;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSources;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ProducerSequenceFactory;
import com.facebook.imagepipeline.datasource.CloseableProducerToDataSourceAdapter;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.listener.ForwardingRequestListener;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.producers.SettableProducerContext;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class ImagePipeline {
    private static final CancellationException PREFETCH_EXCEPTION = new CancellationException("Prefetching is not enabled");
    private final MemoryCache<CacheKey, CloseableImage> mBitmapMemoryCache;
    private final CacheKeyFactory mCacheKeyFactory;
    private final MemoryCache<CacheKey, PooledByteBuffer> mEncodedMemoryCache;
    private AtomicLong mIdCounter = new AtomicLong();
    private final Supplier<Boolean> mIsPrefetchEnabledSupplier;
    private final BufferedDiskCache mMainBufferedDiskCache;
    private final ProducerSequenceFactory mProducerSequenceFactory;
    private final RequestListener mRequestListener;
    private final BufferedDiskCache mSmallImageBufferedDiskCache;
    private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;

    public ImagePipeline(ProducerSequenceFactory producerSequenceFactory, Set<RequestListener> set, Supplier<Boolean> supplier, MemoryCache<CacheKey, CloseableImage> memoryCache, MemoryCache<CacheKey, PooledByteBuffer> memoryCache2, BufferedDiskCache bufferedDiskCache, BufferedDiskCache bufferedDiskCache2, CacheKeyFactory cacheKeyFactory, ThreadHandoffProducerQueue threadHandoffProducerQueue) {
        this.mProducerSequenceFactory = producerSequenceFactory;
        this.mRequestListener = new ForwardingRequestListener(set);
        this.mIsPrefetchEnabledSupplier = supplier;
        this.mBitmapMemoryCache = memoryCache;
        this.mEncodedMemoryCache = memoryCache2;
        this.mMainBufferedDiskCache = bufferedDiskCache;
        this.mSmallImageBufferedDiskCache = bufferedDiskCache2;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mThreadHandoffProducerQueue = threadHandoffProducerQueue;
    }

    private String generateUniqueFutureId() {
        return String.valueOf(this.mIdCounter.getAndIncrement());
    }

    private RequestListener getRequestListenerForRequest(ImageRequest imageRequest) {
        if (imageRequest.getRequestListener() == null) {
            return this.mRequestListener;
        }
        return new ForwardingRequestListener(this.mRequestListener, imageRequest.getRequestListener());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private <T> DataSource<CloseableReference<T>> submitFetchRequest(Producer<CloseableReference<T>> dataSource, ImageRequest imageRequest, ImageRequest.RequestLevel requestLevel, Object object) {
        RequestListener requestListener;
        boolean bl;
        String string2;
        block3: {
            bl = false;
            requestListener = this.getRequestListenerForRequest(imageRequest);
            try {
                requestLevel = ImageRequest.RequestLevel.getMax(imageRequest.getLowestPermittedRequestLevel(), requestLevel);
                string2 = this.generateUniqueFutureId();
                if (imageRequest.getProgressiveRenderingEnabled()) break block3;
                if (UriUtil.isNetworkUri(imageRequest.getSourceUri())) return CloseableProducerToDataSourceAdapter.create(dataSource, new SettableProducerContext(imageRequest, string2, requestListener, object, requestLevel, false, bl, imageRequest.getPriority()), requestListener);
            }
            catch (Exception exception) {
                return DataSources.immediateFailedDataSource(exception);
            }
        }
        bl = true;
        return CloseableProducerToDataSourceAdapter.create(dataSource, new SettableProducerContext(imageRequest, string2, requestListener, object, requestLevel, false, bl, imageRequest.getPriority()), requestListener);
    }

    public DataSource<CloseableReference<CloseableImage>> fetchDecodedImage(ImageRequest object, Object object2, ImageRequest.RequestLevel requestLevel) {
        try {
            object = this.submitFetchRequest(this.mProducerSequenceFactory.getDecodedImageProducerSequence((ImageRequest)object), (ImageRequest)object, requestLevel, object2);
            return object;
        }
        catch (Exception exception) {
            return DataSources.immediateFailedDataSource(exception);
        }
    }

    public MemoryCache<CacheKey, CloseableImage> getBitmapMemoryCache() {
        return this.mBitmapMemoryCache;
    }

    public CacheKeyFactory getCacheKeyFactory() {
        return this.mCacheKeyFactory;
    }
}


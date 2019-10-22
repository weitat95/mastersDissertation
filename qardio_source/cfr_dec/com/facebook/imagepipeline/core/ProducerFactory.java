/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 */
package com.facebook.imagepipeline.core;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.core.ExecutorSupplier;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.ByteArrayPool;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.producers.AddImageTransformMetaDataProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheGetProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheKeyMultiplexProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.BranchOnSeparateImagesProducer;
import com.facebook.imagepipeline.producers.DataFetchProducer;
import com.facebook.imagepipeline.producers.DecodeProducer;
import com.facebook.imagepipeline.producers.DiskCacheProducer;
import com.facebook.imagepipeline.producers.EncodedCacheKeyMultiplexProducer;
import com.facebook.imagepipeline.producers.EncodedMemoryCacheProducer;
import com.facebook.imagepipeline.producers.LocalAssetFetchProducer;
import com.facebook.imagepipeline.producers.LocalContentUriFetchProducer;
import com.facebook.imagepipeline.producers.LocalContentUriThumbnailFetchProducer;
import com.facebook.imagepipeline.producers.LocalExifThumbnailProducer;
import com.facebook.imagepipeline.producers.LocalFileFetchProducer;
import com.facebook.imagepipeline.producers.LocalResourceFetchProducer;
import com.facebook.imagepipeline.producers.LocalVideoThumbnailProducer;
import com.facebook.imagepipeline.producers.NetworkFetchProducer;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.PostprocessedBitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.PostprocessorProducer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ResizeAndRotateProducer;
import com.facebook.imagepipeline.producers.ThreadHandoffProducer;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import com.facebook.imagepipeline.producers.ThrottlingProducer;
import com.facebook.imagepipeline.producers.ThumbnailBranchProducer;
import com.facebook.imagepipeline.producers.ThumbnailProducer;
import com.facebook.imagepipeline.producers.WebpTranscodeProducer;
import java.util.concurrent.Executor;

public class ProducerFactory {
    private AssetManager mAssetManager;
    private final MemoryCache<CacheKey, CloseableImage> mBitmapMemoryCache;
    private final ByteArrayPool mByteArrayPool;
    private final CacheKeyFactory mCacheKeyFactory;
    private ContentResolver mContentResolver;
    private final boolean mDecodeFileDescriptorEnabled;
    private final BufferedDiskCache mDefaultBufferedDiskCache;
    private final boolean mDownsampleEnabled;
    private final MemoryCache<CacheKey, PooledByteBuffer> mEncodedMemoryCache;
    private final int mEnhancedWebpTranscodingType;
    private final ExecutorSupplier mExecutorSupplier;
    private final int mForceSmallCacheThresholdBytes;
    private final ImageDecoder mImageDecoder;
    private final PlatformBitmapFactory mPlatformBitmapFactory;
    private final PooledByteBufferFactory mPooledByteBufferFactory;
    private final ProgressiveJpegConfig mProgressiveJpegConfig;
    private final boolean mResizeAndRotateEnabledForNetwork;
    private Resources mResources;
    private final BufferedDiskCache mSmallImageBufferedDiskCache;

    public ProducerFactory(Context context, ByteArrayPool byteArrayPool, ImageDecoder imageDecoder, ProgressiveJpegConfig progressiveJpegConfig, boolean bl, int n, boolean bl2, ExecutorSupplier executorSupplier, PooledByteBufferFactory pooledByteBufferFactory, MemoryCache<CacheKey, CloseableImage> memoryCache, MemoryCache<CacheKey, PooledByteBuffer> memoryCache2, BufferedDiskCache bufferedDiskCache, BufferedDiskCache bufferedDiskCache2, CacheKeyFactory cacheKeyFactory, PlatformBitmapFactory platformBitmapFactory, boolean bl3, int n2) {
        this.mForceSmallCacheThresholdBytes = n2;
        this.mContentResolver = context.getApplicationContext().getContentResolver();
        this.mResources = context.getApplicationContext().getResources();
        this.mAssetManager = context.getApplicationContext().getAssets();
        this.mByteArrayPool = byteArrayPool;
        this.mImageDecoder = imageDecoder;
        this.mProgressiveJpegConfig = progressiveJpegConfig;
        this.mDownsampleEnabled = bl;
        this.mEnhancedWebpTranscodingType = n;
        this.mResizeAndRotateEnabledForNetwork = bl2;
        this.mExecutorSupplier = executorSupplier;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mBitmapMemoryCache = memoryCache;
        this.mEncodedMemoryCache = memoryCache2;
        this.mDefaultBufferedDiskCache = bufferedDiskCache;
        this.mSmallImageBufferedDiskCache = bufferedDiskCache2;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mPlatformBitmapFactory = platformBitmapFactory;
        this.mDecodeFileDescriptorEnabled = bl3;
    }

    public static AddImageTransformMetaDataProducer newAddImageTransformMetaDataProducer(Producer<EncodedImage> producer) {
        return new AddImageTransformMetaDataProducer(producer);
    }

    public static BranchOnSeparateImagesProducer newBranchOnSeparateImagesProducer(Producer<EncodedImage> producer, Producer<EncodedImage> producer2) {
        return new BranchOnSeparateImagesProducer(producer, producer2);
    }

    public <T> ThreadHandoffProducer<T> newBackgroundThreadHandoffProducer(Producer<T> producer, ThreadHandoffProducerQueue threadHandoffProducerQueue) {
        return new ThreadHandoffProducer<T>(producer, threadHandoffProducerQueue);
    }

    public BitmapMemoryCacheGetProducer newBitmapMemoryCacheGetProducer(Producer<CloseableReference<CloseableImage>> producer) {
        return new BitmapMemoryCacheGetProducer(this.mBitmapMemoryCache, this.mCacheKeyFactory, producer);
    }

    public BitmapMemoryCacheKeyMultiplexProducer newBitmapMemoryCacheKeyMultiplexProducer(Producer<CloseableReference<CloseableImage>> producer) {
        return new BitmapMemoryCacheKeyMultiplexProducer(this.mCacheKeyFactory, producer);
    }

    public BitmapMemoryCacheProducer newBitmapMemoryCacheProducer(Producer<CloseableReference<CloseableImage>> producer) {
        return new BitmapMemoryCacheProducer(this.mBitmapMemoryCache, this.mCacheKeyFactory, producer);
    }

    public DataFetchProducer newDataFetchProducer() {
        return new DataFetchProducer(this.mPooledByteBufferFactory, this.mDecodeFileDescriptorEnabled);
    }

    public DecodeProducer newDecodeProducer(Producer<EncodedImage> producer) {
        return new DecodeProducer(this.mByteArrayPool, this.mExecutorSupplier.forDecode(), this.mImageDecoder, this.mProgressiveJpegConfig, this.mDownsampleEnabled, this.mResizeAndRotateEnabledForNetwork, producer);
    }

    public DiskCacheProducer newDiskCacheProducer(Producer<EncodedImage> producer) {
        return new DiskCacheProducer(this.mDefaultBufferedDiskCache, this.mSmallImageBufferedDiskCache, this.mCacheKeyFactory, producer, this.mForceSmallCacheThresholdBytes);
    }

    public EncodedCacheKeyMultiplexProducer newEncodedCacheKeyMultiplexProducer(Producer<EncodedImage> producer) {
        return new EncodedCacheKeyMultiplexProducer(this.mCacheKeyFactory, producer);
    }

    public EncodedMemoryCacheProducer newEncodedMemoryCacheProducer(Producer<EncodedImage> producer) {
        return new EncodedMemoryCacheProducer(this.mEncodedMemoryCache, this.mCacheKeyFactory, producer);
    }

    public LocalAssetFetchProducer newLocalAssetFetchProducer() {
        return new LocalAssetFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mAssetManager, this.mDecodeFileDescriptorEnabled);
    }

    public LocalContentUriFetchProducer newLocalContentUriFetchProducer() {
        return new LocalContentUriFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mContentResolver, this.mDecodeFileDescriptorEnabled);
    }

    public LocalContentUriThumbnailFetchProducer newLocalContentUriThumbnailFetchProducer() {
        return new LocalContentUriThumbnailFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mContentResolver, this.mDecodeFileDescriptorEnabled);
    }

    public LocalExifThumbnailProducer newLocalExifThumbnailProducer() {
        return new LocalExifThumbnailProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mContentResolver);
    }

    public LocalFileFetchProducer newLocalFileFetchProducer() {
        return new LocalFileFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mDecodeFileDescriptorEnabled);
    }

    public LocalResourceFetchProducer newLocalResourceFetchProducer() {
        return new LocalResourceFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mResources, this.mDecodeFileDescriptorEnabled);
    }

    public LocalVideoThumbnailProducer newLocalVideoThumbnailProducer() {
        return new LocalVideoThumbnailProducer(this.mExecutorSupplier.forLocalStorageRead());
    }

    public NetworkFetchProducer newNetworkFetchProducer(NetworkFetcher networkFetcher) {
        return new NetworkFetchProducer(this.mPooledByteBufferFactory, this.mByteArrayPool, networkFetcher);
    }

    public PostprocessedBitmapMemoryCacheProducer newPostprocessorBitmapMemoryCacheProducer(Producer<CloseableReference<CloseableImage>> producer) {
        return new PostprocessedBitmapMemoryCacheProducer(this.mBitmapMemoryCache, this.mCacheKeyFactory, producer);
    }

    public PostprocessorProducer newPostprocessorProducer(Producer<CloseableReference<CloseableImage>> producer) {
        return new PostprocessorProducer(producer, this.mPlatformBitmapFactory, this.mExecutorSupplier.forBackgroundTasks());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ResizeAndRotateProducer newResizeAndRotateProducer(Producer<EncodedImage> producer, boolean bl) {
        Executor executor = this.mExecutorSupplier.forBackgroundTasks();
        PooledByteBufferFactory pooledByteBufferFactory = this.mPooledByteBufferFactory;
        if (bl && !this.mDownsampleEnabled) {
            bl = true;
            do {
                return new ResizeAndRotateProducer(executor, pooledByteBufferFactory, bl, producer);
                break;
            } while (true);
        }
        bl = false;
        return new ResizeAndRotateProducer(executor, pooledByteBufferFactory, bl, producer);
    }

    public <T> ThrottlingProducer<T> newThrottlingProducer(int n, Producer<T> producer) {
        return new ThrottlingProducer<T>(n, this.mExecutorSupplier.forLightweightBackgroundTasks(), producer);
    }

    public ThumbnailBranchProducer newThumbnailBranchProducer(ThumbnailProducer<EncodedImage>[] arrthumbnailProducer) {
        return new ThumbnailBranchProducer(arrthumbnailProducer);
    }

    public WebpTranscodeProducer newWebpTranscodeProducer(Producer<EncodedImage> producer) {
        return new WebpTranscodeProducer(this.mExecutorSupplier.forBackgroundTasks(), this.mPooledByteBufferFactory, producer, this.mEnhancedWebpTranscodingType);
    }
}


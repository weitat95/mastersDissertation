/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.os.Build
 *  android.os.Build$VERSION
 *  javax.annotation.concurrent.NotThreadSafe
 */
package com.facebook.imagepipeline.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.Pools;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.cache.disk.FileCache;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.webp.WebpBitmapFactory;
import com.facebook.imagepipeline.animated.factory.AnimatedFactory;
import com.facebook.imagepipeline.animated.factory.AnimatedFactoryProvider;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import com.facebook.imagepipeline.bitmaps.ArtBitmapFactory;
import com.facebook.imagepipeline.bitmaps.EmptyJpegGenerator;
import com.facebook.imagepipeline.bitmaps.GingerbreadBitmapFactory;
import com.facebook.imagepipeline.bitmaps.HoneycombBitmapFactory;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.BitmapCountingMemoryCacheFactory;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheFactory;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.EncodedCountingMemoryCacheFactory;
import com.facebook.imagepipeline.cache.EncodedMemoryCacheFactory;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ExecutorSupplier;
import com.facebook.imagepipeline.core.FileCacheFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineExperiments;
import com.facebook.imagepipeline.core.ProducerFactory;
import com.facebook.imagepipeline.core.ProducerSequenceFactory;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.memory.BitmapPool;
import com.facebook.imagepipeline.memory.ByteArrayPool;
import com.facebook.imagepipeline.memory.FlexByteArrayPool;
import com.facebook.imagepipeline.memory.PoolFactory;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteStreams;
import com.facebook.imagepipeline.platform.ArtDecoder;
import com.facebook.imagepipeline.platform.GingerbreadPurgeableDecoder;
import com.facebook.imagepipeline.platform.KitKatPurgeableDecoder;
import com.facebook.imagepipeline.platform.PlatformDecoder;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class ImagePipelineFactory {
    private static ImagePipelineFactory sInstance = null;
    private AnimatedFactory mAnimatedFactory;
    private CountingMemoryCache<CacheKey, CloseableImage> mBitmapCountingMemoryCache;
    private MemoryCache<CacheKey, CloseableImage> mBitmapMemoryCache;
    private final ImagePipelineConfig mConfig;
    private CountingMemoryCache<CacheKey, PooledByteBuffer> mEncodedCountingMemoryCache;
    private MemoryCache<CacheKey, PooledByteBuffer> mEncodedMemoryCache;
    private ImageDecoder mImageDecoder;
    private ImagePipeline mImagePipeline;
    private BufferedDiskCache mMainBufferedDiskCache;
    private FileCache mMainFileCache;
    private PlatformBitmapFactory mPlatformBitmapFactory;
    private PlatformDecoder mPlatformDecoder;
    private ProducerFactory mProducerFactory;
    private ProducerSequenceFactory mProducerSequenceFactory;
    private BufferedDiskCache mSmallImageBufferedDiskCache;
    private FileCache mSmallImageFileCache;
    private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;

    public ImagePipelineFactory(ImagePipelineConfig imagePipelineConfig) {
        this.mConfig = Preconditions.checkNotNull(imagePipelineConfig);
        this.mThreadHandoffProducerQueue = new ThreadHandoffProducerQueue(imagePipelineConfig.getExecutorSupplier().forLightweightBackgroundTasks());
    }

    public static PlatformBitmapFactory buildPlatformBitmapFactory(PoolFactory poolFactory, PlatformDecoder platformDecoder) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new ArtBitmapFactory(poolFactory.getBitmapPool());
        }
        if (Build.VERSION.SDK_INT >= 11) {
            return new HoneycombBitmapFactory(new EmptyJpegGenerator(poolFactory.getPooledByteBufferFactory()), platformDecoder);
        }
        return new GingerbreadBitmapFactory();
    }

    public static PlatformDecoder buildPlatformDecoder(PoolFactory poolFactory, boolean bl, boolean bl2, WebpBitmapFactory.WebpErrorLogger webpErrorLogger) {
        if (Build.VERSION.SDK_INT >= 21) {
            int n = poolFactory.getFlexByteArrayPoolMaxNumThreads();
            return new ArtDecoder(poolFactory.getBitmapPool(), n, new Pools.SynchronizedPool(n));
        }
        if (bl && Build.VERSION.SDK_INT < 19) {
            return new GingerbreadPurgeableDecoder(bl2, webpErrorLogger);
        }
        return new KitKatPurgeableDecoder(poolFactory.getFlexByteArrayPool());
    }

    /*
     * Enabled aggressive block sorting
     */
    private ImageDecoder getImageDecoder() {
        if (this.mImageDecoder != null) return this.mImageDecoder;
        if (this.mConfig.getImageDecoder() != null) {
            this.mImageDecoder = this.mConfig.getImageDecoder();
            return this.mImageDecoder;
        }
        AnimatedImageFactory animatedImageFactory = this.getAnimatedFactory() != null ? this.getAnimatedFactory().getAnimatedImageFactory() : null;
        this.mImageDecoder = new ImageDecoder(animatedImageFactory, this.getPlatformDecoder(), this.mConfig.getBitmapConfig());
        return this.mImageDecoder;
    }

    public static ImagePipelineFactory getInstance() {
        return Preconditions.checkNotNull(sInstance, "ImagePipelineFactory was not initialized!");
    }

    private BufferedDiskCache getMainBufferedDiskCache() {
        if (this.mMainBufferedDiskCache == null) {
            this.mMainBufferedDiskCache = new BufferedDiskCache(this.getMainFileCache(), this.mConfig.getPoolFactory().getPooledByteBufferFactory(), this.mConfig.getPoolFactory().getPooledByteStreams(), this.mConfig.getExecutorSupplier().forLocalStorageRead(), this.mConfig.getExecutorSupplier().forLocalStorageWrite(), this.mConfig.getImageCacheStatsTracker());
        }
        return this.mMainBufferedDiskCache;
    }

    private ProducerFactory getProducerFactory() {
        if (this.mProducerFactory == null) {
            this.mProducerFactory = new ProducerFactory(this.mConfig.getContext(), this.mConfig.getPoolFactory().getSmallByteArrayPool(), this.getImageDecoder(), this.mConfig.getProgressiveJpegConfig(), this.mConfig.isDownsampleEnabled(), this.mConfig.getExperiments().getEnhancedWebpTranscodingType(), this.mConfig.isResizeAndRotateEnabledForNetwork(), this.mConfig.getExecutorSupplier(), this.mConfig.getPoolFactory().getPooledByteBufferFactory(), this.getBitmapMemoryCache(), this.getEncodedMemoryCache(), this.getMainBufferedDiskCache(), this.getSmallImageBufferedDiskCache(), this.mConfig.getCacheKeyFactory(), this.getPlatformBitmapFactory(), this.mConfig.getExperiments().isDecodeFileDescriptorEnabled(), this.mConfig.getExperiments().getForceSmallCacheThresholdBytes());
        }
        return this.mProducerFactory;
    }

    private ProducerSequenceFactory getProducerSequenceFactory() {
        if (this.mProducerSequenceFactory == null) {
            this.mProducerSequenceFactory = new ProducerSequenceFactory(this.getProducerFactory(), this.mConfig.getNetworkFetcher(), this.mConfig.isResizeAndRotateEnabledForNetwork(), this.mConfig.getExperiments().isWebpSupportEnabled(), this.mThreadHandoffProducerQueue, this.mConfig.getExperiments().getThrottlingMaxSimultaneousRequests());
        }
        return this.mProducerSequenceFactory;
    }

    private BufferedDiskCache getSmallImageBufferedDiskCache() {
        if (this.mSmallImageBufferedDiskCache == null) {
            this.mSmallImageBufferedDiskCache = new BufferedDiskCache(this.getSmallImageFileCache(), this.mConfig.getPoolFactory().getPooledByteBufferFactory(), this.mConfig.getPoolFactory().getPooledByteStreams(), this.mConfig.getExecutorSupplier().forLocalStorageRead(), this.mConfig.getExecutorSupplier().forLocalStorageWrite(), this.mConfig.getImageCacheStatsTracker());
        }
        return this.mSmallImageBufferedDiskCache;
    }

    public static void initialize(Context context) {
        ImagePipelineFactory.initialize(ImagePipelineConfig.newBuilder(context).build());
    }

    public static void initialize(ImagePipelineConfig imagePipelineConfig) {
        sInstance = new ImagePipelineFactory(imagePipelineConfig);
    }

    public AnimatedFactory getAnimatedFactory() {
        if (this.mAnimatedFactory == null) {
            this.mAnimatedFactory = AnimatedFactoryProvider.getAnimatedFactory(this.getPlatformBitmapFactory(), this.mConfig.getExecutorSupplier());
        }
        return this.mAnimatedFactory;
    }

    public CountingMemoryCache<CacheKey, CloseableImage> getBitmapCountingMemoryCache() {
        if (this.mBitmapCountingMemoryCache == null) {
            this.mBitmapCountingMemoryCache = BitmapCountingMemoryCacheFactory.get(this.mConfig.getBitmapMemoryCacheParamsSupplier(), this.mConfig.getMemoryTrimmableRegistry(), this.getPlatformBitmapFactory(), this.mConfig.getExperiments().isExternalCreatedBitmapLogEnabled());
        }
        return this.mBitmapCountingMemoryCache;
    }

    public MemoryCache<CacheKey, CloseableImage> getBitmapMemoryCache() {
        if (this.mBitmapMemoryCache == null) {
            this.mBitmapMemoryCache = BitmapMemoryCacheFactory.get(this.getBitmapCountingMemoryCache(), this.mConfig.getImageCacheStatsTracker());
        }
        return this.mBitmapMemoryCache;
    }

    public CountingMemoryCache<CacheKey, PooledByteBuffer> getEncodedCountingMemoryCache() {
        if (this.mEncodedCountingMemoryCache == null) {
            this.mEncodedCountingMemoryCache = EncodedCountingMemoryCacheFactory.get(this.mConfig.getEncodedMemoryCacheParamsSupplier(), this.mConfig.getMemoryTrimmableRegistry(), this.getPlatformBitmapFactory());
        }
        return this.mEncodedCountingMemoryCache;
    }

    public MemoryCache<CacheKey, PooledByteBuffer> getEncodedMemoryCache() {
        if (this.mEncodedMemoryCache == null) {
            this.mEncodedMemoryCache = EncodedMemoryCacheFactory.get(this.getEncodedCountingMemoryCache(), this.mConfig.getImageCacheStatsTracker());
        }
        return this.mEncodedMemoryCache;
    }

    public ImagePipeline getImagePipeline() {
        if (this.mImagePipeline == null) {
            this.mImagePipeline = new ImagePipeline(this.getProducerSequenceFactory(), this.mConfig.getRequestListeners(), this.mConfig.getIsPrefetchEnabledSupplier(), this.getBitmapMemoryCache(), this.getEncodedMemoryCache(), this.getMainBufferedDiskCache(), this.getSmallImageBufferedDiskCache(), this.mConfig.getCacheKeyFactory(), this.mThreadHandoffProducerQueue);
        }
        return this.mImagePipeline;
    }

    public FileCache getMainFileCache() {
        if (this.mMainFileCache == null) {
            DiskCacheConfig diskCacheConfig = this.mConfig.getMainDiskCacheConfig();
            this.mMainFileCache = this.mConfig.getFileCacheFactory().get(diskCacheConfig);
        }
        return this.mMainFileCache;
    }

    public PlatformBitmapFactory getPlatformBitmapFactory() {
        if (this.mPlatformBitmapFactory == null) {
            this.mPlatformBitmapFactory = ImagePipelineFactory.buildPlatformBitmapFactory(this.mConfig.getPoolFactory(), this.getPlatformDecoder());
        }
        return this.mPlatformBitmapFactory;
    }

    public PlatformDecoder getPlatformDecoder() {
        if (this.mPlatformDecoder == null) {
            this.mPlatformDecoder = ImagePipelineFactory.buildPlatformDecoder(this.mConfig.getPoolFactory(), this.mConfig.isDecodeMemoryFileEnabled(), this.mConfig.getExperiments().isWebpSupportEnabled(), this.mConfig.getExperiments().getWebpErrorLogger());
        }
        return this.mPlatformDecoder;
    }

    public FileCache getSmallImageFileCache() {
        if (this.mSmallImageFileCache == null) {
            DiskCacheConfig diskCacheConfig = this.mConfig.getSmallImageDiskCacheConfig();
            this.mSmallImageFileCache = this.mConfig.getFileCacheFactory().get(diskCacheConfig);
        }
        return this.mSmallImageFileCache;
    }
}


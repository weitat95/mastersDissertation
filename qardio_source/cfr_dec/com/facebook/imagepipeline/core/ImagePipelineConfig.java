/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.core;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.DefaultBitmapMemoryCacheParamsSupplier;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.cache.DefaultEncodedMemoryCacheParamsSupplier;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.cache.NoOpImageCacheStatsTracker;
import com.facebook.imagepipeline.core.DefaultExecutorSupplier;
import com.facebook.imagepipeline.core.DiskStorageCacheFactory;
import com.facebook.imagepipeline.core.DiskStorageFactory;
import com.facebook.imagepipeline.core.DynamicDefaultDiskStorageFactory;
import com.facebook.imagepipeline.core.ExecutorSupplier;
import com.facebook.imagepipeline.core.FileCacheFactory;
import com.facebook.imagepipeline.core.ImagePipelineExperiments;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.memory.PoolConfig;
import com.facebook.imagepipeline.memory.PoolFactory;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;

public class ImagePipelineConfig {
    private static DefaultImageRequestConfig sDefaultImageRequestConfig = new DefaultImageRequestConfig();
    @Nullable
    private final AnimatedImageFactory mAnimatedImageFactory;
    private final Bitmap.Config mBitmapConfig;
    private final Supplier<MemoryCacheParams> mBitmapMemoryCacheParamsSupplier;
    private final CacheKeyFactory mCacheKeyFactory;
    private final Context mContext;
    private final boolean mDecodeMemoryFileEnabled;
    private final boolean mDownsampleEnabled;
    private final Supplier<MemoryCacheParams> mEncodedMemoryCacheParamsSupplier;
    private final ExecutorSupplier mExecutorSupplier;
    private final FileCacheFactory mFileCacheFactory;
    private final ImageCacheStatsTracker mImageCacheStatsTracker;
    @Nullable
    private final ImageDecoder mImageDecoder;
    private final ImagePipelineExperiments mImagePipelineExperiments;
    private final Supplier<Boolean> mIsPrefetchEnabledSupplier;
    private final DiskCacheConfig mMainDiskCacheConfig;
    private final MemoryTrimmableRegistry mMemoryTrimmableRegistry;
    private final NetworkFetcher mNetworkFetcher;
    @Nullable
    private final PlatformBitmapFactory mPlatformBitmapFactory;
    private final PoolFactory mPoolFactory;
    private final ProgressiveJpegConfig mProgressiveJpegConfig;
    private final Set<RequestListener> mRequestListeners;
    private final boolean mResizeAndRotateEnabledForNetwork;
    private final DiskCacheConfig mSmallImageDiskCacheConfig;

    /*
     * Enabled aggressive block sorting
     */
    private ImagePipelineConfig(Builder builder) {
        this.mAnimatedImageFactory = builder.mAnimatedImageFactory;
        Object object = builder.mBitmapMemoryCacheParamsSupplier == null ? new DefaultBitmapMemoryCacheParamsSupplier((ActivityManager)builder.mContext.getSystemService("activity")) : builder.mBitmapMemoryCacheParamsSupplier;
        this.mBitmapMemoryCacheParamsSupplier = object;
        object = builder.mBitmapConfig == null ? Bitmap.Config.ARGB_8888 : builder.mBitmapConfig;
        this.mBitmapConfig = object;
        object = builder.mCacheKeyFactory == null ? DefaultCacheKeyFactory.getInstance() : builder.mCacheKeyFactory;
        this.mCacheKeyFactory = object;
        this.mContext = Preconditions.checkNotNull(builder.mContext);
        this.mDecodeMemoryFileEnabled = builder.mDecodeMemoryFileEnabled;
        object = builder.mFileCacheFactory == null ? new DiskStorageCacheFactory(new DynamicDefaultDiskStorageFactory()) : builder.mFileCacheFactory;
        this.mFileCacheFactory = object;
        this.mDownsampleEnabled = builder.mDownsampleEnabled;
        object = builder.mEncodedMemoryCacheParamsSupplier == null ? new DefaultEncodedMemoryCacheParamsSupplier() : builder.mEncodedMemoryCacheParamsSupplier;
        this.mEncodedMemoryCacheParamsSupplier = object;
        object = builder.mImageCacheStatsTracker == null ? NoOpImageCacheStatsTracker.getInstance() : builder.mImageCacheStatsTracker;
        this.mImageCacheStatsTracker = object;
        this.mImageDecoder = builder.mImageDecoder;
        object = builder.mIsPrefetchEnabledSupplier == null ? new Supplier<Boolean>(){

            @Override
            public Boolean get() {
                return true;
            }
        } : builder.mIsPrefetchEnabledSupplier;
        this.mIsPrefetchEnabledSupplier = object;
        object = builder.mMainDiskCacheConfig == null ? ImagePipelineConfig.getDefaultMainDiskCacheConfig(builder.mContext) : builder.mMainDiskCacheConfig;
        this.mMainDiskCacheConfig = object;
        object = builder.mMemoryTrimmableRegistry == null ? NoOpMemoryTrimmableRegistry.getInstance() : builder.mMemoryTrimmableRegistry;
        this.mMemoryTrimmableRegistry = object;
        object = builder.mNetworkFetcher == null ? new HttpUrlConnectionNetworkFetcher() : builder.mNetworkFetcher;
        this.mNetworkFetcher = object;
        this.mPlatformBitmapFactory = builder.mPlatformBitmapFactory;
        object = builder.mPoolFactory == null ? new PoolFactory(PoolConfig.newBuilder().build()) : builder.mPoolFactory;
        this.mPoolFactory = object;
        object = builder.mProgressiveJpegConfig == null ? new SimpleProgressiveJpegConfig() : builder.mProgressiveJpegConfig;
        this.mProgressiveJpegConfig = object;
        object = builder.mRequestListeners == null ? new HashSet<RequestListener>() : builder.mRequestListeners;
        this.mRequestListeners = object;
        this.mResizeAndRotateEnabledForNetwork = builder.mResizeAndRotateEnabledForNetwork;
        object = builder.mSmallImageDiskCacheConfig == null ? this.mMainDiskCacheConfig : builder.mSmallImageDiskCacheConfig;
        this.mSmallImageDiskCacheConfig = object;
        int n = this.mPoolFactory.getFlexByteArrayPoolMaxNumThreads();
        object = builder.mExecutorSupplier == null ? new DefaultExecutorSupplier(n) : builder.mExecutorSupplier;
        this.mExecutorSupplier = object;
        this.mImagePipelineExperiments = builder.mExperimentsBuilder.build();
    }

    public static DefaultImageRequestConfig getDefaultImageRequestConfig() {
        return sDefaultImageRequestConfig;
    }

    private static DiskCacheConfig getDefaultMainDiskCacheConfig(Context context) {
        return DiskCacheConfig.newBuilder(context).build();
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    public Bitmap.Config getBitmapConfig() {
        return this.mBitmapConfig;
    }

    public Supplier<MemoryCacheParams> getBitmapMemoryCacheParamsSupplier() {
        return this.mBitmapMemoryCacheParamsSupplier;
    }

    public CacheKeyFactory getCacheKeyFactory() {
        return this.mCacheKeyFactory;
    }

    public Context getContext() {
        return this.mContext;
    }

    public Supplier<MemoryCacheParams> getEncodedMemoryCacheParamsSupplier() {
        return this.mEncodedMemoryCacheParamsSupplier;
    }

    public ExecutorSupplier getExecutorSupplier() {
        return this.mExecutorSupplier;
    }

    public ImagePipelineExperiments getExperiments() {
        return this.mImagePipelineExperiments;
    }

    public FileCacheFactory getFileCacheFactory() {
        return this.mFileCacheFactory;
    }

    public ImageCacheStatsTracker getImageCacheStatsTracker() {
        return this.mImageCacheStatsTracker;
    }

    @Nullable
    public ImageDecoder getImageDecoder() {
        return this.mImageDecoder;
    }

    public Supplier<Boolean> getIsPrefetchEnabledSupplier() {
        return this.mIsPrefetchEnabledSupplier;
    }

    public DiskCacheConfig getMainDiskCacheConfig() {
        return this.mMainDiskCacheConfig;
    }

    public MemoryTrimmableRegistry getMemoryTrimmableRegistry() {
        return this.mMemoryTrimmableRegistry;
    }

    public NetworkFetcher getNetworkFetcher() {
        return this.mNetworkFetcher;
    }

    public PoolFactory getPoolFactory() {
        return this.mPoolFactory;
    }

    public ProgressiveJpegConfig getProgressiveJpegConfig() {
        return this.mProgressiveJpegConfig;
    }

    public Set<RequestListener> getRequestListeners() {
        return Collections.unmodifiableSet(this.mRequestListeners);
    }

    public DiskCacheConfig getSmallImageDiskCacheConfig() {
        return this.mSmallImageDiskCacheConfig;
    }

    public boolean isDecodeMemoryFileEnabled() {
        return this.mDecodeMemoryFileEnabled;
    }

    public boolean isDownsampleEnabled() {
        return this.mDownsampleEnabled;
    }

    public boolean isResizeAndRotateEnabledForNetwork() {
        return this.mResizeAndRotateEnabledForNetwork;
    }

    public static class Builder {
        private AnimatedImageFactory mAnimatedImageFactory;
        private Bitmap.Config mBitmapConfig;
        private Supplier<MemoryCacheParams> mBitmapMemoryCacheParamsSupplier;
        private CacheKeyFactory mCacheKeyFactory;
        private final Context mContext;
        private boolean mDecodeMemoryFileEnabled;
        private boolean mDownsampleEnabled = false;
        private Supplier<MemoryCacheParams> mEncodedMemoryCacheParamsSupplier;
        private ExecutorSupplier mExecutorSupplier;
        private final ImagePipelineExperiments.Builder mExperimentsBuilder = new ImagePipelineExperiments.Builder(this);
        private FileCacheFactory mFileCacheFactory;
        private ImageCacheStatsTracker mImageCacheStatsTracker;
        private ImageDecoder mImageDecoder;
        private Supplier<Boolean> mIsPrefetchEnabledSupplier;
        private DiskCacheConfig mMainDiskCacheConfig;
        private MemoryTrimmableRegistry mMemoryTrimmableRegistry;
        private NetworkFetcher mNetworkFetcher;
        private PlatformBitmapFactory mPlatformBitmapFactory;
        private PoolFactory mPoolFactory;
        private ProgressiveJpegConfig mProgressiveJpegConfig;
        private Set<RequestListener> mRequestListeners;
        private boolean mResizeAndRotateEnabledForNetwork = true;
        private DiskCacheConfig mSmallImageDiskCacheConfig;

        private Builder(Context context) {
            this.mContext = Preconditions.checkNotNull(context);
        }

        public ImagePipelineConfig build() {
            return new ImagePipelineConfig(this);
        }

        public boolean isDownsampleEnabled() {
            return this.mDownsampleEnabled;
        }

        public Builder setMainDiskCacheConfig(DiskCacheConfig diskCacheConfig) {
            this.mMainDiskCacheConfig = diskCacheConfig;
            return this;
        }

        public Builder setMemoryTrimmableRegistry(MemoryTrimmableRegistry memoryTrimmableRegistry) {
            this.mMemoryTrimmableRegistry = memoryTrimmableRegistry;
            return this;
        }
    }

    public static class DefaultImageRequestConfig {
        private boolean mProgressiveRenderingEnabled = false;

        private DefaultImageRequestConfig() {
        }

        public boolean isProgressiveRenderingEnabled() {
            return this.mProgressiveRenderingEnabled;
        }
    }

}


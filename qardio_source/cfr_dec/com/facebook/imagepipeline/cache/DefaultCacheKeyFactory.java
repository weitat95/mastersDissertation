/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.cache;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheKey;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import javax.annotation.Nullable;

public class DefaultCacheKeyFactory
implements CacheKeyFactory {
    private static DefaultCacheKeyFactory sInstance = null;

    protected DefaultCacheKeyFactory() {
    }

    public static DefaultCacheKeyFactory getInstance() {
        synchronized (DefaultCacheKeyFactory.class) {
            if (sInstance == null) {
                sInstance = new DefaultCacheKeyFactory();
            }
            DefaultCacheKeyFactory defaultCacheKeyFactory = sInstance;
            return defaultCacheKeyFactory;
        }
    }

    @Override
    public CacheKey getBitmapCacheKey(ImageRequest imageRequest, Object object) {
        return new BitmapMemoryCacheKey(this.getCacheKeySourceUri(imageRequest.getSourceUri()).toString(), imageRequest.getResizeOptions(), imageRequest.getRotationOptions(), imageRequest.getImageDecodeOptions(), null, null, object);
    }

    protected Uri getCacheKeySourceUri(Uri uri) {
        return uri;
    }

    @Override
    public CacheKey getEncodedCacheKey(ImageRequest imageRequest, @Nullable Object object) {
        return new SimpleCacheKey(this.getCacheKeySourceUri(imageRequest.getSourceUri()).toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public CacheKey getPostprocessedBitmapCacheKey(ImageRequest imageRequest, Object object) {
        CacheKey cacheKey;
        Object object2 = imageRequest.getPostprocessor();
        if (object2 != null) {
            cacheKey = object2.getPostprocessorCacheKey();
            object2 = object2.getClass().getName();
            do {
                return new BitmapMemoryCacheKey(this.getCacheKeySourceUri(imageRequest.getSourceUri()).toString(), imageRequest.getResizeOptions(), imageRequest.getRotationOptions(), imageRequest.getImageDecodeOptions(), cacheKey, (String)object2, object);
                break;
            } while (true);
        }
        cacheKey = null;
        object2 = null;
        return new BitmapMemoryCacheKey(this.getCacheKeySourceUri(imageRequest.getSourceUri()).toString(), imageRequest.getResizeOptions(), imageRequest.getRotationOptions(), imageRequest.getImageDecodeOptions(), cacheKey, (String)object2, object);
    }
}


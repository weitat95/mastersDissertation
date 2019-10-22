/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.request.ImageRequest;
import javax.annotation.Nullable;

public interface CacheKeyFactory {
    public CacheKey getBitmapCacheKey(ImageRequest var1, Object var2);

    public CacheKey getEncodedCacheKey(ImageRequest var1, @Nullable Object var2);

    public CacheKey getPostprocessedBitmapCacheKey(ImageRequest var1, Object var2);
}


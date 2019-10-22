/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.request;

import android.graphics.Bitmap;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import javax.annotation.Nullable;

public interface Postprocessor {
    public String getName();

    @Nullable
    public CacheKey getPostprocessorCacheKey();

    public CloseableReference<Bitmap> process(Bitmap var1, PlatformBitmapFactory var2);
}


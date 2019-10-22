/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.Immutable
 */
package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.time.RealtimeSinceBootClock;
import com.facebook.common.util.HashCodeUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import java.util.Locale;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class BitmapMemoryCacheKey
implements CacheKey {
    private final long mCacheTime;
    private final Object mCallerContext;
    private final int mHash;
    private final ImageDecodeOptions mImageDecodeOptions;
    @Nullable
    private final CacheKey mPostprocessorCacheKey;
    @Nullable
    private final String mPostprocessorName;
    @Nullable
    private final ResizeOptions mResizeOptions;
    private final RotationOptions mRotationOptions;
    private final String mSourceString;

    /*
     * Enabled aggressive block sorting
     */
    public BitmapMemoryCacheKey(String string2, @Nullable ResizeOptions resizeOptions, RotationOptions rotationOptions, ImageDecodeOptions imageDecodeOptions, @Nullable CacheKey cacheKey, @Nullable String string3, Object object) {
        this.mSourceString = Preconditions.checkNotNull(string2);
        this.mResizeOptions = resizeOptions;
        this.mRotationOptions = rotationOptions;
        this.mImageDecodeOptions = imageDecodeOptions;
        this.mPostprocessorCacheKey = cacheKey;
        this.mPostprocessorName = string3;
        int n = string2.hashCode();
        int n2 = resizeOptions != null ? resizeOptions.hashCode() : 0;
        this.mHash = HashCodeUtil.hashCode((Object)n, (Object)n2, (Object)rotationOptions.hashCode(), this.mImageDecodeOptions, this.mPostprocessorCacheKey, string3);
        this.mCallerContext = object;
        this.mCacheTime = RealtimeSinceBootClock.get().now();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof BitmapMemoryCacheKey)) break block2;
                object = (BitmapMemoryCacheKey)object;
                if (this.mHash == ((BitmapMemoryCacheKey)object).mHash && this.mSourceString.equals(((BitmapMemoryCacheKey)object).mSourceString) && Objects.equal(this.mResizeOptions, ((BitmapMemoryCacheKey)object).mResizeOptions) && Objects.equal(this.mRotationOptions, ((BitmapMemoryCacheKey)object).mRotationOptions) && Objects.equal(this.mImageDecodeOptions, ((BitmapMemoryCacheKey)object).mImageDecodeOptions) && Objects.equal(this.mPostprocessorCacheKey, ((BitmapMemoryCacheKey)object).mPostprocessorCacheKey) && Objects.equal(this.mPostprocessorName, ((BitmapMemoryCacheKey)object).mPostprocessorName)) break block3;
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.mHash;
    }

    @Override
    public String toString() {
        return String.format((Locale)null, "%s_%s_%s_%s_%s_%s_%d", this.mSourceString, this.mResizeOptions, this.mRotationOptions, this.mImageDecodeOptions, this.mPostprocessorCacheKey, this.mPostprocessorName, this.mHash);
    }
}


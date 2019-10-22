/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.backends.pipeline;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableList;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawable.base.DrawableWithCaches;
import com.facebook.drawee.backends.pipeline.DrawableFactory;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.drawable.OrientedDrawable;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import java.io.Closeable;
import java.util.Iterator;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class PipelineDraweeController
extends AbstractDraweeController<CloseableReference<CloseableImage>, ImageInfo> {
    private static final Class<?> TAG = PipelineDraweeController.class;
    private final AnimatedDrawableFactory mAnimatedDrawableFactory;
    private CacheKey mCacheKey;
    private Supplier<DataSource<CloseableReference<CloseableImage>>> mDataSourceSupplier;
    private final DrawableFactory mDefaultDrawableFactory = new DrawableFactory(){

        @Override
        public Drawable createDrawable(CloseableImage closeableImage) {
            if (closeableImage instanceof CloseableStaticBitmap) {
                closeableImage = (CloseableStaticBitmap)closeableImage;
                BitmapDrawable bitmapDrawable = new BitmapDrawable(PipelineDraweeController.this.mResources, ((CloseableStaticBitmap)closeableImage).getUnderlyingBitmap());
                if (((CloseableStaticBitmap)closeableImage).getRotationAngle() == 0 || ((CloseableStaticBitmap)closeableImage).getRotationAngle() == -1) {
                    return bitmapDrawable;
                }
                return new OrientedDrawable((Drawable)bitmapDrawable, ((CloseableStaticBitmap)closeableImage).getRotationAngle());
            }
            if (PipelineDraweeController.this.mAnimatedDrawableFactory != null) {
                return PipelineDraweeController.this.mAnimatedDrawableFactory.create(closeableImage);
            }
            return null;
        }

        @Override
        public boolean supportsImageType(CloseableImage closeableImage) {
            return true;
        }
    };
    @Nullable
    private final ImmutableList<DrawableFactory> mDrawableFactories;
    @Nullable
    private MemoryCache<CacheKey, CloseableImage> mMemoryCache;
    private final Resources mResources;

    public PipelineDraweeController(Resources resources, DeferredReleaser deferredReleaser, AnimatedDrawableFactory animatedDrawableFactory, Executor executor, MemoryCache<CacheKey, CloseableImage> memoryCache, Supplier<DataSource<CloseableReference<CloseableImage>>> supplier, String string2, CacheKey cacheKey, Object object, @Nullable ImmutableList<DrawableFactory> immutableList) {
        super(deferredReleaser, executor, string2, object);
        this.mResources = resources;
        this.mAnimatedDrawableFactory = animatedDrawableFactory;
        this.mMemoryCache = memoryCache;
        this.mCacheKey = cacheKey;
        this.mDrawableFactories = immutableList;
        this.init(supplier);
    }

    private void init(Supplier<DataSource<CloseableReference<CloseableImage>>> supplier) {
        this.mDataSourceSupplier = supplier;
    }

    @Override
    protected Drawable createDrawable(CloseableReference<CloseableImage> closeable) {
        Drawable drawable2;
        Preconditions.checkState(CloseableReference.isValid(closeable));
        closeable = closeable.get();
        if (this.mDrawableFactories != null) {
            drawable2 = this.mDrawableFactories.iterator();
            while (drawable2.hasNext()) {
                DrawableFactory drawableFactory = (DrawableFactory)drawable2.next();
                if (!drawableFactory.supportsImageType((CloseableImage)closeable) || (drawableFactory = drawableFactory.createDrawable((CloseableImage)closeable)) == null) continue;
                return drawableFactory;
            }
        }
        if ((drawable2 = this.mDefaultDrawableFactory.createDrawable((CloseableImage)closeable)) != null) {
            return drawable2;
        }
        throw new UnsupportedOperationException("Unrecognized image class: " + closeable);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected CloseableReference<CloseableImage> getCachedImage() {
        CloseableReference<CloseableImage> closeableReference;
        if (this.mMemoryCache == null) return null;
        if (this.mCacheKey == null) {
            return null;
        }
        CloseableReference<CloseableImage> closeableReference2 = closeableReference = this.mMemoryCache.get(this.mCacheKey);
        if (closeableReference == null) return closeableReference2;
        closeableReference2 = closeableReference;
        if (closeableReference.get().getQualityInfo().isOfFullQuality()) return closeableReference2;
        closeableReference.close();
        return null;
    }

    @Override
    protected DataSource<CloseableReference<CloseableImage>> getDataSource() {
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x: getDataSource", (Object)System.identityHashCode(this));
        }
        return this.mDataSourceSupplier.get();
    }

    @Override
    protected int getImageHash(@Nullable CloseableReference<CloseableImage> closeableReference) {
        if (closeableReference != null) {
            return closeableReference.getValueHash();
        }
        return 0;
    }

    @Override
    protected ImageInfo getImageInfo(CloseableReference<CloseableImage> closeableReference) {
        Preconditions.checkState(CloseableReference.isValid(closeableReference));
        return closeableReference.get();
    }

    public void initialize(Supplier<DataSource<CloseableReference<CloseableImage>>> supplier, String string2, CacheKey cacheKey, Object object) {
        super.initialize(string2, object);
        this.init(supplier);
        this.mCacheKey = cacheKey;
    }

    @Override
    protected void releaseDrawable(@Nullable Drawable drawable2) {
        if (drawable2 instanceof DrawableWithCaches) {
            ((DrawableWithCaches)drawable2).dropCaches();
        }
    }

    @Override
    protected void releaseImage(@Nullable CloseableReference<CloseableImage> closeableReference) {
        CloseableReference.closeSafely(closeableReference);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("super", super.toString()).add("dataSourceSupplier", this.mDataSourceSupplier).toString();
    }

}


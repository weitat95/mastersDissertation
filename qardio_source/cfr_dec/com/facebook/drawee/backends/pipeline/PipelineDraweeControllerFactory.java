/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.backends.pipeline;

import android.content.res.Resources;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableList;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.DrawableFactory;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class PipelineDraweeControllerFactory {
    private AnimatedDrawableFactory mAnimatedDrawableFactory;
    private DeferredReleaser mDeferredReleaser;
    @Nullable
    private ImmutableList<DrawableFactory> mDrawableFactories;
    private MemoryCache<CacheKey, CloseableImage> mMemoryCache;
    private Resources mResources;
    private Executor mUiThreadExecutor;

    public PipelineDraweeControllerFactory(Resources resources, DeferredReleaser deferredReleaser, AnimatedDrawableFactory animatedDrawableFactory, Executor executor, MemoryCache<CacheKey, CloseableImage> memoryCache, @Nullable ImmutableList<DrawableFactory> immutableList) {
        this.mResources = resources;
        this.mDeferredReleaser = deferredReleaser;
        this.mAnimatedDrawableFactory = animatedDrawableFactory;
        this.mUiThreadExecutor = executor;
        this.mMemoryCache = memoryCache;
        this.mDrawableFactories = immutableList;
    }

    public PipelineDraweeController newController(Supplier<DataSource<CloseableReference<CloseableImage>>> supplier, String string2, CacheKey cacheKey, Object object) {
        return new PipelineDraweeController(this.mResources, this.mDeferredReleaser, this.mAnimatedDrawableFactory, this.mUiThreadExecutor, this.mMemoryCache, supplier, string2, cacheKey, object, this.mDrawableFactories);
    }
}


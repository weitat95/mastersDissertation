/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.backends.pipeline;

import android.content.Context;
import android.content.res.Resources;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.internal.ImmutableList;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.DrawableFactory;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerFactory;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.animated.factory.AnimatedFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class PipelineDraweeControllerBuilderSupplier
implements Supplier<PipelineDraweeControllerBuilder> {
    private final Set<ControllerListener> mBoundControllerListeners;
    private final Context mContext;
    private final ImagePipeline mImagePipeline;
    private final PipelineDraweeControllerFactory mPipelineDraweeControllerFactory;

    public PipelineDraweeControllerBuilderSupplier(Context context) {
        this(context, null);
    }

    public PipelineDraweeControllerBuilderSupplier(Context context, @Nullable DraweeConfig draweeConfig) {
        this(context, ImagePipelineFactory.getInstance(), draweeConfig);
    }

    public PipelineDraweeControllerBuilderSupplier(Context context, ImagePipelineFactory imagePipelineFactory, @Nullable DraweeConfig draweeConfig) {
        this(context, imagePipelineFactory, null, draweeConfig);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public PipelineDraweeControllerBuilderSupplier(Context object, ImagePipelineFactory object2, Set<ControllerListener> set, @Nullable DraweeConfig draweeConfig) {
        void var3_6;
        AnimatedDrawableFactory animatedDrawableFactory;
        void var1_3;
        void var4_7;
        this.mContext = object;
        this.mImagePipeline = ((ImagePipelineFactory)((Object)animatedDrawableFactory)).getImagePipeline();
        AnimatedFactory animatedFactory = ((ImagePipelineFactory)((Object)animatedDrawableFactory)).getAnimatedFactory();
        animatedDrawableFactory = null;
        if (animatedFactory != null) {
            animatedDrawableFactory = animatedFactory.getAnimatedDrawableFactory((Context)object);
        }
        animatedFactory = object.getResources();
        DeferredReleaser deferredReleaser = DeferredReleaser.getInstance();
        UiThreadImmediateExecutorService uiThreadImmediateExecutorService = UiThreadImmediateExecutorService.getInstance();
        MemoryCache<CacheKey, CloseableImage> memoryCache = this.mImagePipeline.getBitmapMemoryCache();
        if (var4_7 != null) {
            ImmutableList<DrawableFactory> immutableList = var4_7.getCustomDrawableFactories();
        } else {
            Object var1_4 = null;
        }
        this.mPipelineDraweeControllerFactory = new PipelineDraweeControllerFactory((Resources)animatedFactory, deferredReleaser, animatedDrawableFactory, uiThreadImmediateExecutorService, memoryCache, (ImmutableList<DrawableFactory>)var1_3);
        this.mBoundControllerListeners = var3_6;
    }

    @Override
    public PipelineDraweeControllerBuilder get() {
        return new PipelineDraweeControllerBuilder(this.mContext, this.mPipelineDraweeControllerFactory, this.mImagePipeline, this.mBoundControllerListeners);
    }
}


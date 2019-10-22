/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.backends.pipeline;

import android.content.Context;
import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerFactory;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import java.util.Set;
import javax.annotation.Nullable;

public class PipelineDraweeControllerBuilder
extends AbstractDraweeControllerBuilder<PipelineDraweeControllerBuilder, ImageRequest, CloseableReference<CloseableImage>, ImageInfo> {
    private final ImagePipeline mImagePipeline;
    private final PipelineDraweeControllerFactory mPipelineDraweeControllerFactory;

    public PipelineDraweeControllerBuilder(Context context, PipelineDraweeControllerFactory pipelineDraweeControllerFactory, ImagePipeline imagePipeline, Set<ControllerListener> set) {
        super(context, set);
        this.mImagePipeline = imagePipeline;
        this.mPipelineDraweeControllerFactory = pipelineDraweeControllerFactory;
    }

    public static ImageRequest.RequestLevel convertCacheLevelToRequestLevel(AbstractDraweeControllerBuilder.CacheLevel cacheLevel) {
        switch (1.$SwitchMap$com$facebook$drawee$controller$AbstractDraweeControllerBuilder$CacheLevel[cacheLevel.ordinal()]) {
            default: {
                throw new RuntimeException("Cache level" + (Object)((Object)cacheLevel) + "is not supported. ");
            }
            case 1: {
                return ImageRequest.RequestLevel.FULL_FETCH;
            }
            case 2: {
                return ImageRequest.RequestLevel.DISK_CACHE;
            }
            case 3: 
        }
        return ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE;
    }

    private CacheKey getCacheKey() {
        ImageRequest imageRequest;
        CacheKeyFactory cacheKeyFactory;
        block3: {
            CacheKey cacheKey;
            block2: {
                CacheKey cacheKey2;
                imageRequest = (ImageRequest)this.getImageRequest();
                cacheKeyFactory = this.mImagePipeline.getCacheKeyFactory();
                cacheKey = cacheKey2 = null;
                if (cacheKeyFactory == null) break block2;
                cacheKey = cacheKey2;
                if (imageRequest == null) break block2;
                if (imageRequest.getPostprocessor() == null) break block3;
                cacheKey = cacheKeyFactory.getPostprocessedBitmapCacheKey(imageRequest, this.getCallerContext());
            }
            return cacheKey;
        }
        return cacheKeyFactory.getBitmapCacheKey(imageRequest, this.getCallerContext());
    }

    @Override
    protected DataSource<CloseableReference<CloseableImage>> getDataSourceForRequest(ImageRequest imageRequest, Object object, AbstractDraweeControllerBuilder.CacheLevel cacheLevel) {
        return this.mImagePipeline.fetchDecodedImage(imageRequest, object, PipelineDraweeControllerBuilder.convertCacheLevelToRequestLevel(cacheLevel));
    }

    @Override
    protected PipelineDraweeControllerBuilder getThis() {
        return this;
    }

    @Override
    protected PipelineDraweeController obtainController() {
        DraweeController draweeController = this.getOldController();
        if (draweeController instanceof PipelineDraweeController) {
            draweeController = (PipelineDraweeController)draweeController;
            ((PipelineDraweeController)draweeController).initialize(this.obtainDataSourceSupplier(), PipelineDraweeControllerBuilder.generateUniqueControllerId(), this.getCacheKey(), this.getCallerContext());
            return draweeController;
        }
        return this.mPipelineDraweeControllerFactory.newController(this.obtainDataSourceSupplier(), PipelineDraweeControllerBuilder.generateUniqueControllerId(), this.getCacheKey(), this.getCallerContext());
    }

    @Override
    public PipelineDraweeControllerBuilder setUri(@Nullable Uri uri) {
        if (uri == null) {
            return (PipelineDraweeControllerBuilder)super.setImageRequest(null);
        }
        return (PipelineDraweeControllerBuilder)super.setImageRequest(ImageRequestBuilder.newBuilderWithSource(uri).setRotationOptions(RotationOptions.autoRotateAtRenderTime()).build());
    }

}


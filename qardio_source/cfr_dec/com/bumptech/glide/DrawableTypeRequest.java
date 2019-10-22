/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.ParcelFileDescriptor
 */
package com.bumptech.glide;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.model.ImageVideoModelLoader;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.provider.DataLoadProvider;
import com.bumptech.glide.provider.FixedLoadProvider;
import com.bumptech.glide.provider.LoadProvider;
import java.io.InputStream;

public class DrawableTypeRequest<ModelType>
extends DrawableRequestBuilder<ModelType> {
    private final ModelLoader<ModelType, ParcelFileDescriptor> fileDescriptorModelLoader;
    private final RequestManager.OptionsApplier optionsApplier;
    private final ModelLoader<ModelType, InputStream> streamModelLoader;

    DrawableTypeRequest(Class<ModelType> class_, ModelLoader<ModelType, InputStream> modelLoader, ModelLoader<ModelType, ParcelFileDescriptor> modelLoader2, Context context, Glide glide, RequestTracker requestTracker, Lifecycle lifecycle, RequestManager.OptionsApplier optionsApplier) {
        super(context, class_, DrawableTypeRequest.buildProvider(glide, modelLoader, modelLoader2, GifBitmapWrapper.class, GlideDrawable.class, null), glide, requestTracker, lifecycle);
        this.streamModelLoader = modelLoader;
        this.fileDescriptorModelLoader = modelLoader2;
        this.optionsApplier = optionsApplier;
    }

    private static <A, Z, R> FixedLoadProvider<A, ImageVideoWrapper, Z, R> buildProvider(Glide object, ModelLoader<A, InputStream> modelLoader, ModelLoader<A, ParcelFileDescriptor> modelLoader2, Class<Z> class_, Class<R> class_2, ResourceTranscoder<Z, R> resourceTranscoder) {
        if (modelLoader == null && modelLoader2 == null) {
            return null;
        }
        ResourceTranscoder<Z, R> resourceTranscoder2 = resourceTranscoder;
        if (resourceTranscoder == null) {
            resourceTranscoder2 = ((Glide)object).buildTranscoder(class_, class_2);
        }
        object = ((Glide)object).buildDataProvider(ImageVideoWrapper.class, class_);
        return new FixedLoadProvider(new ImageVideoModelLoader<A>(modelLoader, modelLoader2), resourceTranscoder2, object);
    }

    public BitmapTypeRequest<ModelType> asBitmap() {
        return this.optionsApplier.apply(new BitmapTypeRequest<ModelType>(this, this.streamModelLoader, this.fileDescriptorModelLoader, this.optionsApplier));
    }
}


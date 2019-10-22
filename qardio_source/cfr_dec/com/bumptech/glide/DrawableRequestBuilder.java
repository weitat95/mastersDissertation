/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.ImageView
 */
package com.bumptech.glide;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapperTransformation;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.provider.LoadProvider;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.bumptech.glide.request.animation.GlideAnimationFactory;
import com.bumptech.glide.request.target.Target;

public class DrawableRequestBuilder<ModelType>
extends GenericRequestBuilder<ModelType, ImageVideoWrapper, GifBitmapWrapper, GlideDrawable> {
    DrawableRequestBuilder(Context context, Class<ModelType> class_, LoadProvider<ModelType, ImageVideoWrapper, GifBitmapWrapper, GlideDrawable> loadProvider, Glide glide, RequestTracker requestTracker, Lifecycle lifecycle) {
        super(context, class_, loadProvider, GlideDrawable.class, glide, requestTracker, lifecycle);
        this.crossFade();
    }

    @Override
    void applyCenterCrop() {
        this.centerCrop();
    }

    @Override
    void applyFitCenter() {
        this.fitCenter();
    }

    public DrawableRequestBuilder<ModelType> centerCrop() {
        return this.transform(new Transformation[]{this.glide.getDrawableCenterCrop()});
    }

    @Override
    public DrawableRequestBuilder<ModelType> clone() {
        return (DrawableRequestBuilder)super.clone();
    }

    public final DrawableRequestBuilder<ModelType> crossFade() {
        super.animate(new DrawableCrossFadeFactory());
        return this;
    }

    public DrawableRequestBuilder<ModelType> decoder(ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper> resourceDecoder) {
        super.decoder(resourceDecoder);
        return this;
    }

    public DrawableRequestBuilder<ModelType> diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
        super.diskCacheStrategy(diskCacheStrategy);
        return this;
    }

    public DrawableRequestBuilder<ModelType> fitCenter() {
        return this.transform(new Transformation[]{this.glide.getDrawableFitCenter()});
    }

    @Override
    public Target<GlideDrawable> into(ImageView imageView) {
        return super.into(imageView);
    }

    public DrawableRequestBuilder<ModelType> listener(RequestListener<? super ModelType, GlideDrawable> requestListener) {
        super.listener(requestListener);
        return this;
    }

    public DrawableRequestBuilder<ModelType> load(ModelType ModelType) {
        super.load(ModelType);
        return this;
    }

    public DrawableRequestBuilder<ModelType> override(int n, int n2) {
        super.override(n, n2);
        return this;
    }

    public DrawableRequestBuilder<ModelType> signature(Key key) {
        super.signature(key);
        return this;
    }

    public DrawableRequestBuilder<ModelType> skipMemoryCache(boolean bl) {
        super.skipMemoryCache(bl);
        return this;
    }

    public DrawableRequestBuilder<ModelType> sourceEncoder(Encoder<ImageVideoWrapper> encoder) {
        super.sourceEncoder(encoder);
        return this;
    }

    public DrawableRequestBuilder<ModelType> transform(Transformation<GifBitmapWrapper> ... arrtransformation) {
        super.transform(arrtransformation);
        return this;
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.ParcelFileDescriptor
 *  android.widget.ImageView
 */
package com.bumptech.glide;

import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.FileDescriptorBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.provider.LoadProvider;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import java.io.InputStream;

public class BitmapRequestBuilder<ModelType, TranscodeType>
extends GenericRequestBuilder<ModelType, ImageVideoWrapper, Bitmap, TranscodeType> {
    private final BitmapPool bitmapPool;
    private DecodeFormat decodeFormat;
    private Downsampler downsampler = Downsampler.AT_LEAST;
    private ResourceDecoder<InputStream, Bitmap> imageDecoder;
    private ResourceDecoder<ParcelFileDescriptor, Bitmap> videoDecoder;

    BitmapRequestBuilder(LoadProvider<ModelType, ImageVideoWrapper, Bitmap, TranscodeType> loadProvider, Class<TranscodeType> class_, GenericRequestBuilder<ModelType, ?, ?, ?> genericRequestBuilder) {
        super(loadProvider, class_, genericRequestBuilder);
        this.bitmapPool = genericRequestBuilder.glide.getBitmapPool();
        this.decodeFormat = genericRequestBuilder.glide.getDecodeFormat();
        this.imageDecoder = new StreamBitmapDecoder(this.bitmapPool, this.decodeFormat);
        this.videoDecoder = new FileDescriptorBitmapDecoder(this.bitmapPool, this.decodeFormat);
    }

    @Override
    void applyCenterCrop() {
        this.centerCrop();
    }

    @Override
    void applyFitCenter() {
        this.fitCenter();
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> centerCrop() {
        return this.transform(new BitmapTransformation[]{this.glide.getBitmapCenterCrop()});
    }

    @Override
    public BitmapRequestBuilder<ModelType, TranscodeType> clone() {
        return (BitmapRequestBuilder)super.clone();
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> decoder(ResourceDecoder<ImageVideoWrapper, Bitmap> resourceDecoder) {
        super.decoder(resourceDecoder);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
        super.diskCacheStrategy(diskCacheStrategy);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> fitCenter() {
        return this.transform(new BitmapTransformation[]{this.glide.getBitmapFitCenter()});
    }

    @Override
    public Target<TranscodeType> into(ImageView imageView) {
        return super.into(imageView);
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> listener(RequestListener<? super ModelType, TranscodeType> requestListener) {
        super.listener(requestListener);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> load(ModelType ModelType) {
        super.load(ModelType);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> override(int n, int n2) {
        super.override(n, n2);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> signature(Key key) {
        super.signature(key);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> skipMemoryCache(boolean bl) {
        super.skipMemoryCache(bl);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> sourceEncoder(Encoder<ImageVideoWrapper> encoder) {
        super.sourceEncoder(encoder);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> transform(Transformation<Bitmap> ... arrtransformation) {
        super.transform(arrtransformation);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> transform(BitmapTransformation ... arrbitmapTransformation) {
        super.transform(arrbitmapTransformation);
        return this;
    }
}


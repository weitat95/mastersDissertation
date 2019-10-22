/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 */
package com.facebook.imagepipeline.decoder;

import android.graphics.Bitmap;
import com.facebook.common.internal.Closeables;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.GifFormatChecker;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.platform.PlatformDecoder;
import java.io.Closeable;
import java.io.InputStream;

public class ImageDecoder {
    private final AnimatedImageFactory mAnimatedImageFactory;
    private final Bitmap.Config mBitmapConfig;
    private final PlatformDecoder mPlatformDecoder;

    public ImageDecoder(AnimatedImageFactory animatedImageFactory, PlatformDecoder platformDecoder, Bitmap.Config config) {
        this.mAnimatedImageFactory = animatedImageFactory;
        this.mBitmapConfig = config;
        this.mPlatformDecoder = platformDecoder;
    }

    public CloseableImage decodeAnimatedWebp(EncodedImage encodedImage, ImageDecodeOptions imageDecodeOptions) {
        return this.mAnimatedImageFactory.decodeWebP(encodedImage, imageDecodeOptions, this.mBitmapConfig);
    }

    public CloseableImage decodeGif(EncodedImage closeable, ImageDecodeOptions imageDecodeOptions) {
        InputStream inputStream = closeable.getInputStream();
        if (inputStream == null) {
            return null;
        }
        try {
            if (!imageDecodeOptions.forceStaticImage && this.mAnimatedImageFactory != null && GifFormatChecker.isAnimated(inputStream)) {
                closeable = this.mAnimatedImageFactory.decodeGif((EncodedImage)closeable, imageDecodeOptions, this.mBitmapConfig);
                return closeable;
            }
            closeable = this.decodeStaticImage((EncodedImage)closeable);
            return closeable;
        }
        finally {
            Closeables.closeQuietly(inputStream);
        }
    }

    public CloseableImage decodeImage(EncodedImage encodedImage, int n, QualityInfo qualityInfo, ImageDecodeOptions imageDecodeOptions) {
        ImageFormat imageFormat;
        block8: {
            block7: {
                ImageFormat imageFormat2 = encodedImage.getImageFormat();
                if (imageFormat2 == null) break block7;
                imageFormat = imageFormat2;
                if (imageFormat2 != ImageFormat.UNKNOWN) break block8;
            }
            imageFormat = ImageFormatChecker.getImageFormat_WrapIOException(encodedImage.getInputStream());
            encodedImage.setImageFormat(imageFormat);
        }
        if (imageFormat == DefaultImageFormats.JPEG) {
            return this.decodeJpeg(encodedImage, n, qualityInfo);
        }
        if (imageFormat == DefaultImageFormats.GIF) {
            return this.decodeGif(encodedImage, imageDecodeOptions);
        }
        if (imageFormat == DefaultImageFormats.WEBP_ANIMATED) {
            return this.decodeAnimatedWebp(encodedImage, imageDecodeOptions);
        }
        if (imageFormat == ImageFormat.UNKNOWN) {
            throw new IllegalArgumentException("unknown image format");
        }
        return this.decodeStaticImage(encodedImage);
    }

    public CloseableStaticBitmap decodeJpeg(EncodedImage closeable, int n, QualityInfo qualityInfo) {
        CloseableReference<Bitmap> closeableReference = this.mPlatformDecoder.decodeJPEGFromEncodedImage((EncodedImage)closeable, this.mBitmapConfig, n);
        try {
            closeable = new CloseableStaticBitmap(closeableReference, qualityInfo, closeable.getRotationAngle());
            return closeable;
        }
        finally {
            closeableReference.close();
        }
    }

    public CloseableStaticBitmap decodeStaticImage(EncodedImage closeable) {
        CloseableReference<Bitmap> closeableReference = this.mPlatformDecoder.decodeFromEncodedImage((EncodedImage)closeable, this.mBitmapConfig);
        try {
            closeable = new CloseableStaticBitmap(closeableReference, ImmutableQualityInfo.FULL_QUALITY, closeable.getRotationAngle());
            return closeable;
        }
        finally {
            closeableReference.close();
        }
    }
}


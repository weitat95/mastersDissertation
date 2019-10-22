/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 */
package com.facebook.imagepipeline.animated.factory;

import android.graphics.Bitmap;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.EncodedImage;

public interface AnimatedImageFactory {
    public CloseableImage decodeGif(EncodedImage var1, ImageDecodeOptions var2, Bitmap.Config var3);

    public CloseableImage decodeWebP(EncodedImage var1, ImageDecodeOptions var2, Bitmap.Config var3);
}


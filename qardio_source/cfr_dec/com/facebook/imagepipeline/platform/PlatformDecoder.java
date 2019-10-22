/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 */
package com.facebook.imagepipeline.platform;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;

public interface PlatformDecoder {
    public CloseableReference<Bitmap> decodeFromEncodedImage(EncodedImage var1, Bitmap.Config var2);

    public CloseableReference<Bitmap> decodeJPEGFromEncodedImage(EncodedImage var1, Bitmap.Config var2, int var3);
}


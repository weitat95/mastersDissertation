/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;

public final class ThumbnailSizeChecker {
    public static int getAcceptableSize(int n) {
        return (int)((float)n * 1.3333334f);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isImageBigEnough(int n, int n2, ResizeOptions resizeOptions) {
        if (resizeOptions == null) {
            if ((float)ThumbnailSizeChecker.getAcceptableSize(n) >= 2048.0f && ThumbnailSizeChecker.getAcceptableSize(n2) >= 2048) return true;
            return false;
        }
        if (ThumbnailSizeChecker.getAcceptableSize(n) < resizeOptions.width || ThumbnailSizeChecker.getAcceptableSize(n2) < resizeOptions.height) return false;
        return true;
    }

    public static boolean isImageBigEnough(EncodedImage encodedImage, ResizeOptions resizeOptions) {
        if (encodedImage == null) {
            return false;
        }
        switch (encodedImage.getRotationAngle()) {
            default: {
                return ThumbnailSizeChecker.isImageBigEnough(encodedImage.getWidth(), encodedImage.getHeight(), resizeOptions);
            }
            case 90: 
            case 270: 
        }
        return ThumbnailSizeChecker.isImageBigEnough(encodedImage.getHeight(), encodedImage.getWidth(), resizeOptions);
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.imagepipeline.producers;

import android.net.Uri;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;

public class DownsampleUtil {
    /*
     * Enabled aggressive block sorting
     */
    static float determineDownsampleRatio(ImageRequest imageRequest, EncodedImage encodedImage) {
        Preconditions.checkArgument(EncodedImage.isMetaDataAvailable(encodedImage));
        ResizeOptions resizeOptions = imageRequest.getResizeOptions();
        if (resizeOptions == null || resizeOptions.height <= 0 || resizeOptions.width <= 0 || encodedImage.getWidth() == 0 || encodedImage.getHeight() == 0) {
            return 1.0f;
        }
        int n = DownsampleUtil.getRotationAngle(imageRequest, encodedImage);
        int n2 = n == 90 || n == 270 ? 1 : 0;
        n = n2 != 0 ? encodedImage.getHeight() : encodedImage.getWidth();
        n2 = n2 != 0 ? encodedImage.getWidth() : encodedImage.getHeight();
        float f = (float)resizeOptions.width / (float)n;
        float f2 = (float)resizeOptions.height / (float)n2;
        float f3 = Math.max(f, f2);
        FLog.v("DownsampleUtil", "Downsample - Specified size: %dx%d, image size: %dx%d ratio: %.1f x %.1f, ratio: %.3f for %s", resizeOptions.width, resizeOptions.height, n, n2, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), imageRequest.getSourceUri().toString());
        return f3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int determineSampleSize(ImageRequest imageRequest, EncodedImage encodedImage) {
        if (!EncodedImage.isMetaDataAvailable(encodedImage)) {
            return 1;
        }
        float f = DownsampleUtil.determineDownsampleRatio(imageRequest, encodedImage);
        int n = encodedImage.getImageFormat() == DefaultImageFormats.JPEG ? DownsampleUtil.ratioToSampleSizeJPEG(f) : DownsampleUtil.ratioToSampleSize(f);
        int n2 = Math.max(encodedImage.getHeight(), encodedImage.getWidth());
        do {
            int n3 = ++n;
            if (!((float)(n2 / n) > 2048.0f)) return n3;
            if (encodedImage.getImageFormat() != DefaultImageFormats.JPEG) continue;
            n *= 2;
        } while (true);
    }

    private static int getRotationAngle(ImageRequest imageRequest, EncodedImage encodedImage) {
        boolean bl = false;
        if (!imageRequest.getRotationOptions().useImageMetadata()) {
            return 0;
        }
        int n = encodedImage.getRotationAngle();
        if (n == 0 || n == 90 || n == 180 || n == 270) {
            bl = true;
        }
        Preconditions.checkArgument(bl);
        return n;
    }

    static int ratioToSampleSize(float f) {
        if (f > 0.6666667f) {
            return 1;
        }
        int n = 2;
        double d;
        while (!(1.0 / (double)n + 0.3333333432674408 * (d = 1.0 / (Math.pow(n, 2.0) - (double)n)) <= (double)f)) {
            ++n;
        }
        return n - 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int ratioToSampleSizeJPEG(float f) {
        if (f > 0.6666667f) {
            return 1;
        }
        int n = 2;
        do {
            double d = 1.0 / (double)(n * 2);
            int n2 = n;
            if (1.0 / (double)(n * 2) + 0.3333333432674408 * d <= (double)f) return n2;
            n *= 2;
        } while (true);
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imageformat;

import com.facebook.imageformat.ImageFormat;

public final class DefaultImageFormats {
    public static final ImageFormat BMP;
    public static final ImageFormat GIF;
    public static final ImageFormat JPEG;
    public static final ImageFormat PNG;
    public static final ImageFormat WEBP_ANIMATED;
    public static final ImageFormat WEBP_EXTENDED;
    public static final ImageFormat WEBP_EXTENDED_WITH_ALPHA;
    public static final ImageFormat WEBP_LOSSLESS;
    public static final ImageFormat WEBP_SIMPLE;

    static {
        JPEG = new ImageFormat("JPEG", "jpeg");
        PNG = new ImageFormat("PNG", "png");
        GIF = new ImageFormat("GIF", "gif");
        BMP = new ImageFormat("BMP", "bmp");
        WEBP_SIMPLE = new ImageFormat("WEBP_SIMPLE", "webp");
        WEBP_LOSSLESS = new ImageFormat("WEBP_LOSSLESS", "webp");
        WEBP_EXTENDED = new ImageFormat("WEBP_EXTENDED", "webp");
        WEBP_EXTENDED_WITH_ALPHA = new ImageFormat("WEBP_EXTENDED_WITH_ALPHA", "webp");
        WEBP_ANIMATED = new ImageFormat("WEBP_ANIMATED", "webp");
    }

    public static boolean isStaticWebpFormat(ImageFormat imageFormat) {
        return imageFormat == WEBP_SIMPLE || imageFormat == WEBP_LOSSLESS || imageFormat == WEBP_EXTENDED || imageFormat == WEBP_EXTENDED_WITH_ALPHA;
    }

    public static boolean isWebpFormat(ImageFormat imageFormat) {
        return DefaultImageFormats.isStaticWebpFormat(imageFormat) || imageFormat == WEBP_ANIMATED;
    }
}


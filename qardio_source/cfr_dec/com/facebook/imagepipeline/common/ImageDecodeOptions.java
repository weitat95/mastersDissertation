/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.Immutable
 */
package com.facebook.imagepipeline.common;

import com.facebook.imagepipeline.common.ImageDecodeOptionsBuilder;
import java.util.Locale;
import javax.annotation.concurrent.Immutable;

@Immutable
public class ImageDecodeOptions {
    private static final ImageDecodeOptions DEFAULTS = ImageDecodeOptions.newBuilder().build();
    public final boolean decodeAllFrames;
    public final boolean decodePreviewFrame;
    public final boolean forceStaticImage;
    public final int minDecodeIntervalMs;
    public final boolean useLastFrameForPreview;

    public ImageDecodeOptions(ImageDecodeOptionsBuilder imageDecodeOptionsBuilder) {
        this.minDecodeIntervalMs = imageDecodeOptionsBuilder.getMinDecodeIntervalMs();
        this.decodePreviewFrame = imageDecodeOptionsBuilder.getDecodePreviewFrame();
        this.useLastFrameForPreview = imageDecodeOptionsBuilder.getUseLastFrameForPreview();
        this.decodeAllFrames = imageDecodeOptionsBuilder.getDecodeAllFrames();
        this.forceStaticImage = imageDecodeOptionsBuilder.getForceStaticImage();
    }

    public static ImageDecodeOptions defaults() {
        return DEFAULTS;
    }

    public static ImageDecodeOptionsBuilder newBuilder() {
        return new ImageDecodeOptionsBuilder();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block8: {
            block7: {
                if (this == object) break block7;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (ImageDecodeOptions)object;
                if (this.decodePreviewFrame != ((ImageDecodeOptions)object).decodePreviewFrame) {
                    return false;
                }
                if (this.useLastFrameForPreview != ((ImageDecodeOptions)object).useLastFrameForPreview) {
                    return false;
                }
                if (this.decodeAllFrames != ((ImageDecodeOptions)object).decodeAllFrames) {
                    return false;
                }
                if (this.forceStaticImage != ((ImageDecodeOptions)object).forceStaticImage) break block8;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 1;
        int n2 = this.minDecodeIntervalMs;
        int n3 = this.decodePreviewFrame ? 1 : 0;
        int n4 = this.useLastFrameForPreview ? 1 : 0;
        int n5 = this.decodeAllFrames ? 1 : 0;
        if (this.forceStaticImage) {
            return (((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n;
        }
        n = 0;
        return (((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n;
    }

    public String toString() {
        return String.format((Locale)null, "%d-%b-%b-%b-%b", this.minDecodeIntervalMs, this.decodePreviewFrame, this.useLastFrameForPreview, this.decodeAllFrames, this.forceStaticImage);
    }
}


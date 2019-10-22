/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.common;

import com.facebook.imagepipeline.common.ImageDecodeOptions;

public class ImageDecodeOptionsBuilder {
    private boolean mDecodeAllFrames;
    private boolean mDecodePreviewFrame;
    private boolean mForceStaticImage;
    private int mMinDecodeIntervalMs = 100;
    private boolean mUseLastFrameForPreview;

    public ImageDecodeOptions build() {
        return new ImageDecodeOptions(this);
    }

    public boolean getDecodeAllFrames() {
        return this.mDecodeAllFrames;
    }

    public boolean getDecodePreviewFrame() {
        return this.mDecodePreviewFrame;
    }

    public boolean getForceStaticImage() {
        return this.mForceStaticImage;
    }

    public int getMinDecodeIntervalMs() {
        return this.mMinDecodeIntervalMs;
    }

    public boolean getUseLastFrameForPreview() {
        return this.mUseLastFrameForPreview;
    }
}


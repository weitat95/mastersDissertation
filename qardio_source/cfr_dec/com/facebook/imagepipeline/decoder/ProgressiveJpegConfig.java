/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.decoder;

import com.facebook.imagepipeline.image.QualityInfo;

public interface ProgressiveJpegConfig {
    public int getNextScanNumberToDecode(int var1);

    public QualityInfo getQualityInfo(int var1);
}


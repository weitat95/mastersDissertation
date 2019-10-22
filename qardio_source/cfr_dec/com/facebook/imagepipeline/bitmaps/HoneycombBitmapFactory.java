/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.bitmaps;

import android.annotation.TargetApi;
import com.facebook.imagepipeline.bitmaps.EmptyJpegGenerator;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.platform.PlatformDecoder;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(value=11)
@ThreadSafe
public class HoneycombBitmapFactory
extends PlatformBitmapFactory {
    private final EmptyJpegGenerator mJpegGenerator;
    private final PlatformDecoder mPurgeableDecoder;

    public HoneycombBitmapFactory(EmptyJpegGenerator emptyJpegGenerator, PlatformDecoder platformDecoder) {
        this.mJpegGenerator = emptyJpegGenerator;
        this.mPurgeableDecoder = platformDecoder;
    }
}


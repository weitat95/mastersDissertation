/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.bitmaps;

import android.annotation.TargetApi;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.memory.BitmapPool;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(value=21)
@ThreadSafe
public class ArtBitmapFactory
extends PlatformBitmapFactory {
    private final BitmapPool mBitmapPool;

    public ArtBitmapFactory(BitmapPool bitmapPool) {
        this.mBitmapPool = bitmapPool;
    }
}


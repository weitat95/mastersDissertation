/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.facebook.imagepipeline.bitmaps;

import android.graphics.Bitmap;
import com.facebook.common.references.ResourceReleaser;

public class SimpleBitmapReleaser
implements ResourceReleaser<Bitmap> {
    private static SimpleBitmapReleaser sInstance;

    private SimpleBitmapReleaser() {
    }

    public static SimpleBitmapReleaser getInstance() {
        if (sInstance == null) {
            sInstance = new SimpleBitmapReleaser();
        }
        return sInstance;
    }

    @Override
    public void release(Bitmap bitmap) {
        bitmap.recycle();
    }
}


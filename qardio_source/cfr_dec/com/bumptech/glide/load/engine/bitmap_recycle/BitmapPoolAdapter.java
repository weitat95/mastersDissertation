/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 */
package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

public class BitmapPoolAdapter
implements BitmapPool {
    @Override
    public void clearMemory() {
    }

    @Override
    public Bitmap get(int n, int n2, Bitmap.Config config) {
        return null;
    }

    @Override
    public Bitmap getDirty(int n, int n2, Bitmap.Config config) {
        return null;
    }

    @Override
    public boolean put(Bitmap bitmap) {
        return false;
    }

    @Override
    public void trimMemory(int n) {
    }
}


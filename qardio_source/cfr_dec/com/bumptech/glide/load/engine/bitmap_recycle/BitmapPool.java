/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 */
package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;

public interface BitmapPool {
    public void clearMemory();

    public Bitmap get(int var1, int var2, Bitmap.Config var3);

    public Bitmap getDirty(int var1, int var2, Bitmap.Config var3);

    public boolean put(Bitmap var1);

    public void trimMemory(int var1);
}


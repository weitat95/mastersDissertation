/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 */
package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.util.Util;

public abstract class BitmapTransformation
implements Transformation<Bitmap> {
    private BitmapPool bitmapPool;

    public BitmapTransformation(Context context) {
        this(Glide.get(context).getBitmapPool());
    }

    public BitmapTransformation(BitmapPool bitmapPool) {
        this.bitmapPool = bitmapPool;
    }

    protected abstract Bitmap transform(BitmapPool var1, Bitmap var2, int var3, int var4);

    @Override
    public final Resource<Bitmap> transform(Resource<Bitmap> resource, int n, int n2) {
        Bitmap bitmap;
        if (!Util.isValidDimensions(n, n2)) {
            throw new IllegalArgumentException("Cannot apply transformation on width: " + n + " or height: " + n2 + " less than or equal to zero and not Target.SIZE_ORIGINAL");
        }
        Bitmap bitmap2 = resource.get();
        if (n == Integer.MIN_VALUE) {
            n = bitmap2.getWidth();
        }
        if (n2 == Integer.MIN_VALUE) {
            n2 = bitmap2.getHeight();
        }
        if (bitmap2.equals((Object)(bitmap = this.transform(this.bitmapPool, bitmap2, n, n2)))) {
            return resource;
        }
        return BitmapResource.obtain(bitmap, this.bitmapPool);
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.memory;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.memory.BasePool;
import com.facebook.imagepipeline.memory.PoolParams;
import com.facebook.imagepipeline.memory.PoolStatsTracker;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(value=21)
@ThreadSafe
public class BitmapPool
extends BasePool<Bitmap> {
    public BitmapPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        this.initialize();
    }

    @Override
    protected Bitmap alloc(int n) {
        return Bitmap.createBitmap((int)1, (int)((int)Math.ceil((double)n / 2.0)), (Bitmap.Config)Bitmap.Config.RGB_565);
    }

    @Override
    protected void free(Bitmap bitmap) {
        Preconditions.checkNotNull(bitmap);
        bitmap.recycle();
    }

    @Override
    protected int getBucketedSize(int n) {
        return n;
    }

    @Override
    protected int getBucketedSizeForValue(Bitmap bitmap) {
        Preconditions.checkNotNull(bitmap);
        return bitmap.getAllocationByteCount();
    }

    @Override
    protected int getSizeInBytes(int n) {
        return n;
    }

    @Override
    protected boolean isReusable(Bitmap bitmap) {
        Preconditions.checkNotNull(bitmap);
        return !bitmap.isRecycled() && bitmap.isMutable();
    }
}


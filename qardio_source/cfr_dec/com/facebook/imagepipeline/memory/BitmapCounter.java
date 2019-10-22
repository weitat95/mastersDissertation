/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  javax.annotation.concurrent.GuardedBy
 */
package com.facebook.imagepipeline.memory;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imageutils.BitmapUtil;
import javax.annotation.concurrent.GuardedBy;

public class BitmapCounter {
    @GuardedBy(value="this")
    private int mCount;
    private final int mMaxCount;
    private final int mMaxSize;
    @GuardedBy(value="this")
    private long mSize;
    private final ResourceReleaser<Bitmap> mUnpooledBitmapsReleaser;

    /*
     * Enabled aggressive block sorting
     */
    public BitmapCounter(int n, int n2) {
        boolean bl = true;
        boolean bl2 = n > 0;
        Preconditions.checkArgument(bl2);
        bl2 = n2 > 0 ? bl : false;
        Preconditions.checkArgument(bl2);
        this.mMaxCount = n;
        this.mMaxSize = n2;
        this.mUnpooledBitmapsReleaser = new ResourceReleaser<Bitmap>(){

            @Override
            public void release(Bitmap bitmap) {
                try {
                    BitmapCounter.this.decrease(bitmap);
                    return;
                }
                finally {
                    bitmap.recycle();
                }
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void decrease(Bitmap bitmap) {
        boolean bl = true;
        synchronized (this) {
            int n = BitmapUtil.getSizeInBytes(bitmap);
            boolean bl2 = this.mCount > 0;
            Preconditions.checkArgument(bl2, "No bitmaps registered.");
            bl2 = (long)n <= this.mSize ? bl : false;
            Preconditions.checkArgument(bl2, "Bitmap size bigger than the total registered size: %d, %d", n, this.mSize);
            this.mSize -= (long)n;
            --this.mCount;
            return;
        }
    }

    public ResourceReleaser<Bitmap> getReleaser() {
        return this.mUnpooledBitmapsReleaser;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean increase(Bitmap bitmap) {
        synchronized (this) {
            int n = BitmapUtil.getSizeInBytes(bitmap);
            if (this.mCount >= this.mMaxCount) return false;
            long l = this.mSize;
            long l2 = n;
            int n2 = this.mMaxSize;
            if (l + l2 > (long)n2) {
                return false;
            }
            ++this.mCount;
            this.mSize += (long)n;
            return true;
        }
    }

}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  javax.annotation.concurrent.GuardedBy
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.image;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imageutils.BitmapUtil;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class CloseableStaticBitmap
extends CloseableBitmap {
    private volatile Bitmap mBitmap;
    @GuardedBy(value="this")
    private CloseableReference<Bitmap> mBitmapReference;
    private final QualityInfo mQualityInfo;
    private final int mRotationAngle;

    public CloseableStaticBitmap(Bitmap bitmap, ResourceReleaser<Bitmap> resourceReleaser, QualityInfo qualityInfo, int n) {
        this.mBitmap = Preconditions.checkNotNull(bitmap);
        this.mBitmapReference = CloseableReference.of(this.mBitmap, Preconditions.checkNotNull(resourceReleaser));
        this.mQualityInfo = qualityInfo;
        this.mRotationAngle = n;
    }

    public CloseableStaticBitmap(CloseableReference<Bitmap> closeableReference, QualityInfo qualityInfo, int n) {
        this.mBitmapReference = Preconditions.checkNotNull(closeableReference.cloneOrNull());
        this.mBitmap = this.mBitmapReference.get();
        this.mQualityInfo = qualityInfo;
        this.mRotationAngle = n;
    }

    private CloseableReference<Bitmap> detachBitmapReference() {
        synchronized (this) {
            CloseableReference<Bitmap> closeableReference = this.mBitmapReference;
            this.mBitmapReference = null;
            this.mBitmap = null;
            return closeableReference;
        }
    }

    @Override
    public void close() {
        CloseableReference<Bitmap> closeableReference = this.detachBitmapReference();
        if (closeableReference != null) {
            closeableReference.close();
        }
    }

    @Override
    public QualityInfo getQualityInfo() {
        return this.mQualityInfo;
    }

    public int getRotationAngle() {
        return this.mRotationAngle;
    }

    @Override
    public int getSizeInBytes() {
        return BitmapUtil.getSizeInBytes(this.mBitmap);
    }

    public Bitmap getUnderlyingBitmap() {
        return this.mBitmap;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isClosed() {
        synchronized (this) {
            CloseableReference<Bitmap> closeableReference = this.mBitmapReference;
            if (closeableReference != null) return false;
            return true;
        }
    }
}


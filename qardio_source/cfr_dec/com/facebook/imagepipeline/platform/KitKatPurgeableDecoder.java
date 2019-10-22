/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.platform;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.FlexByteArrayPool;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.platform.DalvikPurgeableDecoder;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(value=19)
@ThreadSafe
public class KitKatPurgeableDecoder
extends DalvikPurgeableDecoder {
    private final FlexByteArrayPool mFlexByteArrayPool;

    public KitKatPurgeableDecoder(FlexByteArrayPool flexByteArrayPool) {
        this.mFlexByteArrayPool = flexByteArrayPool;
    }

    private static void putEOI(byte[] arrby, int n) {
        arrby[n] = -1;
        arrby[n + 1] = -39;
    }

    @Override
    protected Bitmap decodeByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> closeableReference, BitmapFactory.Options options) {
        PooledByteBuffer pooledByteBuffer = closeableReference.get();
        int n = pooledByteBuffer.size();
        closeableReference = this.mFlexByteArrayPool.get(n);
        try {
            byte[] arrby = (byte[])closeableReference.get();
            pooledByteBuffer.read(0, arrby, 0, n);
            options = Preconditions.checkNotNull(BitmapFactory.decodeByteArray((byte[])arrby, (int)0, (int)n, (BitmapFactory.Options)options), "BitmapFactory returned null");
            return options;
        }
        finally {
            CloseableReference.closeSafely(closeableReference);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected Bitmap decodeJPEGByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> closeableReference, int n, BitmapFactory.Options options) {
        boolean bl = false;
        byte[] arrby = KitKatPurgeableDecoder.endsWithEOI(closeableReference, n) ? null : EOI;
        PooledByteBuffer pooledByteBuffer = closeableReference.get();
        if (n <= pooledByteBuffer.size()) {
            bl = true;
        }
        Preconditions.checkArgument(bl);
        closeableReference = this.mFlexByteArrayPool.get(n + 2);
        try {
            byte[] arrby2 = (byte[])closeableReference.get();
            pooledByteBuffer.read(0, arrby2, 0, n);
            int n2 = n;
            if (arrby != null) {
                KitKatPurgeableDecoder.putEOI(arrby2, n);
                n2 = n + 2;
            }
            options = Preconditions.checkNotNull(BitmapFactory.decodeByteArray((byte[])arrby2, (int)0, (int)n2, (BitmapFactory.Options)options), "BitmapFactory returned null");
            return options;
        }
        finally {
            CloseableReference.closeSafely(closeableReference);
        }
    }
}


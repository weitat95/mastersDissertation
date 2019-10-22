/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.facebook.imagepipeline.platform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imagepipeline.common.TooManyBitmapsException;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.BitmapCounter;
import com.facebook.imagepipeline.memory.BitmapCounterProvider;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.nativecode.Bitmaps;
import com.facebook.imagepipeline.platform.PlatformDecoder;
import java.io.Closeable;

abstract class DalvikPurgeableDecoder
implements PlatformDecoder {
    protected static final byte[] EOI = new byte[]{-1, -39};
    private final BitmapCounter mUnpooledBitmapsCounter = BitmapCounterProvider.get();

    DalvikPurgeableDecoder() {
    }

    protected static boolean endsWithEOI(CloseableReference<PooledByteBuffer> closeable, int n) {
        closeable = ((CloseableReference)closeable).get();
        return n >= 2 && closeable.read(n - 2) == -1 && closeable.read(n - 1) == -39;
    }

    private static BitmapFactory.Options getBitmapFactoryOptions(int n, Bitmap.Config config) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = true;
        options.inPreferredConfig = config;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = n;
        if (Build.VERSION.SDK_INT >= 11) {
            options.inMutable = true;
        }
        return options;
    }

    abstract Bitmap decodeByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> var1, BitmapFactory.Options var2);

    @Override
    public CloseableReference<Bitmap> decodeFromEncodedImage(EncodedImage closeable, Bitmap.Config object) {
        object = DalvikPurgeableDecoder.getBitmapFactoryOptions(closeable.getSampleSize(), object);
        closeable = closeable.getByteBufferRef();
        Preconditions.checkNotNull(closeable);
        try {
            object = this.pinBitmap(this.decodeByteArrayAsPurgeable((CloseableReference<PooledByteBuffer>)closeable, (BitmapFactory.Options)object));
            return object;
        }
        finally {
            CloseableReference.closeSafely(closeable);
        }
    }

    abstract Bitmap decodeJPEGByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> var1, int var2, BitmapFactory.Options var3);

    @Override
    public CloseableReference<Bitmap> decodeJPEGFromEncodedImage(EncodedImage closeable, Bitmap.Config object, int n) {
        object = DalvikPurgeableDecoder.getBitmapFactoryOptions(closeable.getSampleSize(), object);
        closeable = closeable.getByteBufferRef();
        Preconditions.checkNotNull(closeable);
        try {
            object = this.pinBitmap(this.decodeJPEGByteArrayAsPurgeable((CloseableReference<PooledByteBuffer>)closeable, n, (BitmapFactory.Options)object));
            return object;
        }
        finally {
            CloseableReference.closeSafely(closeable);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public CloseableReference<Bitmap> pinBitmap(Bitmap bitmap) {
        try {
            Bitmaps.pinBitmap(bitmap);
            if (this.mUnpooledBitmapsCounter.increase(bitmap)) return CloseableReference.of(bitmap, this.mUnpooledBitmapsCounter.getReleaser());
        }
        catch (Exception exception) {
            bitmap.recycle();
            throw Throwables.propagate(exception);
        }
        bitmap.recycle();
        throw new TooManyBitmapsException();
    }
}


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
import android.support.v4.util.Pools;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.streams.LimitedInputStream;
import com.facebook.common.streams.TailAppendingInputStream;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.BitmapPool;
import com.facebook.imagepipeline.platform.PlatformDecoder;
import com.facebook.imageutils.BitmapUtil;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(value=21)
@ThreadSafe
public class ArtDecoder
implements PlatformDecoder {
    private static final byte[] EOI_TAIL = new byte[]{-1, -39};
    private final BitmapPool mBitmapPool;
    final Pools.SynchronizedPool<ByteBuffer> mDecodeBuffers;

    public ArtDecoder(BitmapPool bitmapPool, int n, Pools.SynchronizedPool synchronizedPool) {
        this.mBitmapPool = bitmapPool;
        this.mDecodeBuffers = synchronizedPool;
        for (int i = 0; i < n; ++i) {
            this.mDecodeBuffers.release(ByteBuffer.allocate(16384));
        }
    }

    private static BitmapFactory.Options getDecodeOptionsForStream(EncodedImage encodedImage, Bitmap.Config config) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = encodedImage.getSampleSize();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream((InputStream)encodedImage.getInputStream(), null, (BitmapFactory.Options)options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            throw new IllegalArgumentException();
        }
        options.inJustDecodeBounds = false;
        options.inDither = true;
        options.inPreferredConfig = config;
        options.inMutable = true;
        return options;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CloseableReference<Bitmap> decodeFromEncodedImage(EncodedImage encodedImage, Bitmap.Config object) {
        BitmapFactory.Options options = ArtDecoder.getDecodeOptionsForStream(encodedImage, object);
        boolean bl = options.inPreferredConfig != Bitmap.Config.ARGB_8888;
        try {
            return this.decodeStaticImageFromStream(encodedImage.getInputStream(), options);
        }
        catch (RuntimeException runtimeException) {
            if (!bl) throw runtimeException;
            return this.decodeFromEncodedImage(encodedImage, Bitmap.Config.ARGB_8888);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CloseableReference<Bitmap> decodeJPEGFromEncodedImage(EncodedImage encodedImage, Bitmap.Config object, int n) {
        int n2;
        boolean bl = encodedImage.isCompleteAt(n2);
        BitmapFactory.Options options = ArtDecoder.getDecodeOptionsForStream(encodedImage, object);
        InputStream inputStream = encodedImage.getInputStream();
        Preconditions.checkNotNull(inputStream);
        if (encodedImage.getSize() > n2) {
            LimitedInputStream limitedInputStream = new LimitedInputStream(inputStream, n2);
        }
        if (!bl) {
            void var2_5;
            TailAppendingInputStream tailAppendingInputStream = new TailAppendingInputStream((InputStream)var2_5, EOI_TAIL);
        }
        n2 = options.inPreferredConfig != Bitmap.Config.ARGB_8888;
        try {
            void var2_7;
            return this.decodeStaticImageFromStream((InputStream)var2_7, options);
        }
        catch (RuntimeException runtimeException) {
            if (n2 == 0) throw runtimeException;
            return this.decodeFromEncodedImage(encodedImage, Bitmap.Config.ARGB_8888);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected CloseableReference<Bitmap> decodeStaticImageFromStream(InputStream inputStream, BitmapFactory.Options options) {
        ByteBuffer byteBuffer;
        Preconditions.checkNotNull(inputStream);
        int n = BitmapUtil.getSizeInByteForBitmap(options.outWidth, options.outHeight, options.inPreferredConfig);
        Bitmap bitmap = (Bitmap)this.mBitmapPool.get(n);
        if (bitmap == null) {
            throw new NullPointerException("BitmapPool.get returned null");
        }
        options.inBitmap = bitmap;
        ByteBuffer byteBuffer2 = byteBuffer = this.mDecodeBuffers.acquire();
        if (byteBuffer == null) {
            byteBuffer2 = ByteBuffer.allocate(16384);
        }
        try {
            options.inTempStorage = byteBuffer2.array();
            inputStream = BitmapFactory.decodeStream((InputStream)inputStream, null, (BitmapFactory.Options)options);
            if (bitmap == inputStream) return CloseableReference.of(inputStream, this.mBitmapPool);
        }
        catch (RuntimeException runtimeException) {
            this.mBitmapPool.release(bitmap);
            throw runtimeException;
        }
        finally {
            this.mDecodeBuffers.release(byteBuffer2);
        }
        this.mBitmapPool.release(bitmap);
        inputStream.recycle();
        throw new IllegalStateException();
    }
}


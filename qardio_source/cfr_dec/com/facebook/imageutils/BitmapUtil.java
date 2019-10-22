/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Pair
 *  javax.annotation.Nullable
 */
package com.facebook.imageutils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.util.Pools;
import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

public final class BitmapUtil {
    private static final Pools.SynchronizedPool<ByteBuffer> DECODE_BUFFERS = new Pools.SynchronizedPool(12);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public static Pair<Integer, Integer> decodeDimensions(InputStream inputStream) {
        ByteBuffer byteBuffer;
        ByteBuffer byteBuffer2;
        block7: {
            Object var3_2 = null;
            Preconditions.checkNotNull(inputStream);
            byteBuffer = byteBuffer2 = DECODE_BUFFERS.acquire();
            if (byteBuffer2 == null) {
                byteBuffer = ByteBuffer.allocate(16384);
            }
            byteBuffer2 = new BitmapFactory.Options();
            ((BitmapFactory.Options)byteBuffer2).inJustDecodeBounds = true;
            ((BitmapFactory.Options)byteBuffer2).inTempStorage = byteBuffer.array();
            BitmapFactory.decodeStream((InputStream)inputStream, null, (BitmapFactory.Options)byteBuffer2);
            inputStream = var3_2;
            if (((BitmapFactory.Options)byteBuffer2).outWidth == -1) return inputStream;
            int n = ((BitmapFactory.Options)byteBuffer2).outHeight;
            if (n != -1) break block7;
            return var3_2;
        }
        try {
            inputStream = new Pair((Object)((BitmapFactory.Options)byteBuffer2).outWidth, (Object)((BitmapFactory.Options)byteBuffer2).outHeight);
            return inputStream;
        }
        finally {
            DECODE_BUFFERS.release(byteBuffer);
        }
    }

    public static int getPixelSizeForBitmapConfig(Bitmap.Config config) {
        int n = 2;
        switch (1.$SwitchMap$android$graphics$Bitmap$Config[config.ordinal()]) {
            default: {
                throw new UnsupportedOperationException("The provided Bitmap.Config is not supported");
            }
            case 1: {
                n = 4;
            }
            case 3: 
            case 4: {
                return n;
            }
            case 2: 
        }
        return 1;
    }

    public static int getSizeInByteForBitmap(int n, int n2, Bitmap.Config config) {
        return n * n2 * BitmapUtil.getPixelSizeForBitmapConfig(config);
    }

    @SuppressLint(value={"NewApi"})
    public static int getSizeInBytes(@Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT > 19) {
            try {
                int n = bitmap.getAllocationByteCount();
                return n;
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
        }
        if (Build.VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount();
        }
        return bitmap.getWidth() * bitmap.getRowBytes();
    }

}


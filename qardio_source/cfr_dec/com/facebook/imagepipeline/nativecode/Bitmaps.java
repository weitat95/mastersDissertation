/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.facebook.imagepipeline.nativecode;

import android.graphics.Bitmap;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.nativecode.ImagePipelineNativeLoader;
import java.nio.ByteBuffer;

@DoNotStrip
public class Bitmaps {
    static {
        ImagePipelineNativeLoader.load();
    }

    @DoNotStrip
    private static native void nativeCopyBitmap(Bitmap var0, int var1, Bitmap var2, int var3, int var4);

    @DoNotStrip
    private static native ByteBuffer nativeGetByteBuffer(Bitmap var0, long var1, long var3);

    @DoNotStrip
    private static native void nativePinBitmap(Bitmap var0);

    @DoNotStrip
    private static native void nativeReleaseByteBuffer(Bitmap var0);

    public static void pinBitmap(Bitmap bitmap) {
        Preconditions.checkNotNull(bitmap);
        Bitmaps.nativePinBitmap(bitmap);
    }
}


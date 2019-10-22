/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.facebook.imagepipeline.nativecode;

import android.graphics.Bitmap;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.imagepipeline.nativecode.ImagePipelineNativeLoader;

@DoNotStrip
public class NativeBlurFilter {
    static {
        ImagePipelineNativeLoader.load();
    }

    @DoNotStrip
    private static native void nativeIterativeBoxBlur(Bitmap var0, int var1, int var2);
}


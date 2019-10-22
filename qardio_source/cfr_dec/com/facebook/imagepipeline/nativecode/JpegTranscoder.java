/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.nativecode;

import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.nativecode.ImagePipelineNativeLoader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@DoNotStrip
public class JpegTranscoder {
    static {
        ImagePipelineNativeLoader.load();
    }

    public static boolean isRotationAngleAllowed(int n) {
        return n >= 0 && n <= 270 && n % 90 == 0;
    }

    @DoNotStrip
    private static native void nativeTranscodeJpeg(InputStream var0, OutputStream var1, int var2, int var3, int var4) throws IOException;

    /*
     * Enabled aggressive block sorting
     */
    public static void transcodeJpeg(InputStream inputStream, OutputStream outputStream, int n, int n2, int n3) throws IOException {
        boolean bl;
        block3: {
            block2: {
                boolean bl2 = false;
                bl = n2 >= 1;
                Preconditions.checkArgument(bl);
                bl = n2 <= 16;
                Preconditions.checkArgument(bl);
                bl = n3 >= 0;
                Preconditions.checkArgument(bl);
                bl = n3 <= 100;
                Preconditions.checkArgument(bl);
                Preconditions.checkArgument(JpegTranscoder.isRotationAngleAllowed(n));
                if (n2 != 8) break block2;
                bl = bl2;
                if (n == 0) break block3;
            }
            bl = true;
        }
        Preconditions.checkArgument(bl, "no transformation requested");
        JpegTranscoder.nativeTranscodeJpeg(Preconditions.checkNotNull(inputStream), Preconditions.checkNotNull(outputStream), n, n2, n3);
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imageutils;

import com.facebook.common.internal.Preconditions;
import com.facebook.imageutils.StreamProcessor;
import com.facebook.imageutils.TiffUtil;
import java.io.IOException;
import java.io.InputStream;

public class JfifUtil {
    public static int getAutoRotateAngleFromOrientation(int n) {
        return TiffUtil.getAutoRotateAngleFromOrientation(n);
    }

    public static int getOrientation(InputStream inputStream) {
        int n;
        block3: {
            try {
                n = JfifUtil.moveToAPP1EXIF(inputStream);
                if (n != 0) break block3;
                return 0;
            }
            catch (IOException iOException) {
                return 0;
            }
        }
        n = TiffUtil.readOrientationFromTIFF(inputStream, n);
        return n;
    }

    private static boolean isSOFn(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 192: 
            case 193: 
            case 194: 
            case 195: 
            case 197: 
            case 198: 
            case 199: 
            case 201: 
            case 202: 
            case 203: 
            case 205: 
            case 206: 
            case 207: 
        }
        return true;
    }

    private static int moveToAPP1EXIF(InputStream inputStream) throws IOException {
        int n;
        if (JfifUtil.moveToMarker(inputStream, 225) && (n = StreamProcessor.readPackedInt(inputStream, 2, false) - 2) > 6) {
            int n2 = StreamProcessor.readPackedInt(inputStream, 4, false);
            int n3 = StreamProcessor.readPackedInt(inputStream, 2, false);
            if (n2 == 1165519206 && n3 == 0) {
                return n - 4 - 2;
            }
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean moveToMarker(InputStream inputStream, int n) throws IOException {
        Preconditions.checkNotNull(inputStream);
        while (StreamProcessor.readPackedInt(inputStream, 1, false) == 255) {
            int n2 = 255;
            while (n2 == 255) {
                n2 = StreamProcessor.readPackedInt(inputStream, 1, false);
            }
            if (n == 192 && JfifUtil.isSOFn(n2) || n2 == n) {
                return true;
            }
            if (n2 == 216 || n2 == 1) continue;
            if (n2 == 217 || n2 == 218) {
                return false;
            }
            inputStream.skip(StreamProcessor.readPackedInt(inputStream, 2, false) - 2);
        }
        return false;
    }
}


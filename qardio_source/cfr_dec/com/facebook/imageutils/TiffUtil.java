/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imageutils;

import com.facebook.common.logging.FLog;
import com.facebook.imageutils.StreamProcessor;
import java.io.IOException;
import java.io.InputStream;

class TiffUtil {
    private static final Class<?> TAG = TiffUtil.class;

    TiffUtil() {
    }

    public static int getAutoRotateAngleFromOrientation(int n) {
        switch (n) {
            default: {
                FLog.i(TAG, "Unsupported orientation");
            }
            case 0: 
            case 1: {
                return 0;
            }
            case 3: {
                return 180;
            }
            case 6: {
                return 90;
            }
            case 8: 
        }
        return 270;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int getOrientationFromTiffEntry(InputStream inputStream, int n, boolean bl) throws IOException {
        if (n < 10 || StreamProcessor.readPackedInt(inputStream, 2, bl) != 3 || StreamProcessor.readPackedInt(inputStream, 4, bl) != 1) {
            return 0;
        }
        n = StreamProcessor.readPackedInt(inputStream, 2, bl);
        StreamProcessor.readPackedInt(inputStream, 2, bl);
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int moveToTiffEntryWithTag(InputStream inputStream, int n, boolean bl, int n2) throws IOException {
        if (n >= 14) {
            int n3 = StreamProcessor.readPackedInt(inputStream, 2, bl);
            int n4 = n - 2;
            n = n3;
            for (n3 = n4; n > 0 && n3 >= 12; n3 -= 10, --n) {
                n4 = StreamProcessor.readPackedInt(inputStream, 2, bl);
                n3 -= 2;
                if (n4 == n2) {
                    return n3;
                }
                inputStream.skip(10L);
            }
        }
        return 0;
    }

    public static int readOrientationFromTIFF(InputStream inputStream, int n) throws IOException {
        TiffHeader tiffHeader = new TiffHeader();
        n = TiffUtil.readTiffHeader(inputStream, n, tiffHeader);
        int n2 = tiffHeader.firstIfdOffset - 8;
        if (n == 0 || n2 > n) {
            return 0;
        }
        inputStream.skip(n2);
        return TiffUtil.getOrientationFromTiffEntry(inputStream, TiffUtil.moveToTiffEntryWithTag(inputStream, n - n2, tiffHeader.isLittleEndian, 274), tiffHeader.isLittleEndian);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int readTiffHeader(InputStream inputStream, int n, TiffHeader tiffHeader) throws IOException {
        if (n <= 8) {
            return 0;
        }
        tiffHeader.byteOrder = StreamProcessor.readPackedInt(inputStream, 4, false);
        if (tiffHeader.byteOrder != 1229531648 && tiffHeader.byteOrder != 1296891946) {
            FLog.e(TAG, "Invalid TIFF header");
            return 0;
        }
        boolean bl = tiffHeader.byteOrder == 1229531648;
        tiffHeader.isLittleEndian = bl;
        tiffHeader.firstIfdOffset = StreamProcessor.readPackedInt(inputStream, 4, tiffHeader.isLittleEndian);
        n = n - 4 - 4;
        if (tiffHeader.firstIfdOffset >= 8 && tiffHeader.firstIfdOffset - 8 <= n) {
            return n;
        }
        FLog.e(TAG, "Invalid offset");
        return 0;
    }

    private static class TiffHeader {
        int byteOrder;
        int firstIfdOffset;
        boolean isLittleEndian;

        private TiffHeader() {
        }
    }

}


/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imageformat;

import com.facebook.common.internal.Preconditions;
import java.io.IOException;
import java.io.InputStream;

public final class GifFormatChecker {
    private static final byte[] FRAME_HEADER_END_1;
    private static final byte[] FRAME_HEADER_END_2;
    private static final byte[] FRAME_HEADER_START;

    static {
        FRAME_HEADER_START = new byte[]{0, 33, -7, 4};
        FRAME_HEADER_END_1 = new byte[]{0, 44};
        FRAME_HEADER_END_2 = new byte[]{0, 33};
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean circularBufferMatchesBytePattern(byte[] arrby, int n, byte[] arrby2) {
        Preconditions.checkNotNull(arrby);
        Preconditions.checkNotNull(arrby2);
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        if (arrby2.length <= arrby.length) {
            int n2 = 0;
            do {
                if (n2 >= arrby2.length) {
                    return true;
                }
                if (arrby[(n2 + n) % arrby.length] != arrby2[n2]) break;
                ++n2;
            } while (true);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isAnimated(InputStream inputStream) {
        byte[] arrby = new byte[10];
        try {
            inputStream.read(arrby, 0, 10);
            int n = 0;
            int n2 = 0;
            while (inputStream.read(arrby, n, 1) > 0) {
                int n3;
                block7: {
                    block8: {
                        n3 = n2;
                        if (!GifFormatChecker.circularBufferMatchesBytePattern(arrby, n + 1, FRAME_HEADER_START)) break block7;
                        if (GifFormatChecker.circularBufferMatchesBytePattern(arrby, n + 9, FRAME_HEADER_END_1)) break block8;
                        n3 = n2;
                        if (!GifFormatChecker.circularBufferMatchesBytePattern(arrby, n + 9, FRAME_HEADER_END_2)) break block7;
                    }
                    n3 = ++n2;
                    if (n2 > 1) {
                        return true;
                    }
                }
                n = (n + 1) % arrby.length;
                n2 = n3;
            }
            return false;
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }
}


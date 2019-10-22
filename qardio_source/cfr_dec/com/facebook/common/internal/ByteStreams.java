/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.internal;

import com.facebook.common.internal.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ByteStreams {
    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(outputStream);
        byte[] arrby = new byte[4096];
        long l = 0L;
        int n;
        while ((n = inputStream.read(arrby)) != -1) {
            outputStream.write(arrby, 0, n);
            l += (long)n;
        }
        return l;
    }

    public static int read(InputStream inputStream, byte[] arrby, int n, int n2) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(arrby);
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        int n3 = 0;
        int n4;
        while (n3 < n2 && (n4 = inputStream.read(arrby, n + n3, n2 - n3)) != -1) {
            n3 += n4;
        }
        return n3;
    }
}


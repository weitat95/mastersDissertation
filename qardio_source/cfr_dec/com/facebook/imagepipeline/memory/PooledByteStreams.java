/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.memory.ByteArrayPool;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PooledByteStreams {
    private final ByteArrayPool mByteArrayPool;
    private final int mTempBufSize;

    public PooledByteStreams(ByteArrayPool byteArrayPool) {
        this(byteArrayPool, 16384);
    }

    /*
     * Enabled aggressive block sorting
     */
    PooledByteStreams(ByteArrayPool byteArrayPool, int n) {
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        this.mTempBufSize = n;
        this.mByteArrayPool = byteArrayPool;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        long l = 0L;
        byte[] arrby = (byte[])this.mByteArrayPool.get(this.mTempBufSize);
        try {
            do {
                int n;
                if ((n = inputStream.read(arrby, 0, this.mTempBufSize)) == -1) {
                    return l;
                }
                outputStream.write(arrby, 0, n);
                l += (long)n;
            } while (true);
        }
        finally {
            this.mByteArrayPool.release(arrby);
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.NotThreadSafe
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import java.io.InputStream;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class PooledByteBufferInputStream
extends InputStream {
    int mMark;
    int mOffset;
    final PooledByteBuffer mPooledByteBuffer;

    /*
     * Enabled aggressive block sorting
     */
    public PooledByteBufferInputStream(PooledByteBuffer pooledByteBuffer) {
        boolean bl = !pooledByteBuffer.isClosed();
        Preconditions.checkArgument(bl);
        this.mPooledByteBuffer = Preconditions.checkNotNull(pooledByteBuffer);
        this.mOffset = 0;
        this.mMark = 0;
    }

    @Override
    public int available() {
        return this.mPooledByteBuffer.size() - this.mOffset;
    }

    @Override
    public void mark(int n) {
        this.mMark = this.mOffset;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public int read() {
        if (this.available() <= 0) {
            return -1;
        }
        PooledByteBuffer pooledByteBuffer = this.mPooledByteBuffer;
        int n = this.mOffset;
        this.mOffset = n + 1;
        return pooledByteBuffer.read(n) & 0xFF;
    }

    @Override
    public int read(byte[] arrby) {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) {
        if (n < 0 || n2 < 0 || n + n2 > arrby.length) {
            throw new ArrayIndexOutOfBoundsException("length=" + arrby.length + "; regionStart=" + n + "; regionLength=" + n2);
        }
        int n3 = this.available();
        if (n3 <= 0) {
            return -1;
        }
        if (n2 <= 0) {
            return 0;
        }
        n2 = Math.min(n3, n2);
        this.mPooledByteBuffer.read(this.mOffset, arrby, n, n2);
        this.mOffset += n2;
        return n2;
    }

    @Override
    public void reset() {
        this.mOffset = this.mMark;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long skip(long l) {
        boolean bl = l >= 0L;
        Preconditions.checkArgument(bl);
        int n = Math.min((int)l, this.available());
        this.mOffset += n;
        return n;
    }
}


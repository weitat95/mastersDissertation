/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.NotThreadSafe
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.ResourceReleaser;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class PooledByteArrayBufferedInputStream
extends InputStream {
    private int mBufferOffset;
    private int mBufferedSize;
    private final byte[] mByteArray;
    private boolean mClosed;
    private final InputStream mInputStream;
    private final ResourceReleaser<byte[]> mResourceReleaser;

    public PooledByteArrayBufferedInputStream(InputStream inputStream, byte[] arrby, ResourceReleaser<byte[]> resourceReleaser) {
        this.mInputStream = Preconditions.checkNotNull(inputStream);
        this.mByteArray = Preconditions.checkNotNull(arrby);
        this.mResourceReleaser = Preconditions.checkNotNull(resourceReleaser);
        this.mBufferedSize = 0;
        this.mBufferOffset = 0;
        this.mClosed = false;
    }

    private boolean ensureDataInBuffer() throws IOException {
        if (this.mBufferOffset < this.mBufferedSize) {
            return true;
        }
        int n = this.mInputStream.read(this.mByteArray);
        if (n <= 0) {
            return false;
        }
        this.mBufferedSize = n;
        this.mBufferOffset = 0;
        return true;
    }

    private void ensureNotClosed() throws IOException {
        if (this.mClosed) {
            throw new IOException("stream already closed");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int available() throws IOException {
        boolean bl = this.mBufferOffset <= this.mBufferedSize;
        Preconditions.checkState(bl);
        this.ensureNotClosed();
        return this.mBufferedSize - this.mBufferOffset + this.mInputStream.available();
    }

    @Override
    public void close() throws IOException {
        if (!this.mClosed) {
            this.mClosed = true;
            this.mResourceReleaser.release(this.mByteArray);
            super.close();
        }
    }

    protected void finalize() throws Throwable {
        if (!this.mClosed) {
            FLog.e("PooledByteInputStream", "Finalized without closing");
            this.close();
        }
        super.finalize();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int read() throws IOException {
        boolean bl = this.mBufferOffset <= this.mBufferedSize;
        Preconditions.checkState(bl);
        this.ensureNotClosed();
        if (!this.ensureDataInBuffer()) {
            return -1;
        }
        byte[] arrby = this.mByteArray;
        int n = this.mBufferOffset;
        this.mBufferOffset = n + 1;
        return arrby[n] & 0xFF;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        boolean bl = this.mBufferOffset <= this.mBufferedSize;
        Preconditions.checkState(bl);
        this.ensureNotClosed();
        if (!this.ensureDataInBuffer()) {
            return -1;
        }
        n2 = Math.min(this.mBufferedSize - this.mBufferOffset, n2);
        System.arraycopy(this.mByteArray, this.mBufferOffset, arrby, n, n2);
        this.mBufferOffset += n2;
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long skip(long l) throws IOException {
        boolean bl = this.mBufferOffset <= this.mBufferedSize;
        Preconditions.checkState(bl);
        this.ensureNotClosed();
        int n = this.mBufferedSize - this.mBufferOffset;
        if ((long)n >= l) {
            this.mBufferOffset = (int)((long)this.mBufferOffset + l);
            return l;
        }
        this.mBufferOffset = this.mBufferedSize;
        return (long)n + this.mInputStream.skip(l - (long)n);
    }
}


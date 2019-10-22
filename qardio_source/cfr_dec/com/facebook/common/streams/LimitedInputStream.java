/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LimitedInputStream
extends FilterInputStream {
    private int mBytesToRead;
    private int mBytesToReadWhenMarked;

    public LimitedInputStream(InputStream inputStream, int n) {
        super(inputStream);
        if (inputStream == null) {
            throw new NullPointerException();
        }
        if (n < 0) {
            throw new IllegalArgumentException("limit must be >= 0");
        }
        this.mBytesToRead = n;
        this.mBytesToReadWhenMarked = -1;
    }

    @Override
    public int available() throws IOException {
        return Math.min(this.in.available(), this.mBytesToRead);
    }

    @Override
    public void mark(int n) {
        if (this.in.markSupported()) {
            this.in.mark(n);
            this.mBytesToReadWhenMarked = this.mBytesToRead;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int read() throws IOException {
        int n;
        if (this.mBytesToRead == 0) {
            return -1;
        }
        int n2 = n = this.in.read();
        if (n == -1) return n2;
        --this.mBytesToRead;
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (this.mBytesToRead == 0) {
            return -1;
        }
        n2 = Math.min(n2, this.mBytesToRead);
        n = n2 = this.in.read(arrby, n, n2);
        if (n2 <= 0) return n;
        this.mBytesToRead -= n2;
        return n2;
    }

    @Override
    public void reset() throws IOException {
        if (!this.in.markSupported()) {
            throw new IOException("mark is not supported");
        }
        if (this.mBytesToReadWhenMarked == -1) {
            throw new IOException("mark not set");
        }
        this.in.reset();
        this.mBytesToRead = this.mBytesToReadWhenMarked;
    }

    @Override
    public long skip(long l) throws IOException {
        l = Math.min(l, (long)this.mBytesToRead);
        l = this.in.skip(l);
        this.mBytesToRead = (int)((long)this.mBytesToRead - l);
        return l;
    }
}


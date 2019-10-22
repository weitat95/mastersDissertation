/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MarkEnforcingInputStream
extends FilterInputStream {
    private int availableBytes = Integer.MIN_VALUE;

    public MarkEnforcingInputStream(InputStream inputStream) {
        super(inputStream);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private long getBytesToRead(long l) {
        if (this.availableBytes == 0) {
            return -1L;
        }
        long l2 = l;
        if (this.availableBytes == Integer.MIN_VALUE) return l2;
        l2 = l;
        if (l <= (long)this.availableBytes) return l2;
        return this.availableBytes;
    }

    private void updateAvailableBytesAfterRead(long l) {
        if (this.availableBytes != Integer.MIN_VALUE && l != -1L) {
            this.availableBytes = (int)((long)this.availableBytes - l);
        }
    }

    @Override
    public int available() throws IOException {
        if (this.availableBytes == Integer.MIN_VALUE) {
            return super.available();
        }
        return Math.min(this.availableBytes, super.available());
    }

    @Override
    public void mark(int n) {
        super.mark(n);
        this.availableBytes = n;
    }

    @Override
    public int read() throws IOException {
        if (this.getBytesToRead(1L) == -1L) {
            return -1;
        }
        int n = super.read();
        this.updateAvailableBytesAfterRead(1L);
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n2 = (int)this.getBytesToRead(n2)) == -1) {
            return -1;
        }
        n = super.read(arrby, n, n2);
        this.updateAvailableBytesAfterRead(n);
        return n;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        this.availableBytes = Integer.MIN_VALUE;
    }

    @Override
    public long skip(long l) throws IOException {
        if ((l = this.getBytesToRead(l)) == -1L) {
            return -1L;
        }
        l = super.skip(l);
        this.updateAvailableBytesAfterRead(l);
        return l;
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ContentLengthInputStream
extends FilterInputStream {
    private final long contentLength;
    private int readSoFar;

    ContentLengthInputStream(InputStream inputStream, long l) {
        super(inputStream);
        this.contentLength = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int checkReadSoFarOrThrow(int n) throws IOException {
        if (n >= 0) {
            this.readSoFar += n;
            return n;
        } else {
            if (this.contentLength - (long)this.readSoFar <= 0L) return n;
            {
                throw new IOException("Failed to read all expected data, expected: " + this.contentLength + ", but read: " + this.readSoFar);
            }
        }
    }

    public static InputStream obtain(InputStream inputStream, long l) {
        return new ContentLengthInputStream(inputStream, l);
    }

    @Override
    public int available() throws IOException {
        synchronized (this) {
            long l = Math.max(this.contentLength - (long)this.readSoFar, (long)this.in.available());
            int n = (int)l;
            return n;
        }
    }

    @Override
    public int read() throws IOException {
        synchronized (this) {
            int n = this.checkReadSoFarOrThrow(super.read());
            return n;
        }
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            n = this.checkReadSoFarOrThrow(super.read(arrby, n, n2));
            return n;
        }
    }
}


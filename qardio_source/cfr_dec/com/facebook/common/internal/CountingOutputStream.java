/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.internal;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream
extends FilterOutputStream {
    private long mCount = 0L;

    public CountingOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }

    public long getCount() {
        return this.mCount;
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
        ++this.mCount;
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.out.write(arrby, n, n2);
        this.mCount += (long)n2;
    }
}


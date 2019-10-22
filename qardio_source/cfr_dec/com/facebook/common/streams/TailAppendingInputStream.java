/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TailAppendingInputStream
extends FilterInputStream {
    private int mMarkedTailOffset;
    private final byte[] mTail;
    private int mTailOffset;

    public TailAppendingInputStream(InputStream inputStream, byte[] arrby) {
        super(inputStream);
        if (inputStream == null) {
            throw new NullPointerException();
        }
        if (arrby == null) {
            throw new NullPointerException();
        }
        this.mTail = arrby;
    }

    private int readNextTailByte() {
        if (this.mTailOffset >= this.mTail.length) {
            return -1;
        }
        byte[] arrby = this.mTail;
        int n = this.mTailOffset;
        this.mTailOffset = n + 1;
        return arrby[n] & 0xFF;
    }

    @Override
    public void mark(int n) {
        if (this.in.markSupported()) {
            super.mark(n);
            this.mMarkedTailOffset = this.mTailOffset;
        }
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (n != -1) {
            return n;
        }
        return this.readNextTailByte();
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3 = this.in.read(arrby, n, n2);
        if (n3 != -1) {
            return n3;
        }
        if (n2 == 0) {
            return 0;
        }
        n3 = 0;
        do {
            int n4;
            if (n3 >= n2 || (n4 = this.readNextTailByte()) == -1) {
                if (n3 <= 0) return -1;
                return n3;
            }
            arrby[n + n3] = (byte)n4;
            ++n3;
        } while (true);
    }

    @Override
    public void reset() throws IOException {
        if (this.in.markSupported()) {
            this.in.reset();
            this.mTailOffset = this.mMarkedTailOffset;
            return;
        }
        throw new IOException("mark is not supported");
    }
}


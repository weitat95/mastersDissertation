/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.disklrucache;

import com.bumptech.glide.disklrucache.Util;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

class StrictLineReader
implements Closeable {
    private byte[] buf;
    private final Charset charset;
    private int end;
    private final InputStream in;
    private int pos;

    public StrictLineReader(InputStream inputStream, int n, Charset charset) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        }
        if (n < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        }
        if (!charset.equals(Util.US_ASCII)) {
            throw new IllegalArgumentException("Unsupported encoding");
        }
        this.in = inputStream;
        this.charset = charset;
        this.buf = new byte[n];
    }

    public StrictLineReader(InputStream inputStream, Charset charset) {
        this(inputStream, 8192, charset);
    }

    private void fillBuf() throws IOException {
        int n = this.in.read(this.buf, 0, this.buf.length);
        if (n == -1) {
            throw new EOFException();
        }
        this.pos = 0;
        this.end = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        InputStream inputStream = this.in;
        synchronized (inputStream) {
            if (this.buf != null) {
                this.buf = null;
                this.in.close();
            }
            return;
        }
    }

    public boolean hasUnterminatedLine() {
        return this.end == -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String readLine() throws IOException {
        InputStream inputStream = this.in;
        synchronized (inputStream) {
            if (this.buf == null) {
                throw new IOException("LineReader is closed");
            }
            if (this.pos >= this.end) {
                this.fillBuf();
            }
            int n = this.pos;
            do {
                if (n == this.end) break;
                if (this.buf[n] == 10) {
                    int n2 = n != this.pos && this.buf[n - 1] == 13 ? n - 1 : n;
                    String string2 = new String(this.buf, this.pos, n2 - this.pos, this.charset.name());
                    this.pos = n + 1;
                    return string2;
                }
                ++n;
            } while (true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(this.end - this.pos + 80){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public String toString() {
                    int n = this.count > 0 && this.buf[this.count - 1] == 13 ? this.count - 1 : this.count;
                    try {
                        return new String(this.buf, 0, n, StrictLineReader.this.charset.name());
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        throw new AssertionError(unsupportedEncodingException);
                    }
                }
            };
            block5: do {
                byteArrayOutputStream.write(this.buf, this.pos, this.end - this.pos);
                this.end = -1;
                this.fillBuf();
                n = this.pos;
                do {
                    if (n == this.end) continue block5;
                    if (this.buf[n] == 10) {
                        if (n != this.pos) {
                            byteArrayOutputStream.write(this.buf, this.pos, n - this.pos);
                        }
                        this.pos = n + 1;
                        return byteArrayOutputStream.toString();
                    }
                    ++n;
                } while (true);
                break;
            } while (true);
        }
    }

}


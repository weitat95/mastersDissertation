/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.bumptech.glide.load.resource.bitmap;

import android.util.Log;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RecyclableBufferedInputStream
extends FilterInputStream {
    private volatile byte[] buf;
    private int count;
    private int marklimit;
    private int markpos = -1;
    private int pos;

    public RecyclableBufferedInputStream(InputStream inputStream, byte[] arrby) {
        super(inputStream);
        if (arrby == null || arrby.length == 0) {
            throw new IllegalArgumentException("buffer is null or empty");
        }
        this.buf = arrby;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int fillbuf(InputStream inputStream, byte[] arrby) throws IOException {
        int n;
        int n2;
        byte[] arrby2;
        if (this.markpos == -1 || this.pos - this.markpos >= this.marklimit) {
            int n3 = inputStream.read(arrby);
            if (n3 > 0) {
                this.markpos = -1;
                this.pos = 0;
                this.count = n3;
            }
            return n3;
        }
        if (this.markpos == 0 && this.marklimit > arrby.length && this.count == arrby.length) {
            n = n2 = arrby.length * 2;
            if (n2 > this.marklimit) {
                n = this.marklimit;
            }
            if (Log.isLoggable((String)"BufferedIs", (int)3)) {
                Log.d((String)"BufferedIs", (String)("allocate buffer of length: " + n));
            }
            arrby2 = new byte[n];
            System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
            this.buf = arrby2;
        } else {
            arrby2 = arrby;
            if (this.markpos > 0) {
                System.arraycopy(arrby, this.markpos, arrby, 0, arrby.length - this.markpos);
                arrby2 = arrby;
            }
        }
        this.pos -= this.markpos;
        this.markpos = 0;
        this.count = 0;
        n2 = inputStream.read(arrby2, this.pos, arrby2.length - this.pos);
        n = n2 <= 0 ? this.pos : this.pos + n2;
        this.count = n;
        return n2;
    }

    private static IOException streamClosed() throws IOException {
        throw new IOException("BufferedInputStream is closed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int available() throws IOException {
        synchronized (this) {
            InputStream inputStream = this.in;
            if (this.buf != null && inputStream != null) {
                int n = this.count;
                int n2 = this.pos;
                int n3 = inputStream.available();
                return n - n2 + n3;
            }
            throw RecyclableBufferedInputStream.streamClosed();
        }
    }

    @Override
    public void close() throws IOException {
        this.buf = null;
        InputStream inputStream = this.in;
        this.in = null;
        if (inputStream != null) {
            inputStream.close();
        }
    }

    public void fixMarkLimit() {
        synchronized (this) {
            this.marklimit = this.buf.length;
            return;
        }
    }

    @Override
    public void mark(int n) {
        synchronized (this) {
            this.marklimit = Math.max(this.marklimit, n);
            this.markpos = this.pos;
            return;
        }
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read() throws IOException {
        int n = -1;
        synchronized (this) {
            int n2;
            byte[] arrby = this.buf;
            byte[] arrby2 = this.in;
            if (arrby == null || arrby2 == null) {
                throw RecyclableBufferedInputStream.streamClosed();
            }
            if (this.pos < this.count || (n2 = this.fillbuf((InputStream)arrby2, arrby)) != -1) {
                arrby2 = arrby;
                if (arrby != this.buf) {
                    arrby2 = arrby = this.buf;
                    if (arrby == null) {
                        throw RecyclableBufferedInputStream.streamClosed();
                    }
                }
                if (this.count - this.pos > 0) {
                    n = this.pos;
                    this.pos = n + 1;
                    n = arrby2[n];
                    n &= 0xFF;
                }
            }
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3 = -1;
        synchronized (this) {
            int n4;
            byte[] arrby2 = this.buf;
            if (arrby2 == null) {
                throw RecyclableBufferedInputStream.streamClosed();
            }
            if (n2 == 0) {
                return 0;
            }
            InputStream inputStream = this.in;
            if (inputStream == null) {
                throw RecyclableBufferedInputStream.streamClosed();
            }
            if (this.pos < this.count) {
                n4 = this.count - this.pos >= n2 ? n2 : this.count - this.pos;
                System.arraycopy(arrby2, this.pos, arrby, n, n4);
                this.pos += n4;
                if (n4 == n2) return n4;
                if (inputStream.available() == 0) {
                    return n4;
                }
                n += n4;
                n4 = n2 - n4;
            } else {
                n4 = n2;
            }
            do {
                byte[] arrby3;
                int n5;
                int n6;
                if (this.markpos == -1 && n4 >= arrby2.length) {
                    n5 = inputStream.read(arrby, n, n4);
                    arrby3 = arrby2;
                    n6 = n5;
                    if (n5 == -1) {
                        n = n3;
                        if (n4 == n2) return n;
                        return n2 - n4;
                    }
                } else {
                    if (this.fillbuf(inputStream, arrby2) == -1) {
                        n = n3;
                        if (n4 == n2) return n;
                        return n2 - n4;
                    }
                    arrby3 = arrby2;
                    if (arrby2 != this.buf) {
                        arrby3 = arrby2 = this.buf;
                        if (arrby2 == null) {
                            throw RecyclableBufferedInputStream.streamClosed();
                        }
                    }
                    n6 = this.count - this.pos >= n4 ? n4 : this.count - this.pos;
                    System.arraycopy(arrby3, this.pos, arrby, n, n6);
                    this.pos += n6;
                }
                if ((n4 -= n6) == 0) {
                    return n2;
                }
                n5 = inputStream.available();
                if (n5 == 0) {
                    return n2 - n4;
                }
                n += n6;
                arrby2 = arrby3;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void reset() throws IOException {
        synchronized (this) {
            if (this.buf == null) {
                throw new IOException("Stream is closed");
            }
            if (-1 == this.markpos) {
                throw new InvalidMarkException("Mark has been invalidated");
            }
            this.pos = this.markpos;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public long skip(long l) throws IOException {
        // MONITORENTER : this
        byte[] arrby = this.buf;
        InputStream inputStream = this.in;
        if (arrby == null) {
            throw RecyclableBufferedInputStream.streamClosed();
        }
        if (l < 1L) {
            l = 0L;
            // MONITOREXIT : this
            return l;
        }
        if (inputStream == null) {
            throw RecyclableBufferedInputStream.streamClosed();
        }
        if ((long)(this.count - this.pos) >= l) {
            this.pos = (int)((long)this.pos + l);
            return l;
        }
        long l2 = this.count - this.pos;
        this.pos = this.count;
        if (this.markpos != -1 && l <= (long)this.marklimit) {
            if (this.fillbuf(inputStream, arrby) == -1) {
                return l2;
            }
            if ((long)(this.count - this.pos) >= l - l2) {
                this.pos = (int)((long)this.pos + (l - l2));
                return l;
            }
            l = this.count;
            long l3 = this.pos;
            this.pos = this.count;
            return l + l2 - l3;
        }
        l = inputStream.skip(l - l2);
        return l2 + l;
    }

    public static class InvalidMarkException
    extends RuntimeException {
        public InvalidMarkException(String string2) {
            super(string2);
        }
    }

}


/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio.Util;

final class RealBufferedSource
implements BufferedSource {
    public final Buffer buffer = new Buffer();
    boolean closed;
    public final Source source;

    RealBufferedSource(Source source) {
        if (source == null) {
            throw new NullPointerException("source == null");
        }
        this.source = source;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.source.close();
        this.buffer.clear();
    }

    @Override
    public boolean exhausted() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        return this.buffer.exhausted() && this.source.read(this.buffer, 8192L) == -1L;
    }

    @Override
    public long indexOf(byte by) throws IOException {
        return this.indexOf(by, 0L);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public long indexOf(byte var1_1, long var2_2) throws IOException {
        if (!this.closed) ** GOTO lbl4
        throw new IllegalStateException("closed");
lbl-1000:
        // 1 sources
        {
            var2_2 = Math.max(var2_2, var4_3);
lbl4:
            // 2 sources
            if ((var4_3 = this.buffer.indexOf(var1_1, var2_2)) != -1L) {
                return var4_3;
            }
            var4_3 = this.buffer.size;
            ** while (this.source.read((Buffer)this.buffer, (long)8192L) != -1L)
        }
lbl8:
        // 1 sources
        return -1L;
    }

    @Override
    public InputStream inputStream() {
        return new InputStream(){

            @Override
            public int available() throws IOException {
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                return (int)Math.min(RealBufferedSource.this.buffer.size, Integer.MAX_VALUE);
            }

            @Override
            public void close() throws IOException {
                RealBufferedSource.this.close();
            }

            @Override
            public int read() throws IOException {
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                if (RealBufferedSource.this.buffer.size == 0L && RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 8192L) == -1L) {
                    return -1;
                }
                return RealBufferedSource.this.buffer.readByte() & 0xFF;
            }

            @Override
            public int read(byte[] arrby, int n, int n2) throws IOException {
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                Util.checkOffsetAndCount(arrby.length, n, n2);
                if (RealBufferedSource.this.buffer.size == 0L && RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 8192L) == -1L) {
                    return -1;
                }
                return RealBufferedSource.this.buffer.read(arrby, n, n2);
            }

            public String toString() {
                return RealBufferedSource.this + ".inputStream()";
            }
        };
    }

    @Override
    public boolean rangeEquals(long l, ByteString byteString) throws IOException {
        return this.rangeEquals(l, byteString, 0, byteString.size());
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean rangeEquals(long l, ByteString byteString, int n, int n2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (l >= 0L && n >= 0 && n2 >= 0 && byteString.size() - n >= n2) {
            int n3 = 0;
            do {
                if (n3 >= n2) {
                    return true;
                }
                long l2 = l + (long)n3;
                if (!this.request(1L + l2) || this.buffer.getByte(l2) != byteString.getByte(n + n3)) break;
                ++n3;
            } while (true);
        }
        return false;
    }

    @Override
    public long read(Buffer buffer, long l) throws IOException {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (l < 0L) {
            throw new IllegalArgumentException("byteCount < 0: " + l);
        }
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.buffer.size == 0L && this.source.read(this.buffer, 8192L) == -1L) {
            return -1L;
        }
        l = Math.min(l, this.buffer.size);
        return this.buffer.read(buffer, l);
    }

    @Override
    public long readAll(Sink sink) throws IOException {
        long l;
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        long l2 = 0L;
        while (this.source.read(this.buffer, 8192L) != -1L) {
            l = this.buffer.completeSegmentByteCount();
            if (l <= 0L) continue;
            l2 += l;
            sink.write(this.buffer, l);
        }
        l = l2;
        if (this.buffer.size() > 0L) {
            l = l2 + this.buffer.size();
            sink.write(this.buffer, this.buffer.size());
        }
        return l;
    }

    @Override
    public byte readByte() throws IOException {
        this.require(1L);
        return this.buffer.readByte();
    }

    @Override
    public byte[] readByteArray(long l) throws IOException {
        this.require(l);
        return this.buffer.readByteArray(l);
    }

    @Override
    public ByteString readByteString(long l) throws IOException {
        this.require(l);
        return this.buffer.readByteString(l);
    }

    @Override
    public long readDecimalLong() throws IOException {
        this.require(1L);
        int n = 0;
        while (this.request(n + 1)) {
            byte by = this.buffer.getByte(n);
            if (!(by >= 48 && by <= 57 || n == 0 && by == 45)) {
                if (n != 0) break;
                throw new NumberFormatException(String.format("Expected leading [0-9] or '-' character but was %#x", by));
            }
            ++n;
        }
        return this.buffer.readDecimalLong();
    }

    @Override
    public long readHexadecimalUnsignedLong() throws IOException {
        this.require(1L);
        int n = 0;
        while (this.request(n + 1)) {
            byte by = this.buffer.getByte(n);
            if (!(by >= 48 && by <= 57 || by >= 97 && by <= 102 || by >= 65 && by <= 70)) {
                if (n != 0) break;
                throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", by));
            }
            ++n;
        }
        return this.buffer.readHexadecimalUnsignedLong();
    }

    @Override
    public int readInt() throws IOException {
        this.require(4L);
        return this.buffer.readInt();
    }

    @Override
    public int readIntLe() throws IOException {
        this.require(4L);
        return this.buffer.readIntLe();
    }

    @Override
    public short readShort() throws IOException {
        this.require(2L);
        return this.buffer.readShort();
    }

    @Override
    public short readShortLe() throws IOException {
        this.require(2L);
        return this.buffer.readShortLe();
    }

    @Override
    public String readString(Charset charset) throws IOException {
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        this.buffer.writeAll(this.source);
        return this.buffer.readString(charset);
    }

    @Override
    public String readUtf8LineStrict() throws IOException {
        long l = this.indexOf((byte)10);
        if (l == -1L) {
            Buffer buffer = new Buffer();
            this.buffer.copyTo(buffer, 0L, Math.min(32L, this.buffer.size()));
            throw new EOFException("\\n not found: size=" + this.buffer.size() + " content=" + buffer.readByteString().hex() + "\u2026");
        }
        return this.buffer.readUtf8Line(l);
    }

    @Override
    public boolean request(long l) throws IOException {
        if (l < 0L) {
            throw new IllegalArgumentException("byteCount < 0: " + l);
        }
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        while (this.buffer.size < l) {
            if (this.source.read(this.buffer, 8192L) != -1L) continue;
            return false;
        }
        return true;
    }

    @Override
    public void require(long l) throws IOException {
        if (!this.request(l)) {
            throw new EOFException();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void skip(long var1_1) throws IOException {
        if (!this.closed) ** GOTO lbl6
        throw new IllegalStateException("closed");
lbl-1000:
        // 1 sources
        {
            var3_2 = Math.min(var1_1, this.buffer.size());
            this.buffer.skip(var3_2);
            var1_1 -= var3_2;
lbl6:
            // 2 sources
            if (var1_1 <= 0L) return;
            ** while (this.buffer.size != 0L || this.source.read((Buffer)this.buffer, (long)8192L) != -1L)
        }
lbl8:
        // 1 sources
        throw new EOFException();
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }

    public String toString() {
        return "buffer(" + this.source + ")";
    }

}


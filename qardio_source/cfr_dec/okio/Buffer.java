/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Segment;
import okio.SegmentPool;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio.Util;

public final class Buffer
implements Cloneable,
BufferedSink,
BufferedSource {
    private static final byte[] DIGITS = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    Segment head;
    long size;

    @Override
    public Buffer buffer() {
        return this;
    }

    public void clear() {
        try {
            this.skip(this.size);
            return;
        }
        catch (EOFException eOFException) {
            throw new AssertionError(eOFException);
        }
    }

    public Buffer clone() {
        Segment segment;
        Buffer buffer = new Buffer();
        if (this.size == 0L) {
            return buffer;
        }
        Segment segment2 = buffer.head = new Segment(this.head);
        Segment segment3 = buffer.head;
        segment3.prev = segment = buffer.head;
        segment2.next = segment;
        segment2 = this.head.next;
        while (segment2 != this.head) {
            buffer.head.prev.push(new Segment(segment2));
            segment2 = segment2.next;
        }
        buffer.size = this.size;
        return buffer;
    }

    @Override
    public void close() {
    }

    public long completeSegmentByteCount() {
        long l = this.size;
        if (l == 0L) {
            return 0L;
        }
        Segment segment = this.head.prev;
        long l2 = l;
        if (segment.limit < 8192) {
            l2 = l;
            if (segment.owner) {
                l2 = l - (long)(segment.limit - segment.pos);
            }
        }
        return l2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Buffer copyTo(Buffer buffer, long l, long l2) {
        if (buffer == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, l, l2);
        if (l2 != 0L) {
            Segment segment;
            long l3;
            buffer.size += l2;
            Segment segment2 = this.head;
            do {
                segment = segment2;
                l3 = l;
                if (l < (long)(segment2.limit - segment2.pos)) break;
                l -= (long)(segment2.limit - segment2.pos);
                segment2 = segment2.next;
            } while (true);
            for (long i = l2; i > 0L; i -= (long)(segment2.limit - segment2.pos)) {
                segment2 = new Segment(segment);
                segment2.pos = (int)((long)segment2.pos + l3);
                segment2.limit = Math.min(segment2.pos + (int)i, segment2.limit);
                if (buffer.head == null) {
                    segment2.prev = segment2;
                    segment2.next = segment2;
                    buffer.head = segment2;
                } else {
                    buffer.head.prev.push(segment2);
                }
                l3 = 0L;
                segment = segment.next;
            }
        }
        return this;
    }

    @Override
    public Buffer emitCompleteSegments() {
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Buffer)) {
            return false;
        }
        object = (Buffer)object;
        if (this.size != ((Buffer)object).size) {
            return false;
        }
        if (this.size == 0L) {
            return true;
        }
        Segment segment = this.head;
        object = ((Buffer)object).head;
        int n = segment.pos;
        int n2 = ((Segment)object).pos;
        long l = 0L;
        while (l < this.size) {
            long l2 = Math.min(segment.limit - n, ((Segment)object).limit - n2);
            int n3 = 0;
            int n4 = n;
            n = n2;
            n2 = n4;
            n4 = n3;
            while ((long)n4 < l2) {
                if (segment.data[n2] != ((Segment)object).data[n]) {
                    return false;
                }
                ++n4;
                ++n;
                ++n2;
            }
            if (n2 == segment.limit) {
                segment = segment.next;
                n2 = segment.pos;
            }
            if (n == ((Segment)object).limit) {
                object = ((Segment)object).next;
                n4 = ((Segment)object).pos;
            } else {
                n4 = n;
            }
            l += l2;
            n = n2;
            n2 = n4;
        }
        return true;
    }

    @Override
    public boolean exhausted() {
        return this.size == 0L;
    }

    @Override
    public void flush() {
    }

    public byte getByte(long l) {
        Util.checkOffsetAndCount(this.size, l, 1L);
        Segment segment = this.head;
        int n;
        while (l >= (long)(n = segment.limit - segment.pos)) {
            l -= (long)n;
            segment = segment.next;
        }
        return segment.data[segment.pos + (int)l];
    }

    public int hashCode() {
        Segment segment;
        int n;
        Segment segment2 = this.head;
        if (segment2 == null) {
            return 0;
        }
        int n2 = 1;
        do {
            int n3 = segment2.pos;
            int n4 = segment2.limit;
            n = n2;
            for (n2 = n3; n2 < n4; ++n2) {
                n = n * 31 + segment2.data[n2];
            }
            segment = segment2.next;
            n2 = n;
            segment2 = segment;
        } while (segment != this.head);
        return n;
    }

    @Override
    public long indexOf(byte by) {
        return this.indexOf(by, 0L);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public long indexOf(byte var1_1, long var2_2) {
        block8: {
            if (var2_2 < 0L) {
                throw new IllegalArgumentException("fromIndex < 0");
            }
            var13_3 = this.head;
            if (var13_3 == null) {
                return -1L;
            }
            if (this.size - var2_2 >= var2_2) break block8;
            var8_10 = this.size;
            do {
                var6_11 = var8_10;
                var12_13 = var13_4;
                var10_12 = var2_2;
                if (var8_10 > var2_2) {
                    var13_5 = var13_4.prev;
                    var8_10 -= (long)(var13_5.limit - var13_5.pos);
                    continue;
                }
                ** GOTO lbl29
                break;
            } while (true);
        }
        var6_11 = 0L;
        do {
            var8_10 = var6_11 + (long)(var13_6.limit - var13_6.pos);
            var12_14 = var13_6;
            var10_12 = var2_2;
            if (var8_10 >= var2_2) ** GOTO lbl29
            var13_7 = var13_6.next;
            var6_11 = var8_10;
        } while (true);
        {
            var10_12 = var6_11 += (long)(var12_15.limit - var12_15.pos);
            var12_16 = var12_15.next;
lbl29:
            // 3 sources
            if (var6_11 >= this.size) return -1L;
            var13_8 = var12_15.data;
            var4_17 = (int)((long)var12_15.pos + var10_12 - var6_11);
            var5_18 = var12_15.limit;
            do {
                if (var4_17 >= var5_18) continue block2;
                if (var13_8[var4_17] == var1_1) {
                    return (long)(var4_17 - var12_15.pos) + var6_11;
                }
                ++var4_17;
            } while (true);
        }
    }

    @Override
    public InputStream inputStream() {
        return new InputStream(){

            @Override
            public int available() {
                return (int)Math.min(Buffer.this.size, Integer.MAX_VALUE);
            }

            @Override
            public void close() {
            }

            @Override
            public int read() {
                if (Buffer.this.size > 0L) {
                    return Buffer.this.readByte() & 0xFF;
                }
                return -1;
            }

            @Override
            public int read(byte[] arrby, int n, int n2) {
                return Buffer.this.read(arrby, n, n2);
            }

            public String toString() {
                return Buffer.this + ".inputStream()";
            }
        };
    }

    public OutputStream outputStream() {
        return new OutputStream(){

            @Override
            public void close() {
            }

            @Override
            public void flush() {
            }

            public String toString() {
                return Buffer.this + ".outputStream()";
            }

            @Override
            public void write(int n) {
                Buffer.this.writeByte((byte)n);
            }

            @Override
            public void write(byte[] arrby, int n, int n2) {
                Buffer.this.write(arrby, n, n2);
            }
        };
    }

    @Override
    public boolean rangeEquals(long l, ByteString byteString) {
        return this.rangeEquals(l, byteString, 0, byteString.size());
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean rangeEquals(long l, ByteString byteString, int n, int n2) {
        if (l >= 0L && n >= 0 && n2 >= 0 && this.size - l >= (long)n2 && byteString.size() - n >= n2) {
            int n3 = 0;
            do {
                if (n3 >= n2) {
                    return true;
                }
                if (this.getByte((long)n3 + l) != byteString.getByte(n + n3)) break;
                ++n3;
            } while (true);
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int read(byte[] arrby, int n, int n2) {
        Util.checkOffsetAndCount(arrby.length, n, n2);
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        n2 = Math.min(n2, segment.limit - segment.pos);
        System.arraycopy(segment.data, segment.pos, arrby, n, n2);
        segment.pos += n2;
        this.size -= (long)n2;
        n = n2;
        if (segment.pos != segment.limit) return n;
        this.head = segment.pop();
        SegmentPool.recycle(segment);
        return n2;
    }

    @Override
    public long read(Buffer buffer, long l) {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (l < 0L) {
            throw new IllegalArgumentException("byteCount < 0: " + l);
        }
        if (this.size == 0L) {
            return -1L;
        }
        long l2 = l;
        if (l > this.size) {
            l2 = this.size;
        }
        buffer.write(this, l2);
        return l2;
    }

    @Override
    public long readAll(Sink sink) throws IOException {
        long l = this.size;
        if (l > 0L) {
            sink.write(this, l);
        }
        return l;
    }

    @Override
    public byte readByte() {
        if (this.size == 0L) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.head;
        int n = segment.pos;
        int n2 = segment.limit;
        byte[] arrby = segment.data;
        int n3 = n + 1;
        byte by = arrby[n];
        --this.size;
        if (n3 == n2) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            return by;
        }
        segment.pos = n3;
        return by;
    }

    public byte[] readByteArray() {
        try {
            byte[] arrby = this.readByteArray(this.size);
            return arrby;
        }
        catch (EOFException eOFException) {
            throw new AssertionError(eOFException);
        }
    }

    @Override
    public byte[] readByteArray(long l) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0L, l);
        if (l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + l);
        }
        byte[] arrby = new byte[(int)l];
        this.readFully(arrby);
        return arrby;
    }

    public ByteString readByteString() {
        return new ByteString(this.readByteArray());
    }

    @Override
    public ByteString readByteString(long l) throws EOFException {
        return new ByteString(this.readByteArray(l));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long readDecimalLong() {
        long l;
        int n;
        int n2;
        if (this.size == 0L) {
            throw new IllegalStateException("size == 0");
        }
        long l2 = 0L;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        long l3 = -7L;
        do {
            Object object;
            int n6;
            block11: {
                object = this.head;
                byte[] arrby = ((Segment)object).data;
                n = ((Segment)object).pos;
                n6 = ((Segment)object).limit;
                l = l2;
                n2 = n3;
                l2 = l3;
                n3 = n;
                n = n4;
                do {
                    n4 = n5;
                    if (n3 >= n6) break block11;
                    n4 = arrby[n3];
                    if (n4 >= 48 && n4 <= 57) {
                        int n7 = 48 - n4;
                        if (l < -922337203685477580L || l == -922337203685477580L && (long)n7 < l2) {
                            object = new Buffer().writeDecimalLong(l).writeByte(n4);
                            if (n == 0) {
                                ((Buffer)object).readByte();
                            }
                            throw new NumberFormatException("Number too large: " + ((Buffer)object).readUtf8());
                        }
                        l = l * 10L + (long)n7;
                    } else {
                        if (n4 != 45 || n2 != 0) break;
                        n = 1;
                        --l2;
                    }
                    ++n3;
                    ++n2;
                } while (true);
                if (n2 == 0) {
                    throw new NumberFormatException("Expected leading [0-9] or '-' character but was 0x" + Integer.toHexString(n4));
                }
                n4 = 1;
            }
            if (n3 == n6) {
                this.head = ((Segment)object).pop();
                SegmentPool.recycle((Segment)object);
            } else {
                ((Segment)object).pos = n3;
            }
            if (n4 != 0) break;
            n5 = n4;
            n4 = n;
            l3 = l2;
            n3 = n2;
            l2 = l;
        } while (this.head != null);
        this.size -= (long)n2;
        if (n != 0) {
            return l;
        }
        return -l;
    }

    public void readFully(byte[] arrby) throws EOFException {
        int n;
        for (int i = 0; i < arrby.length; i += n) {
            n = this.read(arrby, i, arrby.length - i);
            if (n != -1) continue;
            throw new EOFException();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public long readHexadecimalUnsignedLong() {
        if (this.size == 0L) {
            throw new IllegalStateException("size == 0");
        }
        var7_1 = 0L;
        var1_2 = 0;
        var2_3 = 0;
        block0: do {
            var11_9 = this.head;
            var12_10 = var11_9.data;
            var4_5 = var11_9.pos;
            var5_6 = var11_9.limit;
            var9_8 = var7_1;
            var3_4 = var1_2;
            do {
                block11: {
                    block10: {
                        var1_2 = var2_3;
                        if (var4_5 >= var5_6) ** GOTO lbl34
                        var6_7 = var12_10[var4_5];
                        if (var6_7 < 48 || var6_7 > 57) break block10;
                        var1_2 = var6_7 - 48;
                        ** GOTO lbl27
                    }
                    if (var6_7 < 97 || var6_7 > 102) break block11;
                    var1_2 = var6_7 - 97 + 10;
                    ** GOTO lbl27
                }
                if (var6_7 >= 65 && var6_7 <= 70) {
                    var1_2 = var6_7 - 65 + 10;
lbl27:
                    // 3 sources
                    if ((-1152921504606846976L & var9_8) != 0L) {
                        var11_9 = new Buffer().writeHexadecimalUnsignedLong(var9_8).writeByte(var6_7);
                        throw new NumberFormatException("Number too large: " + var11_9.readUtf8());
                    }
                } else {
                    if (var3_4 == 0) {
                        throw new NumberFormatException("Expected leading [0-9a-fA-F] character but was 0x" + Integer.toHexString(var6_7));
                    }
                    var1_2 = 1;
lbl34:
                    // 2 sources
                    if (var4_5 == var5_6) {
                        this.head = var11_9.pop();
                        SegmentPool.recycle((Segment)var11_9);
                    } else {
                        var11_9.pos = var4_5;
                    }
                    if (var1_2 == 0) {
                        var2_3 = var1_2;
                        var1_2 = var3_4;
                        var7_1 = var9_8;
                        if (this.head != null) continue block0;
                    }
                    this.size -= (long)var3_4;
                    return var9_8;
                }
                var9_8 = var9_8 << 4 | (long)var1_2;
                ++var4_5;
                ++var3_4;
            } while (true);
            break;
        } while (true);
    }

    @Override
    public int readInt() {
        if (this.size < 4L) {
            throw new IllegalStateException("size < 4: " + this.size);
        }
        Segment segment = this.head;
        int n = segment.limit;
        int n2 = segment.pos;
        if (n - n2 < 4) {
            return (this.readByte() & 0xFF) << 24 | (this.readByte() & 0xFF) << 16 | (this.readByte() & 0xFF) << 8 | this.readByte() & 0xFF;
        }
        byte[] arrby = segment.data;
        int n3 = n2 + 1;
        n2 = arrby[n2];
        int n4 = n3 + 1;
        n3 = arrby[n3];
        int n5 = n4 + 1;
        byte by = arrby[n4];
        n4 = n5 + 1;
        n2 = (n2 & 0xFF) << 24 | (n3 & 0xFF) << 16 | (by & 0xFF) << 8 | arrby[n5] & 0xFF;
        this.size -= 4L;
        if (n4 == n) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            return n2;
        }
        segment.pos = n4;
        return n2;
    }

    @Override
    public int readIntLe() {
        return Util.reverseBytesInt(this.readInt());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public short readShort() {
        if (this.size < 2L) {
            throw new IllegalStateException("size < 2: " + this.size);
        }
        Segment segment = this.head;
        int n = segment.limit;
        int n2 = segment.pos;
        if (n - n2 < 2) {
            return (short)((this.readByte() & 0xFF) << 8 | this.readByte() & 0xFF);
        }
        byte[] arrby = segment.data;
        int n3 = n2 + 1;
        n2 = arrby[n2];
        int n4 = n3 + 1;
        n3 = arrby[n3];
        this.size -= 2L;
        if (n4 == n) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            do {
                return (short)((n2 & 0xFF) << 8 | n3 & 0xFF);
                break;
            } while (true);
        }
        segment.pos = n4;
        return (short)((n2 & 0xFF) << 8 | n3 & 0xFF);
    }

    @Override
    public short readShortLe() {
        return Util.reverseBytesShort(this.readShort());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String readString(long l, Charset object) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0L, l);
        if (object == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + l);
        }
        if (l == 0L) {
            return "";
        }
        Segment segment = this.head;
        if ((long)segment.pos + l > (long)segment.limit) {
            return new String(this.readByteArray(l), (Charset)object);
        }
        String string2 = new String(segment.data, segment.pos, (int)l, (Charset)object);
        segment.pos = (int)((long)segment.pos + l);
        this.size -= l;
        object = string2;
        if (segment.pos != segment.limit) return object;
        this.head = segment.pop();
        SegmentPool.recycle(segment);
        return string2;
    }

    @Override
    public String readString(Charset object) {
        try {
            object = this.readString(this.size, (Charset)object);
            return object;
        }
        catch (EOFException eOFException) {
            throw new AssertionError(eOFException);
        }
    }

    public String readUtf8() {
        try {
            String string2 = this.readString(this.size, Util.UTF_8);
            return string2;
        }
        catch (EOFException eOFException) {
            throw new AssertionError(eOFException);
        }
    }

    public String readUtf8(long l) throws EOFException {
        return this.readString(l, Util.UTF_8);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int readUtf8CodePoint() throws EOFException {
        int n;
        int n2;
        int n3;
        if (this.size == 0L) {
            throw new EOFException();
        }
        int n4 = this.getByte(0L);
        if ((n4 & 0x80) == 0) {
            n3 = n4 & 0x7F;
            n2 = 1;
            n = 0;
        } else if ((n4 & 0xE0) == 192) {
            n3 = n4 & 0x1F;
            n2 = 2;
            n = 128;
        } else if ((n4 & 0xF0) == 224) {
            n3 = n4 & 0xF;
            n2 = 3;
            n = 2048;
        } else {
            if ((n4 & 0xF8) != 240) {
                this.skip(1L);
                return 65533;
            }
            n3 = n4 & 7;
            n2 = 4;
            n = 65536;
        }
        if (this.size < (long)n2) {
            throw new EOFException("size < " + n2 + ": " + this.size + " (to read code point prefixed 0x" + Integer.toHexString(n4) + ")");
        }
        for (n4 = 1; n4 < n2; ++n4) {
            byte by = this.getByte(n4);
            if ((by & 0xC0) != 128) {
                this.skip(n4);
                return 65533;
            }
            n3 = n3 << 6 | by & 0x3F;
        }
        this.skip(n2);
        if (n3 > 1114111) {
            return 65533;
        }
        if (n3 >= 55296 && n3 <= 57343) {
            return 65533;
        }
        n2 = n3;
        if (n3 >= n) return n2;
        return 65533;
    }

    String readUtf8Line(long l) throws EOFException {
        if (l > 0L && this.getByte(l - 1L) == 13) {
            String string2 = this.readUtf8(l - 1L);
            this.skip(2L);
            return string2;
        }
        String string3 = this.readUtf8(l);
        this.skip(1L);
        return string3;
    }

    @Override
    public String readUtf8LineStrict() throws EOFException {
        long l = this.indexOf((byte)10);
        if (l == -1L) {
            Buffer buffer = new Buffer();
            this.copyTo(buffer, 0L, Math.min(32L, this.size));
            throw new EOFException("\\n not found: size=" + this.size() + " content=" + buffer.readByteString().hex() + "\u2026");
        }
        return this.readUtf8Line(l);
    }

    @Override
    public boolean request(long l) {
        return this.size >= l;
    }

    @Override
    public void require(long l) throws EOFException {
        if (this.size < l) {
            throw new EOFException();
        }
    }

    public long size() {
        return this.size;
    }

    @Override
    public void skip(long l) throws EOFException {
        while (l > 0L) {
            if (this.head == null) {
                throw new EOFException();
            }
            int n = (int)Math.min(l, (long)(this.head.limit - this.head.pos));
            this.size -= (long)n;
            long l2 = l - (long)n;
            Segment segment = this.head;
            segment.pos += n;
            l = l2;
            if (this.head.pos != this.head.limit) continue;
            segment = this.head;
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            l = l2;
        }
    }

    public ByteString snapshot() {
        if (this.size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.size);
        }
        return this.snapshot((int)this.size);
    }

    public ByteString snapshot(int n) {
        if (n == 0) {
            return ByteString.EMPTY;
        }
        return new SegmentedByteString(this, n);
    }

    @Override
    public Timeout timeout() {
        return Timeout.NONE;
    }

    public String toString() {
        return this.snapshot().toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    Segment writableSegment(int n) {
        Segment segment;
        if (n < 1 || n > 8192) {
            throw new IllegalArgumentException();
        }
        if (this.head == null) {
            Segment segment2 = this.head = SegmentPool.take();
            Segment segment3 = this.head;
            segment3.prev = segment = this.head;
            segment2.next = segment;
            return segment;
        }
        Segment segment4 = this.head.prev;
        if (segment4.limit + n > 8192) return segment4.push(SegmentPool.take());
        segment = segment4;
        if (segment4.owner) return segment;
        return segment4.push(SegmentPool.take());
    }

    @Override
    public Buffer write(ByteString byteString) {
        if (byteString == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        byteString.write(this);
        return this;
    }

    @Override
    public Buffer write(byte[] arrby) {
        if (arrby == null) {
            throw new IllegalArgumentException("source == null");
        }
        return this.write(arrby, 0, arrby.length);
    }

    @Override
    public Buffer write(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            throw new IllegalArgumentException("source == null");
        }
        Util.checkOffsetAndCount(arrby.length, n, n2);
        int n3 = n + n2;
        while (n < n3) {
            Segment segment = this.writableSegment(1);
            int n4 = Math.min(n3 - n, 8192 - segment.limit);
            System.arraycopy(arrby, n, segment.data, segment.limit, n4);
            n += n4;
            segment.limit += n4;
        }
        this.size += (long)n2;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(Buffer buffer, long l) {
        if (buffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (buffer == this) {
            throw new IllegalArgumentException("source == this");
        }
        Util.checkOffsetAndCount(buffer.size, 0L, l);
        while (l > 0L) {
            long l2;
            Segment segment;
            if (l < (long)(buffer.head.limit - buffer.head.pos)) {
                int n;
                segment = this.head != null ? this.head.prev : null;
                if (segment != null && segment.owner && l + (l2 = (long)segment.limit) - (long)(n = segment.shared ? 0 : segment.pos) <= 8192L) {
                    buffer.head.writeTo(segment, (int)l);
                    buffer.size -= l;
                    this.size += l;
                    return;
                }
                buffer.head = buffer.head.split((int)l);
            }
            segment = buffer.head;
            l2 = segment.limit - segment.pos;
            buffer.head = segment.pop();
            if (this.head == null) {
                Segment segment2;
                segment = this.head = segment;
                Segment segment3 = this.head;
                segment3.prev = segment2 = this.head;
                segment.next = segment2;
            } else {
                this.head.prev.push(segment).compact();
            }
            buffer.size -= l2;
            this.size += l2;
            l -= l2;
        }
    }

    public long writeAll(Source source) throws IOException {
        long l;
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long l2 = 0L;
        while ((l = source.read(this, 8192L)) != -1L) {
            l2 += l;
        }
        return l2;
    }

    @Override
    public Buffer writeByte(int n) {
        Segment segment = this.writableSegment(1);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        segment.limit = n2 + 1;
        arrby[n2] = (byte)n;
        ++this.size;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Buffer writeDecimalLong(long l) {
        if (l == 0L) {
            return this.writeByte(48);
        }
        boolean bl = false;
        long l2 = l;
        if (l < 0L) {
            l2 = -l;
            if (l2 < 0L) {
                return this.writeUtf8("-9223372036854775808");
            }
            bl = true;
        }
        int n = l2 < 100000000L ? (l2 < 10000L ? (l2 < 100L ? (l2 < 10L ? 1 : 2) : (l2 < 1000L ? 3 : 4)) : (l2 < 1000000L ? (l2 < 100000L ? 5 : 6) : (l2 < 10000000L ? 7 : 8))) : (l2 < 1000000000000L ? (l2 < 10000000000L ? (l2 < 1000000000L ? 9 : 10) : (l2 < 100000000000L ? 11 : 12)) : (l2 < 1000000000000000L ? (l2 < 10000000000000L ? 13 : (l2 < 100000000000000L ? 14 : 15)) : (l2 < 100000000000000000L ? (l2 < 10000000000000000L ? 16 : 17) : (l2 < 1000000000000000000L ? 18 : 19))));
        int n2 = n;
        if (bl) {
            n2 = n + 1;
        }
        Segment segment = this.writableSegment(n2);
        byte[] arrby = segment.data;
        n = segment.limit + n2;
        while (l2 != 0L) {
            int n3 = (int)(l2 % 10L);
            arrby[--n] = DIGITS[n3];
            l2 /= 10L;
        }
        if (bl) {
            arrby[n - 1] = 45;
        }
        segment.limit += n2;
        this.size += (long)n2;
        return this;
    }

    @Override
    public Buffer writeHexadecimalUnsignedLong(long l) {
        if (l == 0L) {
            return this.writeByte(48);
        }
        int n = Long.numberOfTrailingZeros(Long.highestOneBit(l)) / 4 + 1;
        Segment segment = this.writableSegment(n);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        for (int i = segment.limit + n - 1; i >= n2; --i) {
            arrby[i] = DIGITS[(int)(0xFL & l)];
            l >>>= 4;
        }
        segment.limit += n;
        this.size += (long)n;
        return this;
    }

    @Override
    public Buffer writeInt(int n) {
        Segment segment = this.writableSegment(4);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        int n3 = n2 + 1;
        arrby[n2] = (byte)(n >>> 24 & 0xFF);
        n2 = n3 + 1;
        arrby[n3] = (byte)(n >>> 16 & 0xFF);
        n3 = n2 + 1;
        arrby[n2] = (byte)(n >>> 8 & 0xFF);
        arrby[n3] = (byte)(n & 0xFF);
        segment.limit = n3 + 1;
        this.size += 4L;
        return this;
    }

    @Override
    public Buffer writeShort(int n) {
        Segment segment = this.writableSegment(2);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        int n3 = n2 + 1;
        arrby[n2] = (byte)(n >>> 8 & 0xFF);
        arrby[n3] = (byte)(n & 0xFF);
        segment.limit = n3 + 1;
        this.size += 2L;
        return this;
    }

    @Override
    public Buffer writeUtf8(String string2) {
        return this.writeUtf8(string2, 0, string2.length());
    }

    /*
     * Enabled aggressive block sorting
     */
    public Buffer writeUtf8(String string2, int n, int n2) {
        if (string2 == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (n < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + n);
        }
        if (n2 < n) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + n2 + " < " + n);
        }
        if (n2 > string2.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + n2 + " > " + string2.length());
        }
        block0 : while (n < n2) {
            Segment segment;
            int n3;
            int n4;
            char c = string2.charAt(n);
            if (c < '\u0080') {
                segment = this.writableSegment(1);
                byte[] arrby = segment.data;
                n4 = segment.limit - n;
                n3 = Math.min(n2, 8192 - n4);
                arrby[n4 + n] = (byte)c;
                ++n;
            } else {
                if (c < '\u0800') {
                    this.writeByte(c >> 6 | 0xC0);
                    this.writeByte(c & 0x3F | 0x80);
                    ++n;
                    continue;
                }
                if (c < '\ud800' || c > '\udfff') {
                    this.writeByte(c >> 12 | 0xE0);
                    this.writeByte(c >> 6 & 0x3F | 0x80);
                    this.writeByte(c & 0x3F | 0x80);
                    ++n;
                    continue;
                }
                n4 = n + 1 < n2 ? (int)string2.charAt(n + 1) : 0;
                if (c > '\udbff' || n4 < 56320 || n4 > 57343) {
                    this.writeByte(63);
                    ++n;
                    continue;
                }
                n4 = 65536 + ((0xFFFF27FF & c) << 10 | 0xFFFF23FF & n4);
                this.writeByte(n4 >> 18 | 0xF0);
                this.writeByte(n4 >> 12 & 0x3F | 0x80);
                this.writeByte(n4 >> 6 & 0x3F | 0x80);
                this.writeByte(n4 & 0x3F | 0x80);
                n += 2;
                continue;
            }
            do {
                if (n >= n3 || (c = string2.charAt(n)) >= '\u0080') {
                    n4 = n + n4 - segment.limit;
                    segment.limit += n4;
                    this.size += (long)n4;
                    continue block0;
                }
                arrby[n4 + n] = (byte)c;
                ++n;
            } while (true);
            break;
        }
        return this;
    }

    public Buffer writeUtf8CodePoint(int n) {
        if (n < 128) {
            this.writeByte(n);
            return this;
        }
        if (n < 2048) {
            this.writeByte(n >> 6 | 0xC0);
            this.writeByte(n & 0x3F | 0x80);
            return this;
        }
        if (n < 65536) {
            if (n >= 55296 && n <= 57343) {
                throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(n));
            }
            this.writeByte(n >> 12 | 0xE0);
            this.writeByte(n >> 6 & 0x3F | 0x80);
            this.writeByte(n & 0x3F | 0x80);
            return this;
        }
        if (n <= 1114111) {
            this.writeByte(n >> 18 | 0xF0);
            this.writeByte(n >> 12 & 0x3F | 0x80);
            this.writeByte(n >> 6 & 0x3F | 0x80);
            this.writeByte(n & 0x3F | 0x80);
            return this;
        }
        throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(n));
    }

}


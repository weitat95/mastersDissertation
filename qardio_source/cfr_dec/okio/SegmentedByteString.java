/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.util.Arrays;
import okio.Buffer;
import okio.ByteString;
import okio.Segment;
import okio.Util;

final class SegmentedByteString
extends ByteString {
    final transient int[] directory;
    final transient byte[][] segments;

    SegmentedByteString(Buffer object, int n) {
        super(null);
        Util.checkOffsetAndCount(((Buffer)object).size, 0L, n);
        int n2 = 0;
        int n3 = 0;
        Segment segment = ((Buffer)object).head;
        while (n2 < n) {
            if (segment.limit == segment.pos) {
                throw new AssertionError((Object)"s.limit == s.pos");
            }
            n2 += segment.limit - segment.pos;
            ++n3;
            segment = segment.next;
        }
        this.segments = new byte[n3][];
        this.directory = new int[n3 * 2];
        n3 = 0;
        n2 = 0;
        object = ((Buffer)object).head;
        while (n3 < n) {
            int n4;
            this.segments[n2] = ((Segment)object).data;
            n3 = n4 = n3 + (((Segment)object).limit - ((Segment)object).pos);
            if (n4 > n) {
                n3 = n;
            }
            this.directory[n2] = n3;
            this.directory[this.segments.length + n2] = ((Segment)object).pos;
            ((Segment)object).shared = true;
            ++n2;
            object = ((Segment)object).next;
        }
    }

    private int segment(int n) {
        if ((n = Arrays.binarySearch(this.directory, 0, this.segments.length, n + 1)) >= 0) {
            return n;
        }
        return ~n;
    }

    private ByteString toByteString() {
        return new ByteString(this.toByteArray());
    }

    @Override
    public String base64() {
        return this.toByteString().base64();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ByteString)) return false;
        if (((ByteString)object).size() != this.size()) return false;
        if (!this.rangeEquals(0, (ByteString)object, 0, this.size())) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public byte getByte(int n) {
        Util.checkOffsetAndCount(this.directory[this.segments.length - 1], n, 1L);
        int n2 = this.segment(n);
        int n3 = n2 == 0 ? 0 : this.directory[n2 - 1];
        int n4 = this.directory[this.segments.length + n2];
        return this.segments[n2][n - n3 + n4];
    }

    @Override
    public int hashCode() {
        int n = this.hashCode;
        if (n != 0) {
            return n;
        }
        int n2 = 1;
        int n3 = 0;
        int n4 = this.segments.length;
        for (n = 0; n < n4; ++n) {
            byte[] arrby = this.segments[n];
            int n5 = this.directory[n4 + n];
            int n6 = this.directory[n];
            for (int i = n5; i < n5 + (n6 - n3); ++i) {
                n2 = n2 * 31 + arrby[i];
            }
            n3 = n6;
        }
        this.hashCode = n2;
        return n2;
    }

    @Override
    public String hex() {
        return this.toByteString().hex();
    }

    @Override
    public ByteString md5() {
        return this.toByteString().md5();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean rangeEquals(int n, ByteString byteString, int n2, int n3) {
        if (n >= 0 && n <= this.size() - n3) {
            int n4 = this.segment(n);
            int n5 = n;
            n = n4;
            do {
                int n6;
                int n7;
                if (n3 <= 0) {
                    return true;
                }
                n4 = n == 0 ? 0 : this.directory[n - 1];
                if (!byteString.rangeEquals(n2, this.segments[n], n5 - n4 + (n7 = this.directory[this.segments.length + n]), n6 = Math.min(n3, n4 + (this.directory[n] - n4) - n5))) break;
                n5 += n6;
                n2 += n6;
                n3 -= n6;
                ++n;
            } while (true);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean rangeEquals(int n, byte[] arrby, int n2, int n3) {
        if (n >= 0 && n <= this.size() - n3 && n2 >= 0 && n2 <= arrby.length - n3) {
            int n4 = this.segment(n);
            int n5 = n;
            n = n4;
            do {
                int n6;
                int n7;
                if (n3 <= 0) {
                    return true;
                }
                n4 = n == 0 ? 0 : this.directory[n - 1];
                if (!Util.arrayRangeEquals(this.segments[n], n5 - n4 + (n7 = this.directory[this.segments.length + n]), arrby, n2, n6 = Math.min(n3, n4 + (this.directory[n] - n4) - n5))) break;
                n5 += n6;
                n2 += n6;
                n3 -= n6;
                ++n;
            } while (true);
        }
        return false;
    }

    @Override
    public ByteString sha1() {
        return this.toByteString().sha1();
    }

    @Override
    public ByteString sha256() {
        return this.toByteString().sha256();
    }

    @Override
    public int size() {
        return this.directory[this.segments.length - 1];
    }

    @Override
    public ByteString substring(int n, int n2) {
        return this.toByteString().substring(n, n2);
    }

    @Override
    public ByteString toAsciiLowercase() {
        return this.toByteString().toAsciiLowercase();
    }

    @Override
    public byte[] toByteArray() {
        byte[] arrby = new byte[this.directory[this.segments.length - 1]];
        int n = 0;
        int n2 = this.segments.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = this.directory[n2 + i];
            int n4 = this.directory[i];
            System.arraycopy(this.segments[i], n3, arrby, n, n4 - n);
            n = n4;
        }
        return arrby;
    }

    @Override
    public String toString() {
        return this.toByteString().toString();
    }

    @Override
    public String utf8() {
        return this.toByteString().utf8();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void write(Buffer buffer) {
        int n = 0;
        int n2 = 0;
        int n3 = this.segments.length;
        do {
            if (n2 >= n3) {
                buffer.size += (long)n;
                return;
            }
            int n4 = this.directory[n3 + n2];
            int n5 = this.directory[n2];
            Segment segment = new Segment(this.segments[n2], n4, n4 + n5 - n);
            if (buffer.head == null) {
                segment.prev = segment;
                segment.next = segment;
                buffer.head = segment;
            } else {
                buffer.head.prev.push(segment);
            }
            n = n5;
            ++n2;
        } while (true);
    }
}


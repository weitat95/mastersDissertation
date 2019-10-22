/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.WireFormat;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class CodedOutputStream
implements Flushable {
    private final byte[] buffer;
    private final int limit;
    private final OutputStream output;
    private int position;

    private CodedOutputStream(OutputStream outputStream, byte[] arrby) {
        this.output = outputStream;
        this.buffer = arrby;
        this.position = 0;
        this.limit = arrby.length;
    }

    public static int computeBoolSize(int n, boolean bl) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeBoolSizeNoTag(bl);
    }

    public static int computeBoolSizeNoTag(boolean bl) {
        return 1;
    }

    public static int computeBytesSize(int n, ByteString byteString) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeBytesSizeNoTag(byteString);
    }

    public static int computeBytesSizeNoTag(ByteString byteString) {
        return CodedOutputStream.computeRawVarint32Size(byteString.size()) + byteString.size();
    }

    public static int computeEnumSize(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeEnumSizeNoTag(n2);
    }

    public static int computeEnumSizeNoTag(int n) {
        return CodedOutputStream.computeInt32SizeNoTag(n);
    }

    public static int computeFloatSize(int n, float f) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeFloatSizeNoTag(f);
    }

    public static int computeFloatSizeNoTag(float f) {
        return 4;
    }

    public static int computeInt32SizeNoTag(int n) {
        if (n >= 0) {
            return CodedOutputStream.computeRawVarint32Size(n);
        }
        return 10;
    }

    public static int computeRawVarint32Size(int n) {
        if ((n & 0xFFFFFF80) == 0) {
            return 1;
        }
        if ((n & 0xFFFFC000) == 0) {
            return 2;
        }
        if ((0xFFE00000 & n) == 0) {
            return 3;
        }
        if ((0xF0000000 & n) == 0) {
            return 4;
        }
        return 5;
    }

    public static int computeRawVarint64Size(long l) {
        if ((0xFFFFFFFFFFFFFF80L & l) == 0L) {
            return 1;
        }
        if ((0xFFFFFFFFFFFFC000L & l) == 0L) {
            return 2;
        }
        if ((0xFFFFFFFFFFE00000L & l) == 0L) {
            return 3;
        }
        if ((0xFFFFFFFFF0000000L & l) == 0L) {
            return 4;
        }
        if ((0xFFFFFFF800000000L & l) == 0L) {
            return 5;
        }
        if ((0xFFFFFC0000000000L & l) == 0L) {
            return 6;
        }
        if ((0xFFFE000000000000L & l) == 0L) {
            return 7;
        }
        if ((0xFF00000000000000L & l) == 0L) {
            return 8;
        }
        if ((Long.MIN_VALUE & l) == 0L) {
            return 9;
        }
        return 10;
    }

    public static int computeSInt32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeSInt32SizeNoTag(n2);
    }

    public static int computeSInt32SizeNoTag(int n) {
        return CodedOutputStream.computeRawVarint32Size(CodedOutputStream.encodeZigZag32(n));
    }

    public static int computeTagSize(int n) {
        return CodedOutputStream.computeRawVarint32Size(WireFormat.makeTag(n, 0));
    }

    public static int computeUInt32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeUInt32SizeNoTag(n2);
    }

    public static int computeUInt32SizeNoTag(int n) {
        return CodedOutputStream.computeRawVarint32Size(n);
    }

    public static int computeUInt64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeUInt64SizeNoTag(l);
    }

    public static int computeUInt64SizeNoTag(long l) {
        return CodedOutputStream.computeRawVarint64Size(l);
    }

    public static int encodeZigZag32(int n) {
        return n << 1 ^ n >> 31;
    }

    public static CodedOutputStream newInstance(OutputStream outputStream) {
        return CodedOutputStream.newInstance(outputStream, 4096);
    }

    public static CodedOutputStream newInstance(OutputStream outputStream, int n) {
        return new CodedOutputStream(outputStream, new byte[n]);
    }

    private void refreshBuffer() throws IOException {
        if (this.output == null) {
            throw new OutOfSpaceException();
        }
        this.output.write(this.buffer, 0, this.position);
        this.position = 0;
    }

    @Override
    public void flush() throws IOException {
        if (this.output != null) {
            this.refreshBuffer();
        }
    }

    public void writeBool(int n, boolean bl) throws IOException {
        this.writeTag(n, 0);
        this.writeBoolNoTag(bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeBoolNoTag(boolean bl) throws IOException {
        int n = bl ? 1 : 0;
        this.writeRawByte(n);
    }

    public void writeBytes(int n, ByteString byteString) throws IOException {
        this.writeTag(n, 2);
        this.writeBytesNoTag(byteString);
    }

    public void writeBytesNoTag(ByteString byteString) throws IOException {
        this.writeRawVarint32(byteString.size());
        this.writeRawBytes(byteString);
    }

    public void writeEnum(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeEnumNoTag(n2);
    }

    public void writeEnumNoTag(int n) throws IOException {
        this.writeInt32NoTag(n);
    }

    public void writeFloat(int n, float f) throws IOException {
        this.writeTag(n, 5);
        this.writeFloatNoTag(f);
    }

    public void writeFloatNoTag(float f) throws IOException {
        this.writeRawLittleEndian32(Float.floatToRawIntBits(f));
    }

    public void writeInt32NoTag(int n) throws IOException {
        if (n >= 0) {
            this.writeRawVarint32(n);
            return;
        }
        this.writeRawVarint64(n);
    }

    public void writeRawByte(byte by) throws IOException {
        if (this.position == this.limit) {
            this.refreshBuffer();
        }
        byte[] arrby = this.buffer;
        int n = this.position;
        this.position = n + 1;
        arrby[n] = by;
    }

    public void writeRawByte(int n) throws IOException {
        this.writeRawByte((byte)n);
    }

    public void writeRawBytes(ByteString byteString) throws IOException {
        this.writeRawBytes(byteString, 0, byteString.size());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void writeRawBytes(ByteString var1_1, int var2_2, int var3_3) throws IOException {
        if (this.limit - this.position >= var3_3) {
            var1_1.copyTo(this.buffer, var2_2, this.position, var3_3);
            this.position += var3_3;
            return;
        }
        var5_4 = this.limit - this.position;
        var1_1.copyTo(this.buffer, var2_2, this.position, var5_4);
        var4_5 = var2_2 + var5_4;
        var2_2 = var3_3 - var5_4;
        this.position = this.limit;
        this.refreshBuffer();
        if (var2_2 <= this.limit) {
            var1_1.copyTo(this.buffer, var4_5, 0, var2_2);
            this.position = var2_2;
            return;
        }
        if ((long)var4_5 == (var1_1 = var1_1.newInput()).skip(var4_5)) ** GOTO lbl19
        throw new IllegalStateException("Skip failed.");
lbl-1000:
        // 1 sources
        {
            this.output.write(this.buffer, 0, var4_5);
            var2_2 -= var4_5;
lbl19:
            // 2 sources
            if (var2_2 <= 0) return;
            ** while ((var4_5 = var1_1.read((byte[])this.buffer, (int)0, (int)(var3_3 = Math.min((int)var2_2, (int)this.limit)))) == var3_3)
        }
lbl21:
        // 1 sources
        throw new IllegalStateException("Read failed.");
    }

    public void writeRawBytes(byte[] arrby) throws IOException {
        this.writeRawBytes(arrby, 0, arrby.length);
    }

    public void writeRawBytes(byte[] arrby, int n, int n2) throws IOException {
        if (this.limit - this.position >= n2) {
            System.arraycopy(arrby, n, this.buffer, this.position, n2);
            this.position += n2;
            return;
        }
        int n3 = this.limit - this.position;
        System.arraycopy(arrby, n, this.buffer, this.position, n3);
        n += n3;
        this.position = this.limit;
        this.refreshBuffer();
        if ((n2 -= n3) <= this.limit) {
            System.arraycopy(arrby, n, this.buffer, 0, n2);
            this.position = n2;
            return;
        }
        this.output.write(arrby, n, n2);
    }

    public void writeRawLittleEndian32(int n) throws IOException {
        this.writeRawByte(n & 0xFF);
        this.writeRawByte(n >> 8 & 0xFF);
        this.writeRawByte(n >> 16 & 0xFF);
        this.writeRawByte(n >> 24 & 0xFF);
    }

    public void writeRawVarint32(int n) throws IOException {
        do {
            if ((n & 0xFFFFFF80) == 0) {
                this.writeRawByte(n);
                return;
            }
            this.writeRawByte(n & 0x7F | 0x80);
            n >>>= 7;
        } while (true);
    }

    public void writeRawVarint64(long l) throws IOException {
        do {
            if ((0xFFFFFFFFFFFFFF80L & l) == 0L) {
                this.writeRawByte((int)l);
                return;
            }
            this.writeRawByte((int)l & 0x7F | 0x80);
            l >>>= 7;
        } while (true);
    }

    public void writeSInt32(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeSInt32NoTag(n2);
    }

    public void writeSInt32NoTag(int n) throws IOException {
        this.writeRawVarint32(CodedOutputStream.encodeZigZag32(n));
    }

    public void writeTag(int n, int n2) throws IOException {
        this.writeRawVarint32(WireFormat.makeTag(n, n2));
    }

    public void writeUInt32(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeUInt32NoTag(n2);
    }

    public void writeUInt32NoTag(int n) throws IOException {
        this.writeRawVarint32(n);
    }

    public void writeUInt64(int n, long l) throws IOException {
        this.writeTag(n, 0);
        this.writeUInt64NoTag(l);
    }

    public void writeUInt64NoTag(long l) throws IOException {
        this.writeRawVarint64(l);
    }

    static class OutOfSpaceException
    extends IOException {
        OutOfSpaceException() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
    }

}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.bumptech.glide.load.resource.bitmap;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageHeaderParser {
    private static final int[] BYTES_PER_FORMAT = new int[]{0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
    private static final byte[] JPEG_EXIF_SEGMENT_PREAMBLE_BYTES;
    private final StreamReader streamReader;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        byte[] arrby = new byte[]{};
        try {
            byte[] arrby2 = "Exif\u0000\u0000".getBytes("UTF-8");
            arrby = arrby2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {}
        JPEG_EXIF_SEGMENT_PREAMBLE_BYTES = arrby;
    }

    public ImageHeaderParser(InputStream inputStream) {
        this.streamReader = new StreamReader(inputStream);
    }

    private static int calcTagOffset(int n, int n2) {
        return n + 2 + n2 * 12;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private byte[] getExifSegment() throws IOException {
        short s;
        int n;
        block4: {
            long l;
            do {
                if ((s = this.streamReader.getUInt8()) != 255) {
                    if (!Log.isLoggable((String)"ImageHeaderParser", (int)3)) return null;
                    Log.d((String)"ImageHeaderParser", (String)("Unknown segmentId=" + s));
                    return null;
                }
                s = this.streamReader.getUInt8();
                if (s == 218) {
                    return null;
                }
                if (s == 217) {
                    if (!Log.isLoggable((String)"ImageHeaderParser", (int)3)) return null;
                    Log.d((String)"ImageHeaderParser", (String)"Found MARKER_EOI in exif segment");
                    return null;
                }
                n = this.streamReader.getUInt16() - 2;
                if (s == 225) break block4;
            } while ((l = this.streamReader.skip(n)) == (long)n);
            if (!Log.isLoggable((String)"ImageHeaderParser", (int)3)) return null;
            Log.d((String)"ImageHeaderParser", (String)("Unable to skip enough data, type: " + s + ", wanted to skip: " + n + ", but actually skipped: " + l));
            return null;
        }
        byte[] arrby = new byte[n];
        int n2 = this.streamReader.read(arrby);
        if (n2 == n) return arrby;
        if (!Log.isLoggable((String)"ImageHeaderParser", (int)3)) return null;
        Log.d((String)"ImageHeaderParser", (String)("Unable to read segment data, type: " + s + ", length: " + n + ", actually read: " + n2));
        return null;
    }

    private static boolean handles(int n) {
        return (n & 0xFFD8) == 65496 || n == 19789 || n == 18761;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int parseExifSegment(RandomAccessReader randomAccessReader) {
        ByteOrder byteOrder;
        int n = "Exif\u0000\u0000".length();
        int n2 = randomAccessReader.getInt16(n);
        if (n2 == 19789) {
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else if (n2 == 18761) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else {
            if (Log.isLoggable((String)"ImageHeaderParser", (int)3)) {
                Log.d((String)"ImageHeaderParser", (String)("Unknown endianness = " + n2));
            }
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        randomAccessReader.order(byteOrder);
        n2 = randomAccessReader.getInt32(n + 4) + n;
        short s = randomAccessReader.getInt16(n2);
        n = 0;
        while (n < s) {
            int n3 = ImageHeaderParser.calcTagOffset(n2, n);
            short s2 = randomAccessReader.getInt16(n3);
            if (s2 == 274) {
                short s3 = randomAccessReader.getInt16(n3 + 2);
                if (s3 < 1 || s3 > 12) {
                    if (Log.isLoggable((String)"ImageHeaderParser", (int)3)) {
                        Log.d((String)"ImageHeaderParser", (String)("Got invalid format code=" + s3));
                    }
                } else {
                    int n4 = randomAccessReader.getInt32(n3 + 4);
                    if (n4 < 0) {
                        if (Log.isLoggable((String)"ImageHeaderParser", (int)3)) {
                            Log.d((String)"ImageHeaderParser", (String)"Negative tiff component count");
                        }
                    } else {
                        if (Log.isLoggable((String)"ImageHeaderParser", (int)3)) {
                            Log.d((String)"ImageHeaderParser", (String)("Got tagIndex=" + n + " tagType=" + s2 + " formatCode=" + s3 + " componentCount=" + n4));
                        }
                        if ((n4 += BYTES_PER_FORMAT[s3]) > 4) {
                            if (Log.isLoggable((String)"ImageHeaderParser", (int)3)) {
                                Log.d((String)"ImageHeaderParser", (String)("Got byte count > 4, not orientation, continuing, formatCode=" + s3));
                            }
                        } else if ((n3 += 8) < 0 || n3 > randomAccessReader.length()) {
                            if (Log.isLoggable((String)"ImageHeaderParser", (int)3)) {
                                Log.d((String)"ImageHeaderParser", (String)("Illegal tagValueOffset=" + n3 + " tagType=" + s2));
                            }
                        } else {
                            if (n4 >= 0 && n3 + n4 <= randomAccessReader.length()) {
                                return randomAccessReader.getInt16(n3);
                            }
                            if (Log.isLoggable((String)"ImageHeaderParser", (int)3)) {
                                Log.d((String)"ImageHeaderParser", (String)("Illegal number of bytes for TI tag data tagType=" + s2));
                            }
                        }
                    }
                }
            }
            ++n;
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public int getOrientation() throws IOException {
        if (!ImageHeaderParser.handles(this.streamReader.getUInt16())) {
            return -1;
        }
        var4_1 = this.getExifSegment();
        var1_2 = var4_1 != null && var4_1.length > ImageHeaderParser.JPEG_EXIF_SEGMENT_PREAMBLE_BYTES.length;
        var3_3 = var1_2;
        if (!var1_2) ** GOTO lbl13
        var2_4 = 0;
        do {
            block6: {
                block5: {
                    var3_3 = var1_2;
                    if (var2_4 >= ImageHeaderParser.JPEG_EXIF_SEGMENT_PREAMBLE_BYTES.length) break block5;
                    if (var4_1[var2_4] == ImageHeaderParser.JPEG_EXIF_SEGMENT_PREAMBLE_BYTES[var2_4]) break block6;
                    var3_3 = false;
                }
                if (var3_3 == false) return -1;
                return ImageHeaderParser.parseExifSegment(new RandomAccessReader(var4_1));
            }
            ++var2_4;
        } while (true);
    }

    public ImageType getType() throws IOException {
        int n = this.streamReader.getUInt16();
        if (n == 65496) {
            return ImageType.JPEG;
        }
        if ((n = n << 16 & 0xFFFF0000 | this.streamReader.getUInt16() & 0xFFFF) == -1991225785) {
            this.streamReader.skip(21L);
            if (this.streamReader.getByte() >= 3) {
                return ImageType.PNG_A;
            }
            return ImageType.PNG;
        }
        if (n >> 8 == 4671814) {
            return ImageType.GIF;
        }
        return ImageType.UNKNOWN;
    }

    public boolean hasAlpha() throws IOException {
        return this.getType().hasAlpha();
    }

    public static enum ImageType {
        GIF(true),
        JPEG(false),
        PNG_A(true),
        PNG(false),
        UNKNOWN(false);

        private final boolean hasAlpha;

        private ImageType(boolean bl) {
            this.hasAlpha = bl;
        }

        public boolean hasAlpha() {
            return this.hasAlpha;
        }
    }

    private static class RandomAccessReader {
        private final ByteBuffer data;

        public RandomAccessReader(byte[] arrby) {
            this.data = ByteBuffer.wrap(arrby);
            this.data.order(ByteOrder.BIG_ENDIAN);
        }

        public short getInt16(int n) {
            return this.data.getShort(n);
        }

        public int getInt32(int n) {
            return this.data.getInt(n);
        }

        public int length() {
            return this.data.array().length;
        }

        public void order(ByteOrder byteOrder) {
            this.data.order(byteOrder);
        }
    }

    private static class StreamReader {
        private final InputStream is;

        public StreamReader(InputStream inputStream) {
            this.is = inputStream;
        }

        public int getByte() throws IOException {
            return this.is.read();
        }

        public int getUInt16() throws IOException {
            return this.is.read() << 8 & 0xFF00 | this.is.read() & 0xFF;
        }

        public short getUInt8() throws IOException {
            return (short)(this.is.read() & 0xFF);
        }

        public int read(byte[] arrby) throws IOException {
            int n;
            int n2;
            for (n2 = arrby.length; n2 > 0 && (n = this.is.read(arrby, arrby.length - n2, n2)) != -1; n2 -= n) {
            }
            return arrby.length - n2;
        }

        public long skip(long l) throws IOException {
            if (l < 0L) {
                return 0L;
            }
            long l2 = l;
            do {
                block7: {
                    block6: {
                        if (l2 <= 0L) break block6;
                        long l3 = this.is.skip(l2);
                        if (l3 > 0L) {
                            l2 -= l3;
                            continue;
                        }
                        if (this.is.read() != -1) break block7;
                    }
                    return l - l2;
                }
                --l2;
            } while (true);
        }
    }

}


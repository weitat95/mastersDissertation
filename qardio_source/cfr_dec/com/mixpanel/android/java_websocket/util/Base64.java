/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.mixpanel.android.java_websocket.util;

import android.annotation.SuppressLint;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

@SuppressLint(value={"Assert"})
public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final byte[] _ORDERED_ALPHABET;
    private static final byte[] _ORDERED_DECODABET;
    private static final byte[] _STANDARD_ALPHABET;
    private static final byte[] _STANDARD_DECODABET;
    private static final byte[] _URL_SAFE_ALPHABET;
    private static final byte[] _URL_SAFE_DECODABET;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Base64.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        _STANDARD_ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        _STANDARD_DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
        _URL_SAFE_ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        _URL_SAFE_DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
        _ORDERED_ALPHABET = new byte[]{45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
        _ORDERED_DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    }

    private Base64() {
    }

    private static int decode4to3(byte[] arrby, int n, byte[] arrby2, int n2, int n3) {
        if (arrby == null) {
            throw new NullPointerException("Source array was null.");
        }
        if (arrby2 == null) {
            throw new NullPointerException("Destination array was null.");
        }
        if (n < 0 || n + 3 >= arrby.length) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", arrby.length, n));
        }
        if (n2 < 0 || n2 + 2 >= arrby2.length) {
            throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", arrby2.length, n2));
        }
        byte[] arrby3 = Base64.getDecodabet(n3);
        if (arrby[n + 2] == 61) {
            arrby2[n2] = (byte)(((arrby3[arrby[n]] & 0xFF) << 18 | (arrby3[arrby[n + 1]] & 0xFF) << 12) >>> 16);
            return 1;
        }
        if (arrby[n + 3] == 61) {
            n = (arrby3[arrby[n]] & 0xFF) << 18 | (arrby3[arrby[n + 1]] & 0xFF) << 12 | (arrby3[arrby[n + 2]] & 0xFF) << 6;
            arrby2[n2] = (byte)(n >>> 16);
            arrby2[n2 + 1] = (byte)(n >>> 8);
            return 2;
        }
        n = (arrby3[arrby[n]] & 0xFF) << 18 | (arrby3[arrby[n + 1]] & 0xFF) << 12 | (arrby3[arrby[n + 2]] & 0xFF) << 6 | arrby3[arrby[n + 3]] & 0xFF;
        arrby2[n2] = (byte)(n >> 16);
        arrby2[n2 + 1] = (byte)(n >> 8);
        arrby2[n2 + 2] = (byte)n;
        return 3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static byte[] encode3to4(byte[] arrby, int n, int n2, byte[] arrby2, int n3, int n4) {
        int n5 = 0;
        byte[] arrby3 = Base64.getAlphabet(n4);
        n4 = n2 > 0 ? arrby[n] << 24 >>> 8 : 0;
        int n6 = n2 > 1 ? arrby[n + 1] << 24 >>> 16 : 0;
        if (n2 > 2) {
            n5 = arrby[n + 2] << 24 >>> 24;
        }
        n = n6 | n4 | n5;
        switch (n2) {
            default: {
                return arrby2;
            }
            case 3: {
                arrby2[n3] = arrby3[n >>> 18];
                arrby2[n3 + 1] = arrby3[n >>> 12 & 0x3F];
                arrby2[n3 + 2] = arrby3[n >>> 6 & 0x3F];
                arrby2[n3 + 3] = arrby3[n & 0x3F];
                return arrby2;
            }
            case 2: {
                arrby2[n3] = arrby3[n >>> 18];
                arrby2[n3 + 1] = arrby3[n >>> 12 & 0x3F];
                arrby2[n3 + 2] = arrby3[n >>> 6 & 0x3F];
                arrby2[n3 + 3] = 61;
                return arrby2;
            }
            case 1: 
        }
        arrby2[n3] = arrby3[n >>> 18];
        arrby2[n3 + 1] = arrby3[n >>> 12 & 0x3F];
        arrby2[n3 + 2] = 61;
        arrby2[n3 + 3] = 61;
        return arrby2;
    }

    private static byte[] encode3to4(byte[] arrby, byte[] arrby2, int n, int n2) {
        Base64.encode3to4(arrby2, 0, n, arrby, 0, n2);
        return arrby;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String encodeBytes(byte[] object) {
        void var0_2;
        block3: {
            Object var1_4 = null;
            try {
                String string2 = Base64.encodeBytes(object, 0, ((byte[])object).length, 0);
            }
            catch (IOException iOException) {
                Object var0_3 = var1_4;
                if ($assertionsDisabled) break block3;
                throw new AssertionError((Object)iOException.getMessage());
            }
        }
        if (!$assertionsDisabled && var0_2 == null) {
            throw new AssertionError();
        }
        return var0_2;
    }

    public static String encodeBytes(byte[] arrby, int n, int n2, int n3) throws IOException {
        arrby = Base64.encodeBytesToBytes(arrby, n, n2, n3);
        try {
            String string2 = new String(arrby, "US-ASCII");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return new String(arrby);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static byte[] encodeBytesToBytes(byte[] var0, int var1_9, int var2_10, int var3_11) throws IOException {
        if (var0 == null) {
            throw new NullPointerException("Cannot serialize a null array.");
        }
        if (var1_9 < 0) {
            throw new IllegalArgumentException("Cannot have negative offset: " + var1_9);
        }
        if (var2_10 < 0) {
            throw new IllegalArgumentException("Cannot have length offset: " + var2_10);
        }
        if (var1_9 + var2_10 > ((Object)var0).length) {
            throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", new Object[]{var1_9, var2_10, ((Object)var0).length}));
        }
        if ((var3_11 & 2) == 0) ** GOTO lbl35
        var11_12 = null;
        var18_14 = null;
        var13_15 = null;
        var15_17 = null;
        var14_18 = null;
        var12_19 = null;
        var17_22 = null;
        var16_23 = null;
        var10_24 = new ByteArrayOutputStream();
        var11_12 = new OutputStream((java.io.OutputStream)var10_24, var3_11 | 1);
        var12_19 = new GZIPOutputStream((java.io.OutputStream)var11_12);
        var12_19.write((byte[])var0, var1_9, var2_10);
        var12_19.close();
        var12_19.close();
        ** GOTO lbl69
        catch (IOException var10_25) {
            block35: {
                var13_15 = var14_18;
                var0 = var18_14;
                var11_12 = var16_23;
                break block35;
lbl35:
                // 1 sources
                var6_29 = (var3_11 & 8) != 0;
                var5_30 = var2_10 / 3;
                var4_31 = var2_10 % 3 > 0 ? 4 : 0;
                var4_31 = var5_30 = var5_30 * 4 + var4_31;
                if (var6_29) {
                    var4_31 = var5_30 + var5_30 / 76;
                }
                var10_26 = new byte[var4_31];
                var4_31 = 0;
                var5_30 = 0;
                for (var7_32 = 0; var7_32 < var2_10 - 2; var7_32 += 3) {
                    Base64.encode3to4((byte[])var0, var7_32 + var1_9, 3, var10_26, var4_31, var3_11);
                    var9_34 = var5_30 + 4;
                    var8_33 = var4_31;
                    var5_30 = var9_34;
                    if (var6_29) {
                        var8_33 = var4_31;
                        var5_30 = var9_34;
                        if (var9_34 >= 76) {
                            var10_26[var4_31 + 4] = 10;
                            var8_33 = var4_31 + 1;
                            var5_30 = 0;
                        }
                    }
                    var4_31 = var8_33 + 4;
                }
                var5_30 = var4_31;
                if (var7_32 < var2_10) {
                    Base64.encode3to4((byte[])var0, var7_32 + var1_9, var2_10 - var7_32, var10_26, var4_31, var3_11);
                    var5_30 = var4_31 + 4;
                }
                if (var5_30 > var10_26.length - 1) return var10_26;
                var0 = new byte[var5_30];
                System.arraycopy(var10_26, 0, var0, 0, var5_30);
                return var0;
                catch (Exception var0_3) {}
lbl69:
                // 2 sources
                try {
                    var11_12.close();
                }
                catch (Exception var0_4) {}
                try {
                    var10_24.close();
                    return var10_24.toByteArray();
                }
                catch (Exception var0_5) {
                    return var10_24.toByteArray();
                }
                catch (Throwable var0_6) {
                    var11_12 = var10_24;
                    var10_24 = var17_22;
                    var13_15 = var15_17;
                    ** GOTO lbl118
                }
                catch (Throwable var0_7) {
                    var12_19 = var10_24;
                    var10_24 = var11_12;
                    var11_12 = var12_19;
                    var13_15 = var15_17;
                    ** GOTO lbl118
                }
                catch (Throwable var0_8) {
                    var13_15 = var10_24;
                    var10_24 = var11_12;
                    var11_12 = var13_15;
                    var13_15 = var12_19;
                    ** GOTO lbl118
                }
                catch (IOException var11_13) {
                    var0 = var10_24;
                    var10_24 = var11_13;
                    var11_12 = var16_23;
                    var13_15 = var14_18;
                    break block35;
                }
                catch (IOException var12_21) {
                    var0 = var10_24;
                    var10_24 = var12_21;
                    var13_15 = var14_18;
                    break block35;
                }
                catch (IOException var13_16) {
                    var0 = var10_24;
                    var10_24 = var13_16;
                    var13_15 = var12_19;
                }
            }
            var12_19 = var11_12;
            var11_12 = var0;
            try {
                throw var10_24;
            }
            catch (Throwable var0_1) {
                var10_24 = var12_19;
lbl118:
                // 4 sources
                try {
                    var13_15.close();
                }
                catch (Exception var12_20) {}
                try {
                    var10_24.close();
                }
                catch (Exception var10_27) {}
                try {
                    var11_12.close();
                }
                catch (Exception var10_28) {
                    throw var0_2;
                }
                throw var0_2;
            }
        }
    }

    private static final byte[] getAlphabet(int n) {
        if ((n & 0x10) == 16) {
            return _URL_SAFE_ALPHABET;
        }
        if ((n & 0x20) == 32) {
            return _ORDERED_ALPHABET;
        }
        return _STANDARD_ALPHABET;
    }

    private static final byte[] getDecodabet(int n) {
        if ((n & 0x10) == 16) {
            return _URL_SAFE_DECODABET;
        }
        if ((n & 0x20) == 32) {
            return _ORDERED_DECODABET;
        }
        return _STANDARD_DECODABET;
    }

    public static class OutputStream
    extends FilterOutputStream {
        private byte[] b4;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        /*
         * Enabled aggressive block sorting
         */
        public OutputStream(java.io.OutputStream outputStream, int n) {
            boolean bl = true;
            super(outputStream);
            boolean bl2 = (n & 8) != 0;
            this.breakLines = bl2;
            bl2 = (n & 1) != 0 ? bl : false;
            this.encode = bl2;
            int n2 = this.encode ? 3 : 4;
            this.bufferLength = n2;
            this.buffer = new byte[this.bufferLength];
            this.position = 0;
            this.lineLength = 0;
            this.suspendEncoding = false;
            this.b4 = new byte[4];
            this.options = n;
            this.decodabet = Base64.getDecodabet(n);
        }

        @Override
        public void close() throws IOException {
            this.flushBase64();
            super.close();
            this.buffer = null;
            this.out = null;
        }

        public void flushBase64() throws IOException {
            block3: {
                block2: {
                    if (this.position <= 0) break block2;
                    if (!this.encode) break block3;
                    this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
                    this.position = 0;
                }
                return;
            }
            throw new IOException("Base64 input not properly padded.");
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void write(int n) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(n);
                return;
            } else if (this.encode) {
                byte[] arrby = this.buffer;
                int n2 = this.position;
                this.position = n2 + 1;
                arrby[n2] = (byte)n;
                if (this.position < this.bufferLength) return;
                {
                    this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
                    this.lineLength += 4;
                    if (this.breakLines && this.lineLength >= 76) {
                        this.out.write(10);
                        this.lineLength = 0;
                    }
                    this.position = 0;
                    return;
                }
            } else if (this.decodabet[n & 0x7F] > -5) {
                byte[] arrby = this.buffer;
                int n3 = this.position;
                this.position = n3 + 1;
                arrby[n3] = (byte)n;
                if (this.position < this.bufferLength) return;
                {
                    n = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
                    this.out.write(this.b4, 0, n);
                    this.position = 0;
                    return;
                }
            } else {
                if (this.decodabet[n & 0x7F] == -5) return;
                {
                    throw new IOException("Invalid character in Base64 data.");
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(arrby, n, n2);
                return;
            } else {
                for (int i = 0; i < n2; ++i) {
                    this.write(arrby[n + i]);
                }
            }
        }
    }

}


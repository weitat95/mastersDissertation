/*
 * Decompiled with CFR 0.147.
 */
package io.branch.referral;

import java.io.UnsupportedEncodingException;

class Base64 {
    public static byte[] decode(byte[] arrby, int n) {
        return Base64.decode(arrby, 0, arrby.length, n);
    }

    public static byte[] decode(byte[] arrby, int n, int n2, int n3) {
        Decoder decoder = new Decoder(n3, new byte[n2 * 3 / 4]);
        if (!decoder.process(arrby, n, n2, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (decoder.op == decoder.output.length) {
            return decoder.output;
        }
        arrby = new byte[decoder.op];
        System.arraycopy(decoder.output, 0, arrby, 0, decoder.op);
        return arrby;
    }

    public static byte[] encode(byte[] arrby, int n) {
        return Base64.encode(arrby, 0, arrby.length, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] encode(byte[] arrby, int n, int n2, int n3) {
        Encoder encoder = new Encoder(n3, null);
        int n4 = n2 / 3 * 4;
        if (encoder.do_padding) {
            n3 = n4;
            if (n2 % 3 > 0) {
                n3 = n4 + 4;
            }
        } else {
            n3 = n4;
            switch (n2 % 3) {
                case 0: {
                    break;
                }
                default: {
                    n3 = n4;
                    break;
                }
                case 1: {
                    n3 = n4 + 2;
                    break;
                }
                case 2: {
                    n3 = n4 + 3;
                }
            }
        }
        n4 = n3;
        if (encoder.do_newline) {
            n4 = n3;
            if (n2 > 0) {
                int n5 = (n2 - 1) / 57;
                n4 = encoder.do_cr ? 2 : 1;
                n4 = n3 + n4 * (n5 + 1);
            }
        }
        encoder.output = new byte[n4];
        encoder.process(arrby, n, n2, true);
        return encoder.output;
    }

    public static String encodeToString(byte[] object, int n) {
        try {
            object = new String(Base64.encode(object, n), "US-ASCII");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError(unsupportedEncodingException);
        }
    }

    static abstract class Coder {
        public int op;
        public byte[] output;

        Coder() {
        }
    }

    static class Decoder
    extends Coder {
        private static final int[] DECODE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private final int[] alphabet;
        private int state;
        private int value;

        /*
         * Enabled aggressive block sorting
         */
        public Decoder(int n, byte[] arrby) {
            this.output = arrby;
            arrby = (n & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.alphabet = arrby;
            this.state = 0;
            this.value = 0;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public boolean process(byte[] var1_1, int var2_2, int var3_3, boolean var4_4) {
            block28: {
                if (this.state == 6) {
                    return false;
                }
                var6_5 = var2_2;
                var11_6 = var3_3 + var2_2;
                var9_7 = this.state;
                var3_3 = this.value;
                var5_8 = 0;
                var12_9 = this.output;
                var13_10 = this.alphabet;
                while (var6_5 < var11_6) {
                    var7_11 = var5_8;
                    var10_13 = var6_5;
                    var8_12 = var3_3;
                    if (var9_7 == 0) {
                        var2_2 = var3_3;
                        while (var6_5 + 4 <= var11_6) {
                            var2_2 = var3_3 = var13_10[var1_1[var6_5] & 255] << 18 | var13_10[var1_1[var6_5 + 1] & 255] << 12 | var13_10[var1_1[var6_5 + 2] & 255] << 6 | var13_10[var1_1[var6_5 + 3] & 255];
                            if (var3_3 < 0) break;
                            var12_9[var5_8 + 2] = (byte)var3_3;
                            var12_9[var5_8 + 1] = (byte)(var3_3 >> 8);
                            var12_9[var5_8] = (byte)(var3_3 >> 16);
                            var5_8 += 3;
                            var6_5 += 4;
                            var2_2 = var3_3;
                        }
                        var7_11 = var5_8;
                        var10_13 = var6_5;
                        var8_12 = var2_2;
                        if (var6_5 >= var11_6) {
                            var3_3 = var2_2;
                            var2_2 = var5_8;
                            break block28;
                        }
                    }
                    var6_5 = var13_10[var1_1[var10_13] & 255];
                    switch (var9_7) {
                        default: {
                            var3_3 = var8_12;
                            var2_2 = var9_7;
                            var5_8 = var7_11;
                            break;
                        }
                        case 0: {
                            if (var6_5 >= 0) {
                                var3_3 = var6_5;
                                var2_2 = var9_7 + 1;
                                var5_8 = var7_11;
                                break;
                            }
                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) break;
                            this.state = 6;
                            return false;
                        }
                        case 1: {
                            if (var6_5 >= 0) {
                                var3_3 = var8_12 << 6 | var6_5;
                                var2_2 = var9_7 + 1;
                                var5_8 = var7_11;
                                break;
                            }
                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) break;
                            this.state = 6;
                            return false;
                        }
                        case 2: {
                            if (var6_5 >= 0) {
                                var3_3 = var8_12 << 6 | var6_5;
                                var2_2 = var9_7 + 1;
                                var5_8 = var7_11;
                                break;
                            }
                            if (var6_5 == -2) {
                                var12_9[var7_11] = (byte)(var8_12 >> 4);
                                var2_2 = 4;
                                var5_8 = var7_11 + 1;
                                var3_3 = var8_12;
                                break;
                            }
                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) break;
                            this.state = 6;
                            return false;
                        }
                        case 3: {
                            if (var6_5 >= 0) {
                                var3_3 = var8_12 << 6 | var6_5;
                                var12_9[var7_11 + 2] = (byte)var3_3;
                                var12_9[var7_11 + 1] = (byte)(var3_3 >> 8);
                                var12_9[var7_11] = (byte)(var3_3 >> 16);
                                var5_8 = var7_11 + 3;
                                var2_2 = 0;
                                break;
                            }
                            if (var6_5 == -2) {
                                var12_9[var7_11 + 1] = (byte)(var8_12 >> 2);
                                var12_9[var7_11] = (byte)(var8_12 >> 10);
                                var5_8 = var7_11 + 2;
                                var2_2 = 5;
                                var3_3 = var8_12;
                                break;
                            }
                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) break;
                            this.state = 6;
                            return false;
                        }
                        case 4: {
                            if (var6_5 == -2) {
                                var2_2 = var9_7 + 1;
                                var5_8 = var7_11;
                                var3_3 = var8_12;
                                break;
                            }
                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) break;
                            this.state = 6;
                            return false;
                        }
                        case 5: {
                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) break;
                            this.state = 6;
                            return false;
                        }
                    }
                    var6_5 = var10_13 + 1;
                    var9_7 = var2_2;
                }
                var2_2 = var5_8;
            }
            if (!var4_4) {
                this.state = var9_7;
                this.value = var3_3;
                this.op = var2_2;
                return true;
            }
            switch (var9_7) {
                case 0: {
                    ** break;
                }
                case 1: {
                    this.state = 6;
                    return false;
                }
                case 2: {
                    var5_8 = var2_2 + 1;
                    var12_9[var2_2] = (byte)(var3_3 >> 4);
                    var2_2 = var5_8;
                    ** break;
                }
                case 3: {
                    var5_8 = var2_2 + 1;
                    var12_9[var2_2] = (byte)(var3_3 >> 10);
                    var12_9[var5_8] = (byte)(var3_3 >> 2);
                    var2_2 = var5_8 + 1;
                }
lbl149:
                // 4 sources
                default: {
                    this.state = var9_7;
                    this.op = var2_2;
                    return true;
                }
                case 4: 
            }
            this.state = 6;
            return false;
        }
    }

    static class Encoder
    extends Coder {
        private static final byte[] ENCODE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] ENCODE_WEBSAFE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        /*
         * Enabled aggressive block sorting
         */
        public Encoder(int n, byte[] arrby) {
            boolean bl = true;
            this.output = arrby;
            boolean bl2 = (n & 1) == 0;
            this.do_padding = bl2;
            bl2 = (n & 2) == 0;
            this.do_newline = bl2;
            bl2 = (n & 4) != 0 ? bl : false;
            this.do_cr = bl2;
            arrby = (n & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.alphabet = arrby;
            this.tail = new byte[2];
            this.tailLen = 0;
            n = this.do_newline ? 19 : -1;
            this.count = n;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public boolean process(byte[] var1_1, int var2_2, int var3_3, boolean var4_4) {
            var11_5 = this.alphabet;
            var12_6 = this.output;
            var7_7 = 0;
            var9_8 = this.count;
            var5_9 = var2_2;
            var10_10 = var3_3 + var2_2;
            var3_3 = -1;
            var2_2 = var5_9;
            var6_11 = var3_3;
            switch (this.tailLen) {
                default: {
                    var6_11 = var3_3;
                    var2_2 = var5_9;
                    break;
                }
                case 1: {
                    var2_2 = var5_9;
                    var6_11 = var3_3;
                    if (var5_9 + 2 <= var10_10) {
                        var3_3 = this.tail[0];
                        var6_11 = var5_9 + 1;
                        var5_9 = var1_1[var5_9];
                        var2_2 = var6_11 + 1;
                        var6_11 = (var3_3 & 255) << 16 | (var5_9 & 255) << 8 | var1_1[var6_11] & 255;
                        this.tailLen = 0;
                        break;
                    }
                    ** GOTO lbl34
                }
                case 2: {
                    var2_2 = var5_9;
                    var6_11 = var3_3;
                    if (var5_9 + 1 <= var10_10) {
                        var6_11 = (this.tail[0] & 255) << 16 | (this.tail[1] & 255) << 8 | var1_1[var5_9] & 255;
                        this.tailLen = 0;
                        var2_2 = var5_9 + 1;
                    }
                }
lbl34:
                // 5 sources
                case 0: 
            }
            var3_3 = var9_8;
            var5_9 = var7_7;
            var7_7 = var2_2;
            if (var6_11 == -1) ** GOTO lbl180
            var5_9 = 0 + 1;
            var12_6[0] = var11_5[var6_11 >> 18 & 63];
            var3_3 = var5_9 + 1;
            var12_6[var5_9] = var11_5[var6_11 >> 12 & 63];
            var5_9 = var3_3 + 1;
            var12_6[var3_3] = var11_5[var6_11 >> 6 & 63];
            var8_12 = var5_9 + 1;
            var12_6[var5_9] = var11_5[var6_11 & 63];
            var3_3 = var6_11 = var9_8 - 1;
            var5_9 = var8_12;
            var7_7 = var2_2;
            if (var6_11 != 0) ** GOTO lbl180
            var3_3 = var8_12;
            if (this.do_cr) {
                var12_6[var8_12] = 13;
                var3_3 = var8_12 + 1;
            }
            var5_9 = var3_3 + 1;
            var12_6[var3_3] = 10;
            var6_11 = 19;
            var3_3 = var2_2;
            var2_2 = var5_9;
            var5_9 = var6_11;
            do {
                if (var3_3 + 3 <= var10_10) {
                    var6_11 = (var1_1[var3_3] & 255) << 16 | (var1_1[var3_3 + 1] & 255) << 8 | var1_1[var3_3 + 2] & 255;
                    var12_6[var2_2] = var11_5[var6_11 >> 18 & 63];
                    var12_6[var2_2 + 1] = var11_5[var6_11 >> 12 & 63];
                    var12_6[var2_2 + 2] = var11_5[var6_11 >> 6 & 63];
                    var12_6[var2_2 + 3] = var11_5[var6_11 & 63];
                    var6_11 = var3_3 + 3;
                    var3_3 = var8_12 = var5_9 - 1;
                    var5_9 = var2_2 += 4;
                    var7_7 = var6_11;
                    if (var8_12 == 0) {
                        var3_3 = var2_2;
                        if (this.do_cr) {
                            var12_6[var2_2] = 13;
                            var3_3 = var2_2 + 1;
                        }
                        var2_2 = var3_3 + 1;
                        var12_6[var3_3] = 10;
                        var5_9 = 19;
                        var3_3 = var6_11;
                        continue;
                    }
                } else {
                    if (var4_4) {
                        if (var3_3 - this.tailLen == var10_10 - 1) {
                            var6_11 = 0;
                            if (this.tailLen > 0) {
                                var6_11 = this.tail[0];
                                var3_3 = 0 + 1;
                            } else {
                                var7_7 = var1_1[var3_3];
                                var3_3 = var6_11;
                                var6_11 = var7_7;
                            }
                            var6_11 = (var6_11 & 255) << 4;
                            this.tailLen -= var3_3;
                            var3_3 = var2_2 + 1;
                            var12_6[var2_2] = var11_5[var6_11 >> 6 & 63];
                            var2_2 = var3_3 + 1;
                            var12_6[var3_3] = var11_5[var6_11 & 63];
                            var3_3 = var2_2;
                            if (this.do_padding) {
                                var6_11 = var2_2 + 1;
                                var12_6[var2_2] = 61;
                                var3_3 = var6_11 + 1;
                                var12_6[var6_11] = 61;
                            }
                            var2_2 = var3_3;
                            if (this.do_newline) {
                                var2_2 = var3_3;
                                if (this.do_cr) {
                                    var12_6[var3_3] = 13;
                                    var2_2 = var3_3 + 1;
                                }
                                var3_3 = var2_2 + 1;
                                var12_6[var2_2] = 10;
                                var2_2 = var3_3;
                            }
                        } else if (var3_3 - this.tailLen == var10_10 - 2) {
                            var6_11 = 0;
                            if (this.tailLen > 1) {
                                var6_11 = this.tail[0];
                                var8_12 = 0 + 1;
                                var7_7 = var3_3;
                                var3_3 = var8_12;
                            } else {
                                var7_7 = var3_3 + 1;
                                var8_12 = var1_1[var3_3];
                                var3_3 = var6_11;
                                var6_11 = var8_12;
                            }
                            if (this.tailLen > 0) {
                                var7_7 = this.tail[var3_3];
                                ++var3_3;
                            } else {
                                var7_7 = var1_1[var7_7];
                            }
                            var6_11 = (var6_11 & 255) << 10 | (var7_7 & 255) << 2;
                            this.tailLen -= var3_3;
                            var3_3 = var2_2 + 1;
                            var12_6[var2_2] = var11_5[var6_11 >> 12 & 63];
                            var7_7 = var3_3 + 1;
                            var12_6[var3_3] = var11_5[var6_11 >> 6 & 63];
                            var2_2 = var7_7 + 1;
                            var12_6[var7_7] = var11_5[var6_11 & 63];
                            var3_3 = var2_2;
                            if (this.do_padding) {
                                var12_6[var2_2] = 61;
                                var3_3 = var2_2 + 1;
                            }
                            var2_2 = var3_3;
                            if (this.do_newline) {
                                var2_2 = var3_3;
                                if (this.do_cr) {
                                    var12_6[var3_3] = 13;
                                    var2_2 = var3_3 + 1;
                                }
                                var3_3 = var2_2 + 1;
                                var12_6[var2_2] = 10;
                                var2_2 = var3_3;
                            }
                        } else if (this.do_newline && var2_2 > 0 && var5_9 != 19) {
                            if (this.do_cr) {
                                var3_3 = var2_2 + 1;
                                var12_6[var2_2] = 13;
                                var2_2 = var3_3;
                            }
                            var12_6[var2_2] = 10;
                            ++var2_2;
                        }
                    } else if (var3_3 == var10_10 - 1) {
                        var11_5 = this.tail;
                        var6_11 = this.tailLen;
                        this.tailLen = var6_11 + 1;
                        var11_5[var6_11] = var1_1[var3_3];
                    } else if (var3_3 == var10_10 - 2) {
                        var11_5 = this.tail;
                        var6_11 = this.tailLen;
                        this.tailLen = var6_11 + 1;
                        var11_5[var6_11] = var1_1[var3_3];
                        var11_5 = this.tail;
                        var6_11 = this.tailLen;
                        this.tailLen = var6_11 + 1;
                        var11_5[var6_11] = var1_1[var3_3 + 1];
                    }
                    this.op = var2_2;
                    this.count = var5_9;
                    return true;
                }
lbl180:
                // 3 sources
                var2_2 = var5_9;
                var5_9 = var3_3;
                var3_3 = var7_7;
            } while (true);
        }
    }

}


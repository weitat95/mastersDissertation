/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.UnsupportedEncodingException;

final class Base64 {
    private static final byte[] MAP = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] URL_MAP = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] decode(String arrby) {
        int n;
        int n2 = arrby.length();
        do {
            if (n2 <= 0 || (n = arrby.charAt(n2 - 1)) != 61 && n != 10 && n != 13 && n != 32 && n != 9) break;
            --n2;
        } while (true);
        byte[] arrby2 = new byte[(int)((long)n2 * 6L / 8L)];
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        n = 0;
        do {
            int n6;
            block14: {
                int n7;
                block13: {
                    block6: {
                        char c;
                        block12: {
                            block8: {
                                block11: {
                                    block10: {
                                        block9: {
                                            block7: {
                                                if (n5 >= n2) break block6;
                                                c = arrby.charAt(n5);
                                                if (c < 'A' || c > 'Z') break block7;
                                                n6 = c - 65;
                                                break block8;
                                            }
                                            if (c < 'a' || c > 'z') break block9;
                                            n6 = c - 71;
                                            break block8;
                                        }
                                        if (c < '0' || c > '9') break block10;
                                        n6 = c + 4;
                                        break block8;
                                    }
                                    if (c != '+' && c != '-') break block11;
                                    n6 = 62;
                                    break block8;
                                }
                                if (c != '/' && c != '_') break block12;
                                n6 = 63;
                            }
                            n4 = n4 << 6 | (byte)n6;
                            n7 = ++n3;
                            n6 = n4;
                            if (n3 % 4 != 0) break block13;
                            n6 = n + 1;
                            arrby2[n] = (byte)(n4 >> 16);
                            n7 = n6 + 1;
                            arrby2[n6] = (byte)(n4 >> 8);
                            n = n7 + 1;
                            arrby2[n7] = (byte)n4;
                            n6 = n4;
                            break block14;
                        }
                        n7 = n3;
                        n6 = n4;
                        if (c == '\n') break block13;
                        n7 = n3;
                        n6 = n4;
                        if (c == '\r') break block13;
                        n7 = n3;
                        n6 = n4;
                        if (c == ' ') break block13;
                        if (c != '\t') return null;
                        n6 = n4;
                        break block14;
                    }
                    if ((n3 %= 4) == 1) {
                        return null;
                    }
                    if (n3 == 2) {
                        n6 = n + '\u0001';
                        arrby2[n] = (byte)(n4 << 12 >> 16);
                        n = n6;
                    } else {
                        n6 = n;
                        if (n3 == 3) {
                            n3 = n + 1;
                            arrby2[n] = (byte)((n4 <<= 6) >> 16);
                            n6 = n3 + 1;
                            arrby2[n3] = (byte)(n4 >> 8);
                        }
                        n = n6;
                    }
                    arrby = arrby2;
                    if (n == arrby2.length) return arrby;
                    arrby = new byte[n];
                    System.arraycopy(arrby2, 0, arrby, 0, n);
                    return arrby;
                }
                n3 = n7;
            }
            ++n5;
            n4 = n6;
        } while (true);
    }

    public static String encode(byte[] arrby) {
        return Base64.encode(arrby, MAP);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String encode(byte[] object, byte[] arrby) {
        void var1_3;
        int n;
        byte[] arrby2 = new byte[(((byte[])object).length + 2) / 3 * 4];
        int n2 = ((byte[])object).length - ((byte[])object).length % 3;
        int n3 = 0;
        for (n = 0; n < n2; n += 3) {
            int n4 = n3 + 1;
            arrby2[n3] = var1_3[(object[n] & 0xFF) >> 2];
            n3 = n4 + 1;
            arrby2[n4] = var1_3[(object[n] & 3) << 4 | (object[n + 1] & 0xFF) >> 4];
            n4 = n3 + 1;
            arrby2[n3] = var1_3[(object[n + 1] & 0xF) << 2 | (object[n + 2] & 0xFF) >> 6];
            n3 = n4 + 1;
            arrby2[n4] = var1_3[object[n + 2] & 0x3F];
        }
        switch (((byte[])object).length % 3) {
            case 1: {
                n = n3 + 1;
                arrby2[n3] = var1_3[(object[n2] & 0xFF) >> 2];
                n3 = n + 1;
                arrby2[n] = var1_3[(object[n2] & 3) << 4];
                n = n3 + 1;
                arrby2[n3] = 61;
                arrby2[n] = 61;
            }
            default: {
                break;
            }
            case 2: {
                n = n3 + 1;
                arrby2[n3] = var1_3[(object[n2] & 0xFF) >> 2];
                n3 = n + 1;
                arrby2[n] = var1_3[(object[n2] & 3) << 4 | (object[n2 + 1] & 0xFF) >> 4];
                n = n3 + 1;
                arrby2[n3] = var1_3[(object[n2 + 1] & 0xF) << 2];
                n3 = n + 1;
                arrby2[n] = 61;
            }
        }
        try {
            return new String(arrby2, "US-ASCII");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError(unsupportedEncodingException);
        }
    }
}


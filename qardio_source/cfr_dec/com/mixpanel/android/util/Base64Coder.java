/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.util;

public class Base64Coder {
    private static char[] map1 = new char[64];
    private static byte[] map2;

    static {
        int n = 65;
        int n2 = 0;
        while (n <= 90) {
            Base64Coder.map1[n2] = n;
            n = (char)(n + 1);
            ++n2;
        }
        n = 97;
        while (n <= 122) {
            Base64Coder.map1[n2] = n;
            n = (char)(n + 1);
            ++n2;
        }
        n = 48;
        while (n <= 57) {
            Base64Coder.map1[n2] = n;
            n = (char)(n + 1);
            ++n2;
        }
        char[] arrc = map1;
        int n3 = n2 + 1;
        arrc[n2] = 43;
        Base64Coder.map1[n3] = 47;
        map2 = new byte[128];
        for (n2 = 0; n2 < map2.length; ++n2) {
            Base64Coder.map2[n2] = -1;
        }
        for (n2 = 0; n2 < 64; ++n2) {
            Base64Coder.map2[Base64Coder.map1[n2]] = (byte)n2;
        }
    }

    public static char[] encode(byte[] arrby) {
        return Base64Coder.encode(arrby, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static char[] encode(byte[] arrby, int n) {
        int n2 = (n * 4 + 2) / 3;
        char[] arrc = new char[(n + 2) / 3 * 4];
        int n3 = 0;
        int n4 = 0;
        while (n4 < n) {
            int n5;
            int n6;
            int n7 = n4 + 1;
            int n8 = arrby[n4] & 0xFF;
            if (n7 < n) {
                n4 = n7 + 1;
                n7 = arrby[n7] & 0xFF;
            } else {
                n6 = 0;
                n4 = n7;
                n7 = n6;
            }
            if (n4 < n) {
                n6 = n4 + 1;
                n5 = arrby[n4] & 0xFF;
                n4 = n6;
                n6 = n5;
            } else {
                n6 = 0;
            }
            n5 = n3 + 1;
            arrc[n3] = map1[n8 >>> 2];
            n3 = n5 + 1;
            arrc[n5] = map1[(n8 & 3) << 4 | n7 >>> 4];
            int n9 = n3 < n2 ? map1[(n7 & 0xF) << 2 | n6 >>> 6] : 61;
            arrc[n3] = n9;
            n9 = ++n3 < n2 ? map1[n6 & 0x3F] : 61;
            arrc[n3] = n9;
            ++n3;
        }
        return arrc;
    }

    public static String encodeString(String string2) {
        return new String(Base64Coder.encode(string2.getBytes()));
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.util;

public class HexUtil {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] arrby) {
        char[] arrc = new char[arrby.length * 2];
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby[i] & 0xFF;
            arrc[i * 2] = HEX_ARRAY[n >>> 4];
            arrc[i * 2 + 1] = HEX_ARRAY[n & 0xF];
        }
        return new String(arrc);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String intToHex(String object) {
        int n;
        Integer n2 = 0;
        try {
            n = Integer.parseInt((String)object);
        }
        catch (NumberFormatException numberFormatException) {
            object = n2;
            return Integer.toHexString((Integer)object);
        }
        object = n;
        do {
            return Integer.toHexString((Integer)object);
            break;
        } while (true);
    }
}


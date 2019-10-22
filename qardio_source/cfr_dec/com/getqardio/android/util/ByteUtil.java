/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.util;

import java.nio.ByteBuffer;
import java.util.Formatter;

public class ByteUtil {
    public static String bytesToHex(byte[] arrby) {
        Formatter formatter = new Formatter();
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            formatter.format("%02x", arrby[i]);
        }
        return formatter.toString();
    }

    public static byte[] littleEndian(int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(n);
        byte[] arrby = new byte[4];
        for (n = 0; n < 4; ++n) {
            arrby[4 - (n + 1)] = byteBuffer.get(n);
        }
        return arrby;
    }
}


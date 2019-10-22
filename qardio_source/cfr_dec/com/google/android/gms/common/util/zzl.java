/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.util;

public final class zzl {
    /*
     * Enabled aggressive block sorting
     */
    public static String zza(byte[] arrby, int n, int n2, boolean bl) {
        if (arrby == null || arrby.length == 0 || n2 <= 0 || n2 > arrby.length) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder((n2 + 16 - 1) / 16 * 57);
        int n3 = 0;
        int n4 = n2;
        n = 0;
        while (n4 > 0) {
            if (n == 0) {
                if (n2 < 65536) {
                    stringBuilder.append(String.format("%04X:", n3));
                } else {
                    stringBuilder.append(String.format("%08X:", n3));
                }
            } else if (n == 8) {
                stringBuilder.append(" -");
            }
            stringBuilder.append(String.format(" %02X", arrby[n3] & 0xFF));
            if (++n == 16 || --n4 == 0) {
                stringBuilder.append('\n');
                n = 0;
            }
            ++n3;
        }
        return stringBuilder.toString();
    }
}


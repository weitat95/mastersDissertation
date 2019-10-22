/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

public final class zzcaj {
    private static final ThreadLocal<String> zzhfv = new ThreadLocal();

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static String zzho(String var0) {
        var3_1 = 0;
        var7_2 = zzcaj.zzhfv.get();
        var1_3 = var7_2 == null || var7_2.startsWith("com.google") != false ? 1 : 0;
        if (var1_3 != 0) {
            return var0;
        }
        var7_2 = zzcaj.zzhfv.get();
        if (var0 == null) return var0;
        if (var7_2 == null) return var0;
        var8_4 = new byte[var0.length() + var7_2.length()];
        System.arraycopy(var0.getBytes(), 0, var8_4, 0, var0.length());
        System.arraycopy(var7_2.getBytes(), 0, var8_4, var0.length(), var7_2.length());
        var4_5 = var8_4.length;
        var5_6 = (var4_5 & -4) + 0;
        var1_3 = 0;
        for (var2_7 = 0; var2_7 < var5_6; var1_3 ^= (var6_8 >>> 17 | var6_8 << 15) * 461845907, var2_7 += 4) {
            var6_8 = (var8_4[var2_7] & 255 | (var8_4[var2_7 + 1] & 255) << 8 | (var8_4[var2_7 + 2] & 255) << 16 | var8_4[var2_7 + 3] << 24) * -862048943;
            var1_3 = (var1_3 >>> 19 | var1_3 << 13) * 5 - 430675100;
        }
        var2_7 = var3_1;
        switch (var4_5 & 3) {
            case 3: {
                var2_7 = (var8_4[var5_6 + 2] & 255) << 16;
                ** GOTO lbl26
            }
            case 2: {
                var2_7 = 0;
lbl26:
                // 2 sources
                var2_7 = (var8_4[var5_6 + 1] & 255) << 8 | var2_7;
            }
            case 1: {
                var2_7 = (var8_4[var5_6] & 255 | var2_7) * -862048943;
                var1_3 = (var2_7 >>> 17 | var2_7 << 15) * 461845907 ^ var1_3;
            }
        }
        var1_3 ^= var4_5;
        var1_3 = (var1_3 ^ var1_3 >>> 16) * -2048144789;
        var1_3 = (var1_3 ^ var1_3 >>> 13) * -1028477387;
        return Integer.toHexString(var1_3 ^ var1_3 >>> 16);
    }
}


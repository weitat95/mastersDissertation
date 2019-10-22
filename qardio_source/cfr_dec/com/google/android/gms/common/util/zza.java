/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.zzbg;
import java.lang.reflect.Array;
import java.util.Arrays;

public final class zza {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static <T> T[] zza(T[] var0, T ... var1_1) {
        block13: {
            if (var0 /* !! */  == null) {
                return var0_2;
            }
            if (((void)var1_4).length == 0) {
                return Arrays.copyOf(var0 /* !! */ , var0 /* !! */ .length);
            }
            var7_5 = (Object[])Array.newInstance(var1_4.getClass().getComponentType(), var0 /* !! */ .length);
            if (((void)var1_4).length != 1) break block13;
            var6_6 = var0 /* !! */ .length;
            var3_8 = 0;
            var2_10 = 0;
            do {
                var5_14 = var2_10;
                if (var3_8 < var6_6) {
                    var8_15 = var0 /* !! */ [var3_8];
                    if (!zzbg.equal(var1_4[0], var8_15)) {
                        var4_12 = var2_10 + 1;
                        var7_5[var2_10] = var8_15;
                        var2_10 = var4_12;
                    }
                    ++var3_8;
                    continue;
                }
                ** GOTO lbl-1000
                break;
            } while (true);
        }
        var6_7 = var0 /* !! */ .length;
        var4_13 = 0;
        var2_11 = 0;
        do {
            block12: {
                var5_14 = var2_11;
                if (var4_13 < var6_7) {
                    var8_16 = var0 /* !! */ [var4_13];
                    var5_14 = ((void)var1_4).length;
                } else lbl-1000:
                // 2 sources
                {
                    if (var7_5 == null) {
                        return null;
                    }
                    var0_3 = var7_5;
                    if (var5_14 == var7_5.length) return var0_2;
                    return Arrays.copyOf(var7_5, var5_14);
                }
                for (var3_9 = 0; var3_9 < var5_14; ++var3_9) {
                    if (!zzbg.equal(var1_4[var3_9], var8_16)) {
                        continue;
                    }
                    break block12;
                }
                var3_9 = -1;
            }
            if ((var3_9 = var3_9 >= 0 ? 1 : 0) == 0) {
                var3_9 = var2_11 + 1;
                var7_5[var2_11] = var8_16;
                var2_11 = var3_9;
            }
            ++var4_13;
        } while (true);
    }
}


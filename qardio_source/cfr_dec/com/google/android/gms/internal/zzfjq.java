/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import java.nio.charset.Charset;
import java.util.Arrays;

public final class zzfjq {
    private static Charset ISO_8859_1;
    protected static final Charset UTF_8;
    public static final Object zzpnk;

    static {
        UTF_8 = Charset.forName("UTF-8");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        zzpnk = new Object();
    }

    public static boolean equals(float[] arrf, float[] arrf2) {
        if (arrf == null || arrf.length == 0) {
            return arrf2 == null || arrf2.length == 0;
        }
        return Arrays.equals(arrf, arrf2);
    }

    public static boolean equals(int[] arrn, int[] arrn2) {
        if (arrn == null || arrn.length == 0) {
            return arrn2 == null || arrn2.length == 0;
        }
        return Arrays.equals(arrn, arrn2);
    }

    public static boolean equals(long[] arrl, long[] arrl2) {
        if (arrl == null || arrl.length == 0) {
            return arrl2 == null || arrl2.length == 0;
        }
        return Arrays.equals(arrl, arrl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean equals(Object[] arrobject, Object[] arrobject2) {
        boolean bl = false;
        int n = arrobject == null ? 0 : arrobject.length;
        int n2 = arrobject2 == null ? 0 : arrobject2.length;
        int n3 = 0;
        int n4 = 0;
        do {
            if (n4 < n && arrobject[n4] == null) {
                ++n4;
                continue;
            }
            while (n3 < n2 && arrobject2[n3] == null) {
                ++n3;
            }
            boolean bl2 = n4 >= n;
            boolean bl3 = n3 >= n2;
            if (bl2 && bl3) {
                return true;
            }
            boolean bl4 = bl;
            if (bl2 != bl3) return bl4;
            bl4 = bl;
            if (!arrobject[n4].equals(arrobject2[n3])) return bl4;
            ++n3;
            ++n4;
        } while (true);
    }

    public static int hashCode(float[] arrf) {
        if (arrf == null || arrf.length == 0) {
            return 0;
        }
        return Arrays.hashCode(arrf);
    }

    public static int hashCode(int[] arrn) {
        if (arrn == null || arrn.length == 0) {
            return 0;
        }
        return Arrays.hashCode(arrn);
    }

    public static int hashCode(long[] arrl) {
        if (arrl == null || arrl.length == 0) {
            return 0;
        }
        return Arrays.hashCode(arrl);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int hashCode(Object[] arrobject) {
        int n = 0;
        int n2 = arrobject == null ? 0 : arrobject.length;
        int n3 = 0;
        while (n3 < n2) {
            Object object = arrobject[n3];
            int n4 = n;
            if (object != null) {
                n4 = n * 31 + object.hashCode();
            }
            ++n3;
            n = n4;
        }
        return n;
    }

    public static void zza(zzfjm zzfjm2, zzfjm zzfjm3) {
        if (zzfjm2.zzpnc != null) {
            zzfjm3.zzpnc = (zzfjo)zzfjm2.zzpnc.clone();
        }
    }
}


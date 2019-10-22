/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjj;
import java.io.IOException;

public final class zzfjv {
    public static final String[] EMPTY_STRING_ARRAY;
    private static int zzpnl;
    private static int zzpnm;
    private static int zzpnn;
    private static int zzpno;
    public static final int[] zzpnp;
    public static final long[] zzpnq;
    public static final float[] zzpnr;
    public static final double[] zzpns;
    public static final boolean[] zzpnt;
    public static final byte[][] zzpnu;
    public static final byte[] zzpnv;

    static {
        zzpnl = 11;
        zzpnm = 12;
        zzpnn = 16;
        zzpno = 26;
        zzpnp = new int[0];
        zzpnq = new long[0];
        zzpnr = new float[0];
        zzpns = new double[0];
        zzpnt = new boolean[0];
        EMPTY_STRING_ARRAY = new String[0];
        zzpnu = new byte[0][];
        zzpnv = new byte[0];
    }

    public static final int zzb(zzfjj zzfjj2, int n) throws IOException {
        int n2 = 1;
        int n3 = zzfjj2.getPosition();
        zzfjj2.zzkq(n);
        while (zzfjj2.zzcvt() == n) {
            zzfjj2.zzkq(n);
            ++n2;
        }
        zzfjj2.zzam(n3, n);
        return n2;
    }
}


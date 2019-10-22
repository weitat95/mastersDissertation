/*
 * Decompiled with CFR 0.147.
 */
package com.firebase.jobdispatcher;

public final class Constraint {
    static final int[] ALL_CONSTRAINTS = new int[]{2, 1, 4, 8};

    static int compact(int[] arrn) {
        int n = 0;
        if (arrn == null) {
            return 0;
        }
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            n |= arrn[i];
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    static int[] uncompact(int n) {
        int n2;
        int n3;
        int n4 = 0;
        int n5 = 0;
        int[] arrn = ALL_CONSTRAINTS;
        int n6 = arrn.length;
        for (n2 = 0; n2 < n6; n5 += n3, ++n2) {
            n3 = arrn[n2];
            n3 = (n & n3) == n3 ? 1 : 0;
        }
        arrn = new int[n5];
        int[] arrn2 = ALL_CONSTRAINTS;
        n6 = arrn2.length;
        n2 = 0;
        n5 = n4;
        while (n5 < n6) {
            n4 = arrn2[n5];
            if ((n & n4) == n4) {
                n3 = n2 + 1;
                arrn[n2] = n4;
                n2 = n3;
            }
            ++n5;
        }
        return arrn;
    }
}


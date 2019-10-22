/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.internal;

import com.facebook.common.internal.Preconditions;

public class Ints {
    /*
     * Enabled aggressive block sorting
     */
    public static int max(int ... arrn) {
        boolean bl = arrn.length > 0;
        Preconditions.checkArgument(bl);
        int n = arrn[0];
        int n2 = 1;
        while (n2 < arrn.length) {
            int n3 = n;
            if (arrn[n2] > n) {
                n3 = arrn[n2];
            }
            ++n2;
            n = n3;
        }
        return n;
    }
}


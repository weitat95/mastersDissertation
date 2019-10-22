/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import java.util.List;

public final class zzbuu {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <T> int zza(T t, List<T> list) {
        int n;
        if (t == null) {
            return -1;
        }
        int n2 = n = list.indexOf(t);
        if (n >= 0) return n2;
        list.add(t);
        return list.size() - 1;
    }
}


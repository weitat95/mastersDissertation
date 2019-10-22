/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbva;

public final class zzbvb {
    private final double zzhek;
    private final double zzhel;

    private zzbvb(double d, double d2) {
        this.zzhek = d;
        this.zzhel = d2;
    }

    /* synthetic */ zzbvb(double d, double d2, zzbva zzbva2) {
        this(d, d2);
    }

    public final boolean zzf(double d) {
        return d >= this.zzhek && d <= this.zzhel;
    }
}


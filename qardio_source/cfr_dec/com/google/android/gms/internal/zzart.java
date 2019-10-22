/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzaru;

public final class zzart {
    private final zzd zzata;
    private final String zzdrp;
    private final long zzdxz;
    private final int zzdya;
    private double zzdyb;
    private long zzdyc;
    private final Object zzdyd = new Object();

    private zzart(int n, long l, String string2, zzd zzd2) {
        this.zzdya = 60;
        this.zzdyb = this.zzdya;
        this.zzdxz = 2000L;
        this.zzdrp = string2;
        this.zzata = zzd2;
    }

    public zzart(String string2, zzd zzd2) {
        this(60, 2000L, string2, zzd2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean zzzn() {
        Object object = this.zzdyd;
        synchronized (object) {
            double d;
            long l = this.zzata.currentTimeMillis();
            if (this.zzdyb < (double)this.zzdya && (d = (double)(l - this.zzdyc) / (double)this.zzdxz) > 0.0) {
                this.zzdyb = Math.min((double)this.zzdya, d + this.zzdyb);
            }
            this.zzdyc = l;
            if (this.zzdyb >= 1.0) {
                this.zzdyb -= 1.0;
                return true;
            }
            String string2 = this.zzdrp;
            zzaru.zzcu(new StringBuilder(String.valueOf(string2).length() + 34).append("Excessive ").append(string2).append(" detected; call ignored.").toString());
            return false;
        }
    }
}


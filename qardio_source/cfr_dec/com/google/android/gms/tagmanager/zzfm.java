/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzek;

final class zzfm
implements zzek {
    private final zzd zzata;
    private final long zzdxz;
    private final int zzdya;
    private double zzdyb;
    private final Object zzdyd = new Object();
    private long zzkji;

    public zzfm() {
        this(60, 2000L);
    }

    private zzfm(int n, long l) {
        this.zzdya = 60;
        this.zzdyb = this.zzdya;
        this.zzdxz = 2000L;
        this.zzata = zzh.zzamg();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final boolean zzzn() {
        Object object = this.zzdyd;
        synchronized (object) {
            double d;
            long l = this.zzata.currentTimeMillis();
            if (this.zzdyb < (double)this.zzdya && (d = (double)(l - this.zzkji) / (double)this.zzdxz) > 0.0) {
                this.zzdyb = Math.min((double)this.zzdya, d + this.zzdyb);
            }
            this.zzkji = l;
            if (this.zzdyb >= 1.0) {
                this.zzdyb -= 1.0;
                return true;
            }
            zzdj.zzcu("No more tokens available.");
            return false;
        }
    }
}


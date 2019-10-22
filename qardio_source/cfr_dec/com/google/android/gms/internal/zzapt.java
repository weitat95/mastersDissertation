/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzapr;

final class zzapt
implements Runnable {
    private /* synthetic */ zzapr zzdsu;
    private /* synthetic */ boolean zzdsv;

    zzapt(zzapr zzapr2, boolean bl) {
        this.zzdsu = zzapr2;
        this.zzdsv = bl;
    }

    @Override
    public final void run() {
        zzapr.zza(this.zzdsu).zzyf();
    }
}


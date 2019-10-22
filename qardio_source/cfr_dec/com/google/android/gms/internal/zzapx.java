/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzarj;

final class zzapx
implements Runnable {
    private /* synthetic */ zzapr zzdsu;
    private /* synthetic */ zzarj zzdsz;

    zzapx(zzapr zzapr2, zzarj zzarj2) {
        this.zzdsu = zzapr2;
        this.zzdsz = zzarj2;
    }

    @Override
    public final void run() {
        zzapr.zza(this.zzdsu).zzb(this.zzdsz);
    }
}


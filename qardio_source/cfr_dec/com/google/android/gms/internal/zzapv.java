/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzarq;

final class zzapv
implements Runnable {
    private /* synthetic */ zzapr zzdsu;
    private /* synthetic */ zzarq zzdsy;

    zzapv(zzapr zzapr2, zzarq zzarq2) {
        this.zzdsu = zzapr2;
        this.zzdsy = zzarq2;
    }

    @Override
    public final void run() {
        zzapr.zza(this.zzdsu).zza(this.zzdsy);
    }
}


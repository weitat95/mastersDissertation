/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzapr;

final class zzapu
implements Runnable {
    private /* synthetic */ zzapr zzdsu;
    private /* synthetic */ String zzdsw;
    private /* synthetic */ Runnable zzdsx;

    zzapu(zzapr zzapr2, String string2, Runnable runnable) {
        this.zzdsu = zzapr2;
        this.zzdsw = string2;
        this.zzdsx = runnable;
    }

    @Override
    public final void run() {
        zzapr.zza(this.zzdsu).zzec(this.zzdsw);
        if (this.zzdsx != null) {
            this.zzdsx.run();
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckg;

final class zzcka
implements Runnable {
    private /* synthetic */ zzcjn zzjhc;

    zzcka(zzcjn zzcjn2) {
        this.zzjhc = zzcjn2;
    }

    @Override
    public final void run() {
        zzcjn zzcjn2 = this.zzjhc;
        ((zzcjk)zzcjn2).zzve();
        zzcjn2.zzxf();
        ((zzcjk)zzcjn2).zzawy().zzazi().log("Resetting analytics data (FE)");
        ((zzcjk)zzcjn2).zzawp().resetAnalyticsData();
    }
}


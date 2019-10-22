/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckf;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.measurement.AppMeasurement;

final class zzcke
implements Runnable {
    private /* synthetic */ zzckc zzjhz;
    private /* synthetic */ zzckf zzjia;

    zzcke(zzckc zzckc2, zzckf zzckf2) {
        this.zzjhz = zzckc2;
        this.zzjia = zzckf2;
    }

    @Override
    public final void run() {
        zzckc.zza(this.zzjhz, this.zzjia);
        this.zzjhz.zzjhn = null;
        ((zzcjk)this.zzjhz).zzawp().zza((AppMeasurement.zzb)null);
    }
}


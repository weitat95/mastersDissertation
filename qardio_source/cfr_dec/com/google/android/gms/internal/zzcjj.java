/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcir;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.measurement.AppMeasurement;

final class zzcjj
implements Runnable {
    private /* synthetic */ String zzimf;
    private /* synthetic */ zzcir zzjgo;
    private /* synthetic */ String zzjgu;
    private /* synthetic */ String zzjgv;
    private /* synthetic */ long zzjgw;

    zzcjj(zzcir zzcir2, String string2, String string3, String string4, long l) {
        this.zzjgo = zzcir2;
        this.zzjgu = string2;
        this.zzimf = string3;
        this.zzjgv = string4;
        this.zzjgw = l;
    }

    @Override
    public final void run() {
        if (this.zzjgu == null) {
            zzcir.zza(this.zzjgo).zzawq().zza(this.zzimf, null);
            return;
        }
        AppMeasurement.zzb zzb2 = new AppMeasurement.zzb();
        zzb2.zziwk = this.zzjgv;
        zzb2.zziwl = this.zzjgu;
        zzb2.zziwm = this.zzjgw;
        zzcir.zza(this.zzjgo).zzawq().zza(this.zzimf, zzb2);
    }
}


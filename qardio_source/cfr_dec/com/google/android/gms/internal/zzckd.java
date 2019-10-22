/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckf;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.measurement.AppMeasurement;

final class zzckd
implements Runnable {
    private /* synthetic */ boolean zzjhw;
    private /* synthetic */ AppMeasurement.zzb zzjhx;
    private /* synthetic */ zzckf zzjhy;
    private /* synthetic */ zzckc zzjhz;

    zzckd(zzckc zzckc2, boolean bl, AppMeasurement.zzb zzb2, zzckf zzckf2) {
        this.zzjhz = zzckc2;
        this.zzjhw = bl;
        this.zzjhx = zzb2;
        this.zzjhy = zzckf2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void run() {
        boolean bl;
        if (this.zzjhw && this.zzjhz.zzjhn != null) {
            zzckc.zza(this.zzjhz, this.zzjhz.zzjhn);
        }
        if (bl = this.zzjhx == null || this.zzjhx.zziwm != this.zzjhy.zziwm || !zzclq.zzas(this.zzjhx.zziwl, this.zzjhy.zziwl) || !zzclq.zzas(this.zzjhx.zziwk, this.zzjhy.zziwk)) {
            Bundle bundle = new Bundle();
            zzckc.zza(this.zzjhy, bundle);
            if (this.zzjhx != null) {
                if (this.zzjhx.zziwk != null) {
                    bundle.putString("_pn", this.zzjhx.zziwk);
                }
                bundle.putString("_pc", this.zzjhx.zziwl);
                bundle.putLong("_pi", this.zzjhx.zziwm);
            }
            ((zzcjk)this.zzjhz).zzawm().zzc("auto", "_vs", bundle);
        }
        this.zzjhz.zzjhn = this.zzjhy;
        ((zzcjk)this.zzjhz).zzawp().zza(this.zzjhy);
    }
}


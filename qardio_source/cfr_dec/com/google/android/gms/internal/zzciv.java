/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcir;

final class zzciv
implements Runnable {
    private /* synthetic */ zzcir zzjgo;
    private /* synthetic */ zzcgl zzjgp;

    zzciv(zzcir zzcir2, zzcgl zzcgl2) {
        this.zzjgo = zzcir2;
        this.zzjgp = zzcgl2;
    }

    @Override
    public final void run() {
        zzcir.zza(this.zzjgo).zzbal();
        zzcir.zza(this.zzjgo).zze(this.zzjgp);
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcir;
import com.google.android.gms.internal.zzcln;

final class zzcjg
implements Runnable {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzcir zzjgo;
    private /* synthetic */ zzcln zzjgt;

    zzcjg(zzcir zzcir2, zzcln zzcln2, zzcgi zzcgi2) {
        this.zzjgo = zzcir2;
        this.zzjgt = zzcln2;
        this.zzjgn = zzcgi2;
    }

    @Override
    public final void run() {
        zzcir.zza(this.zzjgo).zzbal();
        zzcir.zza(this.zzjgo).zzb(this.zzjgt, this.zzjgn);
    }
}


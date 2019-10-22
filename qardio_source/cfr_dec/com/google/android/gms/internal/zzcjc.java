/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzcir;

final class zzcjc
implements Runnable {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzcir zzjgo;
    private /* synthetic */ zzcha zzjgs;

    zzcjc(zzcir zzcir2, zzcha zzcha2, zzcgi zzcgi2) {
        this.zzjgo = zzcir2;
        this.zzjgs = zzcha2;
        this.zzjgn = zzcgi2;
    }

    @Override
    public final void run() {
        zzcir.zza(this.zzjgo).zzbal();
        zzcir.zza(this.zzjgo).zzb(this.zzjgs, this.zzjgn);
    }
}


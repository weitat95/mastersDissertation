/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzcir;

final class zzcjd
implements Runnable {
    private /* synthetic */ String zzimf;
    private /* synthetic */ zzcir zzjgo;
    private /* synthetic */ zzcha zzjgs;

    zzcjd(zzcir zzcir2, zzcha zzcha2, String string2) {
        this.zzjgo = zzcir2;
        this.zzjgs = zzcha2;
        this.zzimf = string2;
    }

    @Override
    public final void run() {
        zzcir.zza(this.zzjgo).zzbal();
        zzcir.zza(this.zzjgo).zzb(this.zzjgs, this.zzimf);
    }
}


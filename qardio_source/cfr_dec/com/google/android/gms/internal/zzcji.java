/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcir;

final class zzcji
implements Runnable {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzcir zzjgo;

    zzcji(zzcir zzcir2, zzcgi zzcgi2) {
        this.zzjgo = zzcir2;
        this.zzjgn = zzcgi2;
    }

    @Override
    public final void run() {
        zzcir.zza(this.zzjgo).zzbal();
        zzcir.zza(this.zzjgo).zzf(this.zzjgn);
    }
}

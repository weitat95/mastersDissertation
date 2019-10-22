/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzcku;

final class zzckz
implements Runnable {
    private /* synthetic */ zzcku zzjit;

    zzckz(zzcku zzcku2) {
        this.zzjit = zzcku2;
    }

    @Override
    public final void run() {
        zzckg.zza(this.zzjit.zzjij, null);
        zzckg.zzb(this.zzjit.zzjij);
    }
}


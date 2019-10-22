/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcla;

final class zzcld
implements Runnable {
    private /* synthetic */ zzcim zzjdt;
    private /* synthetic */ Runnable zzjjb;

    zzcld(zzcla zzcla2, zzcim zzcim2, Runnable runnable) {
        this.zzjdt = zzcim2;
        this.zzjjb = runnable;
    }

    @Override
    public final void run() {
        this.zzjdt.zzbal();
        this.zzjdt.zzi(this.zzjjb);
        this.zzjdt.zzbah();
    }
}


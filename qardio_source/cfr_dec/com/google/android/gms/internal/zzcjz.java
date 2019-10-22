/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckg;
import java.util.concurrent.atomic.AtomicReference;

final class zzcjz
implements Runnable {
    private /* synthetic */ zzcjn zzjhc;
    private /* synthetic */ AtomicReference zzjhe;

    zzcjz(zzcjn zzcjn2, AtomicReference atomicReference) {
        this.zzjhc = zzcjn2;
        this.zzjhe = atomicReference;
    }

    @Override
    public final void run() {
        ((zzcjk)this.zzjhc).zzawp().zza(this.zzjhe);
    }
}


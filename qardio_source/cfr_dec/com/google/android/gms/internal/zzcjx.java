/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzcln;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

final class zzcjx
implements Runnable {
    private /* synthetic */ zzcjn zzjhc;
    private /* synthetic */ AtomicReference zzjhe;
    private /* synthetic */ boolean zzjhf;

    zzcjx(zzcjn zzcjn2, AtomicReference atomicReference, boolean bl) {
        this.zzjhc = zzcjn2;
        this.zzjhe = atomicReference;
        this.zzjhf = bl;
    }

    @Override
    public final void run() {
        ((zzcjk)this.zzjhc).zzawp().zza(this.zzjhe, this.zzjhf);
    }
}


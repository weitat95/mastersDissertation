/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckg;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

final class zzcjr
implements Runnable {
    private /* synthetic */ String zzimf;
    private /* synthetic */ String zzjgq;
    private /* synthetic */ String zzjgr;
    private /* synthetic */ zzcjn zzjhc;
    private /* synthetic */ AtomicReference zzjhe;

    zzcjr(zzcjn zzcjn2, AtomicReference atomicReference, String string2, String string3, String string4) {
        this.zzjhc = zzcjn2;
        this.zzjhe = atomicReference;
        this.zzimf = string2;
        this.zzjgq = string3;
        this.zzjgr = string4;
    }

    @Override
    public final void run() {
        this.zzjhc.zziwf.zzawp().zza(this.zzjhe, this.zzimf, this.zzjgq, this.zzjgr);
    }
}


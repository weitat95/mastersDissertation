/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcia;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;

final class zzcjt
implements Runnable {
    private /* synthetic */ zzcjn zzjhc;
    private /* synthetic */ long zzjhg;

    zzcjt(zzcjn zzcjn2, long l) {
        this.zzjhc = zzcjn2;
        this.zzjhg = l;
    }

    @Override
    public final void run() {
        this.zzjhc.zzawz().zzjde.set(this.zzjhg);
        ((zzcjk)this.zzjhc).zzawy().zzazi().zzj("Minimum session duration set", this.zzjhg);
    }
}


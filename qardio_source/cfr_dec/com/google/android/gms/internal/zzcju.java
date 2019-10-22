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

final class zzcju
implements Runnable {
    private /* synthetic */ zzcjn zzjhc;
    private /* synthetic */ long zzjhg;

    zzcju(zzcjn zzcjn2, long l) {
        this.zzjhc = zzcjn2;
        this.zzjhg = l;
    }

    @Override
    public final void run() {
        this.zzjhc.zzawz().zzjdf.set(this.zzjhg);
        ((zzcjk)this.zzjhc).zzawy().zzazi().zzj("Session timeout duration set", this.zzjhg);
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzchv;

final class zzchw
implements Runnable {
    private /* synthetic */ boolean zzjcn;
    private /* synthetic */ zzchv zzjco;

    zzchw(zzchv zzchv2, boolean bl) {
        this.zzjco = zzchv2;
        this.zzjcn = bl;
    }

    @Override
    public final void run() {
        zzchv.zza(this.zzjco).zzbo(this.zzjcn);
    }
}


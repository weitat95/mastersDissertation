/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcjn;

final class zzcjo
implements Runnable {
    private /* synthetic */ boolean zzecs;
    private /* synthetic */ zzcjn zzjhc;

    zzcjo(zzcjn zzcjn2, boolean bl) {
        this.zzjhc = zzcjn2;
        this.zzecs = bl;
    }

    @Override
    public final void run() {
        zzcjn.zza(this.zzjhc, this.zzecs);
    }
}


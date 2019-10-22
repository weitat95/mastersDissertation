/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzcv;
import com.google.android.gms.internal.zzcxq;

final class zzcx
implements Runnable {
    private /* synthetic */ zzcxq zzfrt;
    private /* synthetic */ zzcv zzfuv;

    zzcx(zzcv zzcv2, zzcxq zzcxq2) {
        this.zzfuv = zzcv2;
        this.zzfrt = zzcxq2;
    }

    @Override
    public final void run() {
        zzcv.zza(this.zzfuv, this.zzfrt);
    }
}


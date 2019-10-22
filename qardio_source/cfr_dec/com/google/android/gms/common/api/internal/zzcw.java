/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zzcv;

final class zzcw
implements Runnable {
    private /* synthetic */ zzcv zzfuv;

    zzcw(zzcv zzcv2) {
        this.zzfuv = zzcv2;
    }

    @Override
    public final void run() {
        zzcv.zza(this.zzfuv).zzh(new ConnectionResult(4));
    }
}


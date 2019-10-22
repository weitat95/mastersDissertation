/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zzao;
import com.google.android.gms.common.api.internal.zzar;
import com.google.android.gms.common.api.internal.zzbh;
import com.google.android.gms.common.api.internal.zzbj;

final class zzas
extends zzbj {
    private /* synthetic */ ConnectionResult zzfro;
    private /* synthetic */ zzar zzfrp;

    zzas(zzar zzar2, zzbh zzbh2, ConnectionResult connectionResult) {
        this.zzfrp = zzar2;
        this.zzfro = connectionResult;
        super(zzbh2);
    }

    @Override
    public final void zzaib() {
        zzao.zza(this.zzfrp.zzfrl, this.zzfro);
    }
}


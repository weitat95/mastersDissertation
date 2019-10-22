/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzal;
import com.google.android.gms.common.api.internal.zzbh;
import com.google.android.gms.common.api.internal.zzbj;

final class zzam
extends zzbj {
    private /* synthetic */ zzal zzfqx;

    zzam(zzal zzal2, zzbh zzbh2) {
        this.zzfqx = zzal2;
        super(zzbh2);
    }

    @Override
    public final void zzaib() {
        this.zzfqx.onConnectionSuspended(1);
    }
}


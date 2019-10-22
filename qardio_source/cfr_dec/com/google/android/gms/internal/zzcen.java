/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzceo;
import com.google.android.gms.internal.zzcev;

final class zzcen
extends zzcev {
    private final zzn<Status> zzgbw;

    public zzcen(zzn<Status> zzn2) {
        this.zzgbw = zzn2;
    }

    @Override
    public final void zza(zzceo zzceo2) {
        this.zzgbw.setResult(zzceo2.getStatus());
    }
}


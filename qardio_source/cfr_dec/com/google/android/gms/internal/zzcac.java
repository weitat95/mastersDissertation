/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzbyg;

public final class zzcac
extends zzbyg {
    private final zzn<Status> zzgbw;

    public zzcac(zzn<Status> zzn2) {
        this.zzgbw = zzn2;
    }

    @Override
    public final void zzn(Status status) {
        this.zzgbw.setResult(status);
    }
}


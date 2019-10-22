/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.measurement.AppMeasurement;

final class zzcjq
implements Runnable {
    private /* synthetic */ zzcjn zzjhc;
    private /* synthetic */ AppMeasurement.ConditionalUserProperty zzjhd;

    zzcjq(zzcjn zzcjn2, AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        this.zzjhc = zzcjn2;
        this.zzjhd = conditionalUserProperty;
    }

    @Override
    public final void run() {
        zzcjn.zzb(this.zzjhc, this.zzjhd);
    }
}


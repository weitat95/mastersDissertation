/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzcku;

final class zzcky
implements Runnable {
    private /* synthetic */ zzcku zzjit;

    zzcky(zzcku zzcku2) {
        this.zzjit = zzcku2;
    }

    @Override
    public final void run() {
        zzckg.zza(this.zzjit.zzjij, new ComponentName(((zzcjk)this.zzjit.zzjij).getContext(), "com.google.android.gms.measurement.AppMeasurementService"));
    }
}


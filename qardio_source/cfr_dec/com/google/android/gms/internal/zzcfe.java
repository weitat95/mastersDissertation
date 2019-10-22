/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.internal.zzcff;
import com.google.android.gms.internal.zzcfg;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzq;

final class zzcfe
extends zzq {
    private final zzci<LocationCallback> zzfus;

    @Override
    public final void onLocationAvailability(LocationAvailability locationAvailability) {
        this.zzfus.zza(new zzcfg(this, locationAvailability));
    }

    @Override
    public final void onLocationResult(LocationResult locationResult) {
        this.zzfus.zza(new zzcff(this, locationResult));
    }
}


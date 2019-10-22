/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.google.android.gms.internal;

import android.location.Location;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.internal.zzcfj;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.zzt;

final class zzcfi
extends zzt {
    private final zzci<LocationListener> zzfus;

    zzcfi(zzci<LocationListener> zzci2) {
        this.zzfus = zzci2;
    }

    @Override
    public final void onLocationChanged(Location location) {
        synchronized (this) {
            this.zzfus.zza(new zzcfj(this, location));
            return;
        }
    }
}


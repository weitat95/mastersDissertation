/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.internal.zzcfe;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;

final class zzcfg
implements zzcl<LocationCallback> {
    private /* synthetic */ LocationAvailability zzils;

    zzcfg(zzcfe zzcfe2, LocationAvailability locationAvailability) {
        this.zzils = locationAvailability;
    }

    @Override
    public final void zzahz() {
    }

    @Override
    public final /* synthetic */ void zzu(Object object) {
        ((LocationCallback)object).onLocationAvailability(this.zzils);
    }
}


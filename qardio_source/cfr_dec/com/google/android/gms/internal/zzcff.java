/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.internal.zzcfe;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

final class zzcff
implements zzcl<LocationCallback> {
    private /* synthetic */ LocationResult zzilr;

    zzcff(zzcfe zzcfe2, LocationResult locationResult) {
        this.zzilr = locationResult;
    }

    @Override
    public final void zzahz() {
    }

    @Override
    public final /* synthetic */ void zzu(Object object) {
        ((LocationCallback)object).onLocationResult(this.zzilr);
    }
}


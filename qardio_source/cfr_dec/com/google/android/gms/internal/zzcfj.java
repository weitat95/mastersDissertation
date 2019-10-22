/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.google.android.gms.internal;

import android.location.Location;
import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.internal.zzcfi;
import com.google.android.gms.location.LocationListener;

final class zzcfj
implements zzcl<LocationListener> {
    private /* synthetic */ Location zzilt;

    zzcfj(zzcfi zzcfi2, Location location) {
        this.zzilt = location;
    }

    @Override
    public final void zzahz() {
    }

    @Override
    public final /* synthetic */ void zzu(Object object) {
        ((LocationListener)object).onLocationChanged(this.zzilt);
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.zzbq;

final class zzaj
extends zzbq {
    private /* synthetic */ OnStreetViewPanoramaReadyCallback zzitl;

    zzaj(StreetViewPanoramaView.zza zza2, OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
        this.zzitl = onStreetViewPanoramaReadyCallback;
    }

    @Override
    public final void zza(IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate) throws RemoteException {
        this.zzitl.onStreetViewPanoramaReady(new StreetViewPanorama(iStreetViewPanoramaDelegate));
    }
}


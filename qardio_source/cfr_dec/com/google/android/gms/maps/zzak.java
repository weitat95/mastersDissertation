/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.zzaq;

final class zzak
extends zzaq {
    private /* synthetic */ OnMapReadyCallback zzist;

    zzak(SupportMapFragment.zza zza2, OnMapReadyCallback onMapReadyCallback) {
        this.zzist = onMapReadyCallback;
    }

    @Override
    public final void zza(IGoogleMapDelegate iGoogleMapDelegate) throws RemoteException {
        this.zzist.onMapReady(new GoogleMap(iGoogleMapDelegate));
    }
}


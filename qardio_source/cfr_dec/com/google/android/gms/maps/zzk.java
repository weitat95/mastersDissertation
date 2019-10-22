/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzam;

final class zzk
extends zzam {
    private /* synthetic */ GoogleMap.OnMapLoadedCallback zzirk;

    zzk(GoogleMap googleMap, GoogleMap.OnMapLoadedCallback onMapLoadedCallback) {
        this.zzirk = onMapLoadedCallback;
    }

    @Override
    public final void onMapLoaded() throws RemoteException {
        this.zzirk.onMapLoaded();
    }
}


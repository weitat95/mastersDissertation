/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class CameraUpdateFactory {
    private static ICameraUpdateFactoryDelegate zziqx;

    public static CameraUpdate newLatLngBounds(LatLngBounds object, int n) {
        try {
            object = new CameraUpdate(CameraUpdateFactory.zzawa().newLatLngBounds((LatLngBounds)object, n));
            return object;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }

    public static CameraUpdate newLatLngZoom(LatLng object, float f) {
        try {
            object = new CameraUpdate(CameraUpdateFactory.zzawa().newLatLngZoom((LatLng)object, f));
            return object;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }

    public static void zza(ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegate) {
        zziqx = zzbq.checkNotNull(iCameraUpdateFactoryDelegate);
    }

    private static ICameraUpdateFactoryDelegate zzawa() {
        return zzbq.checkNotNull(zziqx, "CameraUpdateFactory is not initialized");
    }
}


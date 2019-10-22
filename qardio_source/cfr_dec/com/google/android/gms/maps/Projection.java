/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Point
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.graphics.Point;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class Projection {
    private final IProjectionDelegate zzitd;

    Projection(IProjectionDelegate iProjectionDelegate) {
        this.zzitd = iProjectionDelegate;
    }

    public final Point toScreenLocation(LatLng latLng) {
        try {
            latLng = (Point)zzn.zzx(this.zzitd.toScreenLocation(latLng));
            return latLng;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }
}


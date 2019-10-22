/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.model.LatLng;

public final class zzbr
extends zzeu
implements IProjectionDelegate {
    zzbr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IProjectionDelegate");
    }

    @Override
    public final IObjectWrapper toScreenLocation(LatLng latLng) throws RemoteException {
        Object object = this.zzbe();
        zzew.zza(object, latLng);
        latLng = this.zza(2, (Parcel)object);
        object = IObjectWrapper.zza.zzaq(latLng.readStrongBinder());
        latLng.recycle();
        return object;
    }
}


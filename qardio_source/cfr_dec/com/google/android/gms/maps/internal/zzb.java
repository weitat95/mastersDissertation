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
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public final class zzb
extends zzeu
implements ICameraUpdateFactoryDelegate {
    zzb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
    }

    @Override
    public final IObjectWrapper newLatLngBounds(LatLngBounds latLngBounds, int n) throws RemoteException {
        Object object = this.zzbe();
        zzew.zza(object, latLngBounds);
        object.writeInt(n);
        latLngBounds = this.zza(10, (Parcel)object);
        object = IObjectWrapper.zza.zzaq(latLngBounds.readStrongBinder());
        latLngBounds.recycle();
        return object;
    }

    @Override
    public final IObjectWrapper newLatLngZoom(LatLng latLng, float f) throws RemoteException {
        Object object = this.zzbe();
        zzew.zza(object, latLng);
        object.writeFloat(f);
        latLng = this.zza(9, (Parcel)object);
        object = IObjectWrapper.zza.zzaq(latLng.readStrongBinder());
        latLng.recycle();
        return object;
    }
}


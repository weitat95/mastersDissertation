/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.maps.model.internal.zza;

public final class zzc
extends zzeu
implements zza {
    zzc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
    }

    @Override
    public final IObjectWrapper zzea(int n) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeInt(n);
        parcel = this.zza(1, parcel);
        IObjectWrapper iObjectWrapper = IObjectWrapper.zza.zzaq(parcel.readStrongBinder());
        parcel.recycle();
        return iObjectWrapper;
    }
}


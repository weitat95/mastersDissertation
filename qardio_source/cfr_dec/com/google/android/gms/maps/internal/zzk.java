/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.zzap;

public final class zzk
extends zzeu
implements IMapViewDelegate {
    zzk(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IMapViewDelegate");
    }

    @Override
    public final void getMapAsync(zzap zzap2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzap2);
        this.zzb(9, parcel);
    }

    @Override
    public final IObjectWrapper getView() throws RemoteException {
        Parcel parcel = this.zza(8, this.zzbe());
        IObjectWrapper iObjectWrapper = IObjectWrapper.zza.zzaq(parcel.readStrongBinder());
        parcel.recycle();
        return iObjectWrapper;
    }

    @Override
    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, (Parcelable)bundle);
        this.zzb(2, parcel);
    }

    @Override
    public final void onDestroy() throws RemoteException {
        this.zzb(5, this.zzbe());
    }

    @Override
    public final void onLowMemory() throws RemoteException {
        this.zzb(6, this.zzbe());
    }

    @Override
    public final void onPause() throws RemoteException {
        this.zzb(4, this.zzbe());
    }

    @Override
    public final void onResume() throws RemoteException {
        this.zzb(3, this.zzbe());
    }

    @Override
    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, (Parcelable)bundle);
        parcel = this.zza(7, parcel);
        if (parcel.readInt() != 0) {
            bundle.readFromParcel(parcel);
        }
        parcel.recycle();
    }

    @Override
    public final void onStart() throws RemoteException {
        this.zzb(12, this.zzbe());
    }

    @Override
    public final void onStop() throws RemoteException {
        this.zzb(13, this.zzbe());
    }
}


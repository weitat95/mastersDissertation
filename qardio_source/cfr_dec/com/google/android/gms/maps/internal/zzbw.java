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
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.zzbp;

public final class zzbw
extends zzeu
implements IStreetViewPanoramaViewDelegate {
    zzbw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
    }

    @Override
    public final void getStreetViewPanoramaAsync(zzbp zzbp2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzbp2);
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
}


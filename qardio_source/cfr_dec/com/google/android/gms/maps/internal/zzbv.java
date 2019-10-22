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
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.zzbp;

public final class zzbv
extends zzeu
implements IStreetViewPanoramaFragmentDelegate {
    zzbv(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate");
    }

    @Override
    public final void getStreetViewPanoramaAsync(zzbp zzbp2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzbp2);
        this.zzb(12, parcel);
    }

    @Override
    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, (Parcelable)bundle);
        this.zzb(3, parcel);
    }

    @Override
    public final IObjectWrapper onCreateView(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, Bundle bundle) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        zzew.zza(parcel, iObjectWrapper2);
        zzew.zza(parcel, (Parcelable)bundle);
        iObjectWrapper = this.zza(4, parcel);
        iObjectWrapper2 = IObjectWrapper.zza.zzaq(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return iObjectWrapper2;
    }

    @Override
    public final void onDestroy() throws RemoteException {
        this.zzb(8, this.zzbe());
    }

    @Override
    public final void onDestroyView() throws RemoteException {
        this.zzb(7, this.zzbe());
    }

    @Override
    public final void onInflate(IObjectWrapper iObjectWrapper, StreetViewPanoramaOptions streetViewPanoramaOptions, Bundle bundle) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        zzew.zza(parcel, streetViewPanoramaOptions);
        zzew.zza(parcel, (Parcelable)bundle);
        this.zzb(2, parcel);
    }

    @Override
    public final void onLowMemory() throws RemoteException {
        this.zzb(9, this.zzbe());
    }

    @Override
    public final void onPause() throws RemoteException {
        this.zzb(6, this.zzbe());
    }

    @Override
    public final void onResume() throws RemoteException {
        this.zzb(5, this.zzbe());
    }

    @Override
    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, (Parcelable)bundle);
        parcel = this.zza(10, parcel);
        if (parcel.readInt() != 0) {
            bundle.readFromParcel(parcel);
        }
        parcel.recycle();
    }
}


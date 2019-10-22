/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;
import com.google.android.gms.maps.internal.zzal;
import com.google.android.gms.maps.internal.zzbr;
import com.google.android.gms.maps.internal.zzbx;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.internal.zzp;
import com.google.android.gms.maps.model.internal.zzq;

public final class zzg
extends zzeu
implements IGoogleMapDelegate {
    zzg(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IGoogleMapDelegate");
    }

    @Override
    public final zzp addMarker(MarkerOptions markerOptions) throws RemoteException {
        Object object = this.zzbe();
        zzew.zza(object, markerOptions);
        markerOptions = this.zza(11, (Parcel)object);
        object = zzq.zzbk(markerOptions.readStrongBinder());
        markerOptions.recycle();
        return object;
    }

    @Override
    public final void animateCamera(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        this.zzb(5, parcel);
    }

    @Override
    public final void clear() throws RemoteException {
        this.zzb(14, this.zzbe());
    }

    @Override
    public final CameraPosition getCameraPosition() throws RemoteException {
        Parcel parcel = this.zza(1, this.zzbe());
        CameraPosition cameraPosition = zzew.zza(parcel, CameraPosition.CREATOR);
        parcel.recycle();
        return cameraPosition;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public final IProjectionDelegate getProjection() throws RemoteException {
        void var1_4;
        Parcel parcel = this.zza(26, this.zzbe());
        IBinder iBinder = parcel.readStrongBinder();
        if (iBinder == null) {
            Object var1_3 = null;
        } else {
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
            if (iInterface instanceof IProjectionDelegate) {
                IProjectionDelegate iProjectionDelegate = (IProjectionDelegate)iInterface;
            } else {
                zzbr zzbr2 = new zzbr(iBinder);
            }
        }
        parcel.recycle();
        return var1_4;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public final IUiSettingsDelegate getUiSettings() throws RemoteException {
        void var1_4;
        Parcel parcel = this.zza(25, this.zzbe());
        IBinder iBinder = parcel.readStrongBinder();
        if (iBinder == null) {
            Object var1_3 = null;
        } else {
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
            if (iInterface instanceof IUiSettingsDelegate) {
                IUiSettingsDelegate iUiSettingsDelegate = (IUiSettingsDelegate)iInterface;
            } else {
                zzbx zzbx2 = new zzbx(iBinder);
            }
        }
        parcel.recycle();
        return var1_4;
    }

    @Override
    public final void setOnMapLoadedCallback(zzal zzal2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzal2);
        this.zzb(42, parcel);
    }
}


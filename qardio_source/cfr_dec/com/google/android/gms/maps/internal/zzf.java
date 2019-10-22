/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
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
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.zzb;
import com.google.android.gms.maps.internal.zzbv;
import com.google.android.gms.maps.internal.zzbw;
import com.google.android.gms.maps.internal.zze;
import com.google.android.gms.maps.internal.zzj;
import com.google.android.gms.maps.internal.zzk;
import com.google.android.gms.maps.model.internal.zza;

public final class zzf
extends zzeu
implements zze {
    zzf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.ICreator");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public final IMapViewDelegate zza(IObjectWrapper object, GoogleMapOptions googleMapOptions) throws RemoteException {
        Parcel parcel;
        void var1_4;
        Parcel parcel2 = this.zzbe();
        zzew.zza(parcel2, object);
        zzew.zza(parcel2, (Parcelable)parcel);
        parcel = this.zza(3, parcel2);
        IBinder iBinder = parcel.readStrongBinder();
        if (iBinder == null) {
            Object var1_3 = null;
        } else {
            parcel2 = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
            if (parcel2 instanceof IMapViewDelegate) {
                IMapViewDelegate iMapViewDelegate = (IMapViewDelegate)parcel2;
            } else {
                zzk zzk2 = new zzk(iBinder);
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
    public final IStreetViewPanoramaViewDelegate zza(IObjectWrapper object, StreetViewPanoramaOptions streetViewPanoramaOptions) throws RemoteException {
        Parcel parcel;
        void var1_4;
        Parcel parcel2 = this.zzbe();
        zzew.zza(parcel2, object);
        zzew.zza(parcel2, (Parcelable)parcel);
        parcel = this.zza(7, parcel2);
        IBinder iBinder = parcel.readStrongBinder();
        if (iBinder == null) {
            Object var1_3 = null;
        } else {
            parcel2 = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
            if (parcel2 instanceof IStreetViewPanoramaViewDelegate) {
                IStreetViewPanoramaViewDelegate iStreetViewPanoramaViewDelegate = (IStreetViewPanoramaViewDelegate)parcel2;
            } else {
                zzbw zzbw2 = new zzbw(iBinder);
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
    public final IMapFragmentDelegate zzaa(IObjectWrapper object) throws RemoteException {
        void var1_4;
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, object);
        parcel = this.zza(2, parcel);
        IBinder iBinder = parcel.readStrongBinder();
        if (iBinder == null) {
            Object var1_3 = null;
        } else {
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
            if (iInterface instanceof IMapFragmentDelegate) {
                IMapFragmentDelegate iMapFragmentDelegate = (IMapFragmentDelegate)iInterface;
            } else {
                zzj zzj2 = new zzj(iBinder);
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
    public final IStreetViewPanoramaFragmentDelegate zzab(IObjectWrapper object) throws RemoteException {
        void var1_4;
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, object);
        parcel = this.zza(8, parcel);
        IBinder iBinder = parcel.readStrongBinder();
        if (iBinder == null) {
            Object var1_3 = null;
        } else {
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate");
            if (iInterface instanceof IStreetViewPanoramaFragmentDelegate) {
                IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate = (IStreetViewPanoramaFragmentDelegate)iInterface;
            } else {
                zzbv zzbv2 = new zzbv(iBinder);
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
    public final ICameraUpdateFactoryDelegate zzawc() throws RemoteException {
        void var1_4;
        Parcel parcel = this.zza(4, this.zzbe());
        IBinder iBinder = parcel.readStrongBinder();
        if (iBinder == null) {
            Object var1_3 = null;
        } else {
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
            if (iInterface instanceof ICameraUpdateFactoryDelegate) {
                ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegate = (ICameraUpdateFactoryDelegate)iInterface;
            } else {
                zzb zzb2 = new zzb(iBinder);
            }
        }
        parcel.recycle();
        return var1_4;
    }

    @Override
    public final zza zzawd() throws RemoteException {
        Parcel parcel = this.zza(5, this.zzbe());
        zza zza2 = com.google.android.gms.maps.model.internal.zzb.zzbf(parcel.readStrongBinder());
        parcel.recycle();
        return zza2;
    }

    @Override
    public final void zzi(IObjectWrapper iObjectWrapper, int n) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        parcel.writeInt(n);
        this.zzb(6, parcel);
    }
}


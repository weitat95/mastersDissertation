/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.model.internal.zza;

public interface zze
extends IInterface {
    public IMapViewDelegate zza(IObjectWrapper var1, GoogleMapOptions var2) throws RemoteException;

    public IStreetViewPanoramaViewDelegate zza(IObjectWrapper var1, StreetViewPanoramaOptions var2) throws RemoteException;

    public IMapFragmentDelegate zzaa(IObjectWrapper var1) throws RemoteException;

    public IStreetViewPanoramaFragmentDelegate zzab(IObjectWrapper var1) throws RemoteException;

    public ICameraUpdateFactoryDelegate zzawc() throws RemoteException;

    public zza zzawd() throws RemoteException;

    public void zzi(IObjectWrapper var1, int var2) throws RemoteException;
}


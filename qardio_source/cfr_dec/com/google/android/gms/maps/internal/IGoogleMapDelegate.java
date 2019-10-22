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
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;
import com.google.android.gms.maps.internal.zzal;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.internal.zzp;

public interface IGoogleMapDelegate
extends IInterface {
    public zzp addMarker(MarkerOptions var1) throws RemoteException;

    public void animateCamera(IObjectWrapper var1) throws RemoteException;

    public void clear() throws RemoteException;

    public CameraPosition getCameraPosition() throws RemoteException;

    public IProjectionDelegate getProjection() throws RemoteException;

    public IUiSettingsDelegate getUiSettings() throws RemoteException;

    public void setOnMapLoadedCallback(zzal var1) throws RemoteException;
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.zzbp;

public interface IStreetViewPanoramaFragmentDelegate
extends IInterface {
    public void getStreetViewPanoramaAsync(zzbp var1) throws RemoteException;

    public void onCreate(Bundle var1) throws RemoteException;

    public IObjectWrapper onCreateView(IObjectWrapper var1, IObjectWrapper var2, Bundle var3) throws RemoteException;

    public void onDestroy() throws RemoteException;

    public void onDestroyView() throws RemoteException;

    public void onInflate(IObjectWrapper var1, StreetViewPanoramaOptions var2, Bundle var3) throws RemoteException;

    public void onLowMemory() throws RemoteException;

    public void onPause() throws RemoteException;

    public void onResume() throws RemoteException;

    public void onSaveInstanceState(Bundle var1) throws RemoteException;
}


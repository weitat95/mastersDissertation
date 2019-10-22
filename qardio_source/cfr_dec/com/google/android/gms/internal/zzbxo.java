/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.zzg;
import com.google.android.gms.fitness.request.zzk;

public interface zzbxo
extends IInterface {
    public void zza(DataDeleteRequest var1) throws RemoteException;

    public void zza(DataReadRequest var1) throws RemoteException;

    public void zza(zzg var1) throws RemoteException;

    public void zza(zzk var1) throws RemoteException;
}


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
import com.google.android.gms.internal.zzcdz;
import com.google.android.gms.internal.zzcfb;
import com.google.android.gms.internal.zzcfq;
import com.google.android.gms.location.LocationSettingsRequest;

public interface zzcez
extends IInterface {
    public void zza(zzcdz var1) throws RemoteException;

    public void zza(zzcfq var1) throws RemoteException;

    public void zza(LocationSettingsRequest var1, zzcfb var2, String var3) throws RemoteException;

    public void zzbj(boolean var1) throws RemoteException;
}


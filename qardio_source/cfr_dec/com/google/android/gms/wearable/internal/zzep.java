/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.internal.zzd;
import com.google.android.gms.wearable.internal.zzek;

public interface zzep
extends IInterface {
    public void zza(zzek var1) throws RemoteException;

    public void zza(zzek var1, PutDataRequest var2) throws RemoteException;

    public void zza(zzek var1, zzd var2) throws RemoteException;

    public void zza(zzek var1, String var2, String var3, byte[] var4) throws RemoteException;

    public void zzb(zzek var1, Uri var2, int var3) throws RemoteException;
}


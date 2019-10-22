/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.internal.zzah;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.internal.zzfe;
import com.google.android.gms.wearable.internal.zzfo;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.internal.zzl;
import java.util.List;

public interface zzem
extends IInterface {
    public void onConnectedNodes(List<zzfo> var1) throws RemoteException;

    public void zza(zzah var1) throws RemoteException;

    public void zza(zzaw var1) throws RemoteException;

    public void zza(zzfe var1) throws RemoteException;

    public void zza(zzfo var1) throws RemoteException;

    public void zza(zzi var1) throws RemoteException;

    public void zza(zzl var1) throws RemoteException;

    public void zzas(DataHolder var1) throws RemoteException;

    public void zzb(zzfo var1) throws RemoteException;
}


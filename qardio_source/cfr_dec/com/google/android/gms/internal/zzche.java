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
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzcln;
import java.util.List;

public interface zzche
extends IInterface {
    public List<zzcln> zza(zzcgi var1, boolean var2) throws RemoteException;

    public List<zzcgl> zza(String var1, String var2, zzcgi var3) throws RemoteException;

    public List<zzcln> zza(String var1, String var2, String var3, boolean var4) throws RemoteException;

    public List<zzcln> zza(String var1, String var2, boolean var3, zzcgi var4) throws RemoteException;

    public void zza(long var1, String var3, String var4, String var5) throws RemoteException;

    public void zza(zzcgi var1) throws RemoteException;

    public void zza(zzcgl var1, zzcgi var2) throws RemoteException;

    public void zza(zzcha var1, zzcgi var2) throws RemoteException;

    public void zza(zzcha var1, String var2, String var3) throws RemoteException;

    public void zza(zzcln var1, zzcgi var2) throws RemoteException;

    public byte[] zza(zzcha var1, String var2) throws RemoteException;

    public void zzb(zzcgi var1) throws RemoteException;

    public void zzb(zzcgl var1) throws RemoteException;

    public String zzc(zzcgi var1) throws RemoteException;

    public void zzd(zzcgi var1) throws RemoteException;

    public List<zzcgl> zzj(String var1, String var2, String var3) throws RemoteException;
}


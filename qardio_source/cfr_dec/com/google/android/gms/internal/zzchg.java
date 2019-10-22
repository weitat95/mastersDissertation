/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzcln;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import java.util.ArrayList;
import java.util.List;

public final class zzchg
extends zzeu
implements zzche {
    zzchg(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    @Override
    public final List<zzcln> zza(zzcgi zzcgi2, boolean bl) throws RemoteException {
        Object object = this.zzbe();
        zzew.zza(object, zzcgi2);
        zzew.zza(object, bl);
        zzcgi2 = this.zza(7, (Parcel)object);
        object = zzcgi2.createTypedArrayList(zzcln.CREATOR);
        zzcgi2.recycle();
        return object;
    }

    @Override
    public final List<zzcgl> zza(String string2, String object, zzcgi zzcgi2) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeString(string2);
        parcel.writeString((String)object);
        zzew.zza(parcel, zzcgi2);
        string2 = this.zza(16, parcel);
        object = string2.createTypedArrayList(zzcgl.CREATOR);
        string2.recycle();
        return object;
    }

    @Override
    public final List<zzcln> zza(String string2, String object, String string3, boolean bl) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeString(string2);
        parcel.writeString((String)object);
        parcel.writeString(string3);
        zzew.zza(parcel, bl);
        string2 = this.zza(15, parcel);
        object = string2.createTypedArrayList(zzcln.CREATOR);
        string2.recycle();
        return object;
    }

    @Override
    public final List<zzcln> zza(String string2, String object, boolean bl, zzcgi zzcgi2) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeString(string2);
        parcel.writeString((String)object);
        zzew.zza(parcel, bl);
        zzew.zza(parcel, zzcgi2);
        string2 = this.zza(14, parcel);
        object = string2.createTypedArrayList(zzcln.CREATOR);
        string2.recycle();
        return object;
    }

    @Override
    public final void zza(long l, String string2, String string3, String string4) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeLong(l);
        parcel.writeString(string2);
        parcel.writeString(string3);
        parcel.writeString(string4);
        this.zzb(10, parcel);
    }

    @Override
    public final void zza(zzcgi zzcgi2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcgi2);
        this.zzb(4, parcel);
    }

    @Override
    public final void zza(zzcgl zzcgl2, zzcgi zzcgi2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcgl2);
        zzew.zza(parcel, zzcgi2);
        this.zzb(12, parcel);
    }

    @Override
    public final void zza(zzcha zzcha2, zzcgi zzcgi2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcha2);
        zzew.zza(parcel, zzcgi2);
        this.zzb(1, parcel);
    }

    @Override
    public final void zza(zzcha zzcha2, String string2, String string3) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcha2);
        parcel.writeString(string2);
        parcel.writeString(string3);
        this.zzb(5, parcel);
    }

    @Override
    public final void zza(zzcln zzcln2, zzcgi zzcgi2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcln2);
        zzew.zza(parcel, zzcgi2);
        this.zzb(2, parcel);
    }

    @Override
    public final byte[] zza(zzcha zzcha2, String arrby) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcha2);
        parcel.writeString((String)arrby);
        zzcha2 = this.zza(9, parcel);
        arrby = zzcha2.createByteArray();
        zzcha2.recycle();
        return arrby;
    }

    @Override
    public final void zzb(zzcgi zzcgi2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcgi2);
        this.zzb(6, parcel);
    }

    @Override
    public final void zzb(zzcgl zzcgl2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcgl2);
        this.zzb(13, parcel);
    }

    @Override
    public final String zzc(zzcgi zzcgi2) throws RemoteException {
        Object object = this.zzbe();
        zzew.zza(object, zzcgi2);
        zzcgi2 = this.zza(11, (Parcel)object);
        object = zzcgi2.readString();
        zzcgi2.recycle();
        return object;
    }

    @Override
    public final void zzd(zzcgi zzcgi2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcgi2);
        this.zzb(18, parcel);
    }

    @Override
    public final List<zzcgl> zzj(String string2, String object, String string3) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeString(string2);
        parcel.writeString((String)object);
        parcel.writeString(string3);
        string2 = this.zza(17, parcel);
        object = string2.createTypedArrayList(zzcgl.CREATOR);
        string2.recycle();
        return object;
    }
}


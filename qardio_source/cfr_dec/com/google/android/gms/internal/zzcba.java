/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzcay;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzcba
extends zzeu
implements zzcay {
    zzcba(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.flags.IFlagProvider");
    }

    @Override
    public final boolean getBooleanFlagValue(String string2, boolean bl, int n) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeString(string2);
        zzew.zza(parcel, bl);
        parcel.writeInt(n);
        string2 = this.zza(2, parcel);
        bl = zzew.zza((Parcel)string2);
        string2.recycle();
        return bl;
    }

    @Override
    public final int getIntFlagValue(String string2, int n, int n2) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeString(string2);
        parcel.writeInt(n);
        parcel.writeInt(n2);
        string2 = this.zza(3, parcel);
        n = string2.readInt();
        string2.recycle();
        return n;
    }

    @Override
    public final long getLongFlagValue(String string2, long l, int n) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeString(string2);
        parcel.writeLong(l);
        parcel.writeInt(n);
        string2 = this.zza(4, parcel);
        l = string2.readLong();
        string2.recycle();
        return l;
    }

    @Override
    public final String getStringFlagValue(String string2, String string3, int n) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeString(string2);
        parcel.writeString(string3);
        parcel.writeInt(n);
        string2 = this.zza(5, parcel);
        string3 = string2.readString();
        string2.recycle();
        return string3;
    }

    @Override
    public final void init(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        this.zzb(1, parcel);
    }
}


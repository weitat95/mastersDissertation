/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.internal.zzbww;
import com.google.android.gms.internal.zzbwy;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzbwx
extends zzev
implements zzbww {
    public zzbwx() {
        this.attachInterface((IInterface)this, "com.google.android.gms.fitness.internal.IDataReadCallback");
    }

    public static zzbww zzat(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IDataReadCallback");
        if (iInterface instanceof zzbww) {
            return (zzbww)iInterface;
        }
        return new zzbwy(iBinder);
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        if (n == 1) {
            this.zza(zzew.zza(parcel, DataReadResult.CREATOR));
            return true;
        }
        return false;
    }
}


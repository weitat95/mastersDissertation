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
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.internal.zzbwt;
import com.google.android.gms.internal.zzbwv;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzbwu
extends zzev
implements zzbwt {
    public zzbwu() {
        this.attachInterface((IInterface)this, "com.google.android.gms.fitness.internal.IDailyTotalCallback");
    }

    public static zzbwt zzas(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IDailyTotalCallback");
        if (iInterface instanceof zzbwt) {
            return (zzbwt)iInterface;
        }
        return new zzbwv(iBinder);
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        if (n == 1) {
            this.zza(zzew.zza(parcel, DailyTotalResult.CREATOR));
            return true;
        }
        return false;
    }
}


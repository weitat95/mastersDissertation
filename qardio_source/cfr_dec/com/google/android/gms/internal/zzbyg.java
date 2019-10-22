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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyh;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzbyg
extends zzev
implements zzbyf {
    public zzbyg() {
        this.attachInterface((IInterface)this, "com.google.android.gms.fitness.internal.IStatusCallback");
    }

    public static zzbyf zzba(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IStatusCallback");
        if (iInterface instanceof zzbyf) {
            return (zzbyf)iInterface;
        }
        return new zzbyh(iBinder);
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        if (n == 1) {
            this.zzn(zzew.zza(parcel, Status.CREATOR));
            return true;
        }
        return false;
    }
}


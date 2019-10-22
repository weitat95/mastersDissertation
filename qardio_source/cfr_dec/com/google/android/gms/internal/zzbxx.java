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
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.internal.zzbxw;
import com.google.android.gms.internal.zzbxy;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzbxx
extends zzev
implements zzbxw {
    public zzbxx() {
        this.attachInterface((IInterface)this, "com.google.android.gms.fitness.internal.IListSubscriptionsCallback");
    }

    public static zzbxw zzax(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IListSubscriptionsCallback");
        if (iInterface instanceof zzbxw) {
            return (zzbxw)iInterface;
        }
        return new zzbxy(iBinder);
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        if (n == 1) {
            this.zza(zzew.zza(parcel, ListSubscriptionsResult.CREATOR));
            return true;
        }
        return false;
    }
}


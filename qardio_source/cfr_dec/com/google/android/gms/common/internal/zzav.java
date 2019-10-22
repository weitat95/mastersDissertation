/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzeu;

public final class zzav
extends zzeu
implements zzat {
    zzav(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ICertData");
    }

    @Override
    public final IObjectWrapper zzaga() throws RemoteException {
        Parcel parcel = this.zza(1, this.zzbe());
        IObjectWrapper iObjectWrapper = IObjectWrapper.zza.zzaq(parcel.readStrongBinder());
        parcel.recycle();
        return iObjectWrapper;
    }

    @Override
    public final int zzagb() throws RemoteException {
        Parcel parcel = this.zza(2, this.zzbe());
        int n = parcel.readInt();
        parcel.recycle();
        return n;
    }
}


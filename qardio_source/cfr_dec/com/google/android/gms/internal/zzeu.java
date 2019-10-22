/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class zzeu
implements IInterface {
    private final IBinder zzalc;
    private final String zzald;

    protected zzeu(IBinder iBinder, String string2) {
        this.zzalc = iBinder;
        this.zzald = string2;
    }

    public IBinder asBinder() {
        return this.zzalc;
    }

    protected final Parcel zza(int n, Parcel parcel) throws RemoteException {
        Parcel parcel2 = Parcel.obtain();
        try {
            this.zzalc.transact(n, parcel, parcel2, 0);
            parcel2.readException();
            return parcel2;
        }
        catch (RuntimeException runtimeException) {
            parcel2.recycle();
            throw runtimeException;
        }
        finally {
            parcel.recycle();
        }
    }

    protected final void zzb(int n, Parcel parcel) throws RemoteException {
        Parcel parcel2 = Parcel.obtain();
        try {
            this.zzalc.transact(n, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    protected final Parcel zzbe() {
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(this.zzald);
        return parcel;
    }

    protected final void zzc(int n, Parcel parcel) throws RemoteException {
        try {
            this.zzalc.transact(n, parcel, null, 1);
            return;
        }
        finally {
            parcel.recycle();
        }
    }
}


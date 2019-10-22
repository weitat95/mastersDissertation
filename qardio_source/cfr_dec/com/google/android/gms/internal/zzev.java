/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class zzev
extends Binder
implements IInterface {
    public IBinder asBinder() {
        return this;
    }

    protected final boolean zza(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (n > 16777215) {
            return super.onTransact(n, parcel, parcel2, n2);
        }
        parcel.enforceInterface(this.getInterfaceDescriptor());
        return false;
    }
}


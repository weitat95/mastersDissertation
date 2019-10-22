/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.common.internal.zzav;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzau
extends zzev
implements zzat {
    public zzau() {
        this.attachInterface((IInterface)this, "com.google.android.gms.common.internal.ICertData");
    }

    public static zzat zzam(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
        if (iInterface instanceof zzat) {
            return (zzat)iInterface;
        }
        return new zzav(iBinder);
    }

    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        if (this.zza(n, (Parcel)object, parcel, n2)) {
            return true;
        }
        switch (n) {
            default: {
                return false;
            }
            case 1: {
                object = this.zzaga();
                parcel.writeNoException();
                zzew.zza(parcel, (IInterface)object);
                return true;
            }
            case 2: 
        }
        n = this.zzagb();
        parcel.writeNoException();
        parcel.writeInt(n);
        return true;
    }
}


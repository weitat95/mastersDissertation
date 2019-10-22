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
import com.google.android.gms.common.internal.zzaw;
import com.google.android.gms.common.internal.zzay;
import com.google.android.gms.common.internal.zzz;

final class zzaz
implements zzay {
    private final IBinder zzalc;

    zzaz(IBinder iBinder) {
        this.zzalc = iBinder;
    }

    public final IBinder asBinder() {
        return this.zzalc;
    }

    @Override
    public final void zza(zzaw zzaw2, zzz zzz2) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
            parcel.writeStrongBinder(zzaw2.asBinder());
            parcel.writeInt(1);
            zzz2.writeToParcel(parcel, 0);
            this.zzalc.transact(46, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }
}


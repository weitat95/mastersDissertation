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
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.internal.zzfo;

public final class zzfq
extends zzeu
implements zzfo {
    zzfq(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
    }

    @Override
    public final String getId() throws RemoteException {
        Parcel parcel = this.zza(1, this.zzbe());
        String string2 = parcel.readString();
        parcel.recycle();
        return string2;
    }

    @Override
    public final boolean zzb(boolean bl) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, true);
        parcel = this.zza(2, parcel);
        bl = zzew.zza(parcel);
        parcel.recycle();
        return bl;
    }

    @Override
    public final boolean zzbp() throws RemoteException {
        Parcel parcel = this.zza(6, this.zzbe());
        boolean bl = zzew.zza(parcel);
        parcel.recycle();
        return bl;
    }
}


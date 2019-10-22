/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.maps.model.internal.zzp;

public final class zzr
extends zzeu
implements zzp {
    zzr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IMarkerDelegate");
    }

    @Override
    public final int hashCodeRemote() throws RemoteException {
        Parcel parcel = this.zza(17, this.zzbe());
        int n = parcel.readInt();
        parcel.recycle();
        return n;
    }

    @Override
    public final boolean zzj(zzp zzp2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzp2);
        zzp2 = this.zza(16, parcel);
        boolean bl = zzew.zza((Parcel)zzp2);
        zzp2.recycle();
        return bl;
    }
}


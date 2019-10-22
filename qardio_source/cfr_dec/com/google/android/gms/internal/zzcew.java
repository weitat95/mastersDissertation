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
import com.google.android.gms.internal.zzceo;
import com.google.android.gms.internal.zzceu;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzcew
extends zzeu
implements zzceu {
    zzcew(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.internal.IFusedLocationProviderCallback");
    }

    @Override
    public final void zza(zzceo zzceo2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzceo2);
        this.zzc(1, parcel);
    }
}


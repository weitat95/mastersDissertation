/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzp;

public final class zzr
extends zzeu
implements zzp {
    zzr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.ILocationCallback");
    }

    @Override
    public final void onLocationAvailability(LocationAvailability locationAvailability) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, locationAvailability);
        this.zzc(2, parcel);
    }

    @Override
    public final void onLocationResult(LocationResult locationResult) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, locationResult);
        this.zzc(1, parcel);
    }
}


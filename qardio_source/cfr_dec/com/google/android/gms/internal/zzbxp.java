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
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.zzg;
import com.google.android.gms.fitness.request.zzk;
import com.google.android.gms.internal.zzbxo;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzbxp
extends zzeu
implements zzbxo {
    zzbxp(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
    }

    @Override
    public final void zza(DataDeleteRequest dataDeleteRequest) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, dataDeleteRequest);
        this.zzb(3, parcel);
    }

    @Override
    public final void zza(DataReadRequest dataReadRequest) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, dataReadRequest);
        this.zzb(1, parcel);
    }

    @Override
    public final void zza(zzg zzg2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzg2);
        this.zzb(7, parcel);
    }

    @Override
    public final void zza(zzk zzk2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzk2);
        this.zzb(2, parcel);
    }
}


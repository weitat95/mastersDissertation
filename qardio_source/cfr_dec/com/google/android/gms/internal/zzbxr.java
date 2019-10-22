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
import com.google.android.gms.fitness.request.zzaj;
import com.google.android.gms.fitness.request.zzbj;
import com.google.android.gms.internal.zzbxq;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzbxr
extends zzeu
implements zzbxq {
    zzbxr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitRecordingApi");
    }

    @Override
    public final void zza(zzaj zzaj2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzaj2);
        this.zzb(3, parcel);
    }

    @Override
    public final void zza(zzbj zzbj2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzbj2);
        this.zzb(1, parcel);
    }
}


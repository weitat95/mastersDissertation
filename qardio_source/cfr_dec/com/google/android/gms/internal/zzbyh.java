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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzbyh
extends zzeu
implements zzbyf {
    zzbyh(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IStatusCallback");
    }

    @Override
    public final void zzn(Status status) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, status);
        this.zzc(1, parcel);
    }
}


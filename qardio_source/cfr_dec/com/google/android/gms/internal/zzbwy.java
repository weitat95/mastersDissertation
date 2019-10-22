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
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.internal.zzbww;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzbwy
extends zzeu
implements zzbww {
    zzbwy(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IDataReadCallback");
    }

    @Override
    public final void zza(DataReadResult dataReadResult) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, dataReadResult);
        this.zzc(1, parcel);
    }
}


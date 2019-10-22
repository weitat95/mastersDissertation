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
import com.google.android.gms.fitness.request.zzaa;
import com.google.android.gms.internal.zzbxk;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzbxl
extends zzeu
implements zzbxk {
    zzbxl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitConfigApi");
    }

    @Override
    public final void zza(zzaa zzaa2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzaa2);
        this.zzb(22, parcel);
    }
}


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
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.internal.zzcxj;
import com.google.android.gms.internal.zzcxl;
import com.google.android.gms.internal.zzcxo;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzcxm
extends zzeu
implements zzcxl {
    zzcxm(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    @Override
    public final void zza(zzan zzan2, int n, boolean bl) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzan2);
        parcel.writeInt(n);
        zzew.zza(parcel, bl);
        this.zzb(9, parcel);
    }

    @Override
    public final void zza(zzcxo zzcxo2, zzcxj zzcxj2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcxo2);
        zzew.zza(parcel, zzcxj2);
        this.zzb(12, parcel);
    }

    @Override
    public final void zzeh(int n) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeInt(n);
        this.zzb(7, parcel);
    }
}


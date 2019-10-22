/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.wearable.internal.zzah;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.internal.zzem;
import com.google.android.gms.wearable.internal.zzfe;
import com.google.android.gms.wearable.internal.zzfo;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.internal.zzl;
import java.util.List;

public final class zzeo
extends zzeu
implements zzem {
    zzeo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wearable.internal.IWearableListener");
    }

    @Override
    public final void onConnectedNodes(List<zzfo> list) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeTypedList(list);
        this.zzc(5, parcel);
    }

    @Override
    public final void zza(zzah zzah2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzah2);
        this.zzc(8, parcel);
    }

    @Override
    public final void zza(zzaw zzaw2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzaw2);
        this.zzc(7, parcel);
    }

    @Override
    public final void zza(zzfe zzfe2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzfe2);
        this.zzc(2, parcel);
    }

    @Override
    public final void zza(zzfo zzfo2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzfo2);
        this.zzc(3, parcel);
    }

    @Override
    public final void zza(zzi zzi2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzi2);
        this.zzc(9, parcel);
    }

    @Override
    public final void zza(zzl zzl2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzl2);
        this.zzc(6, parcel);
    }

    @Override
    public final void zzas(DataHolder dataHolder) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, dataHolder);
        this.zzc(1, parcel);
    }

    @Override
    public final void zzb(zzfo zzfo2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzfo2);
        this.zzc(4, parcel);
    }
}


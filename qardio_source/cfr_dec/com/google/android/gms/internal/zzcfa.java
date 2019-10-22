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
import com.google.android.gms.internal.zzcdz;
import com.google.android.gms.internal.zzcez;
import com.google.android.gms.internal.zzcfb;
import com.google.android.gms.internal.zzcfq;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.location.LocationSettingsRequest;

public final class zzcfa
extends zzeu
implements zzcez {
    zzcfa(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.internal.IGoogleLocationManagerService");
    }

    @Override
    public final void zza(zzcdz zzcdz2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcdz2);
        this.zzb(75, parcel);
    }

    @Override
    public final void zza(zzcfq zzcfq2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzcfq2);
        this.zzb(59, parcel);
    }

    @Override
    public final void zza(LocationSettingsRequest locationSettingsRequest, zzcfb zzcfb2, String string2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, locationSettingsRequest);
        zzew.zza(parcel, zzcfb2);
        parcel.writeString(string2);
        this.zzb(63, parcel);
    }

    @Override
    public final void zzbj(boolean bl) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, bl);
        this.zzb(12, parcel);
    }
}


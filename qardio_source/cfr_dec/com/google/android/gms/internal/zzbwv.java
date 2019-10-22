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
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.internal.zzbwt;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzbwv
extends zzeu
implements zzbwt {
    zzbwv(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IDailyTotalCallback");
    }

    @Override
    public final void zza(DailyTotalResult dailyTotalResult) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, dailyTotalResult);
        this.zzc(1, parcel);
    }
}


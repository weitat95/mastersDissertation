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
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.internal.zzbxw;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzbxy
extends zzeu
implements zzbxw {
    zzbxy(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IListSubscriptionsCallback");
    }

    @Override
    public final void zza(ListSubscriptionsResult listSubscriptionsResult) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, listSubscriptionsResult);
        this.zzc(1, parcel);
    }
}


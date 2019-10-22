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
import com.google.android.gms.internal.zzaqx;
import com.google.android.gms.internal.zzarr;
import com.google.android.gms.internal.zzeu;
import java.util.List;
import java.util.Map;

public final class zzars
extends zzeu
implements zzarr {
    zzars(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.analytics.internal.IAnalyticsService");
    }

    @Override
    public final void zza(Map map, long l, String string2, List<zzaqx> list) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeMap(map);
        parcel.writeLong(l);
        parcel.writeString(string2);
        parcel.writeTypedList(list);
        this.zzb(1, parcel);
    }
}


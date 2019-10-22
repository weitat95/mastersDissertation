/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzba;
import com.google.android.gms.common.zzn;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzbc
extends zzeu
implements zzba {
    zzbc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
    }

    @Override
    public final boolean zza(zzn zzn2, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzn2);
        zzew.zza(parcel, iObjectWrapper);
        zzn2 = this.zza(5, parcel);
        boolean bl = zzew.zza((Parcel)zzn2);
        zzn2.recycle();
        return bl;
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbd;
import com.google.android.gms.common.internal.zzbv;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzbe
extends zzeu
implements zzbd {
    zzbe(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ISignInButtonCreator");
    }

    @Override
    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, zzbv object) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        zzew.zza(parcel, (Parcelable)object);
        iObjectWrapper = this.zza(2, parcel);
        object = IObjectWrapper.zza.zzaq(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return object;
    }
}


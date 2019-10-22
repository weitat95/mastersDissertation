/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamite.zzm;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzn
extends zzeu
implements zzm {
    zzn(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
    }

    @Override
    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String object, int n, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        parcel.writeString((String)object);
        parcel.writeInt(n);
        zzew.zza(parcel, iObjectWrapper2);
        iObjectWrapper = this.zza(2, parcel);
        object = IObjectWrapper.zza.zzaq(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return object;
    }
}


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
import com.google.android.gms.dynamite.zzk;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzl
extends zzeu
implements zzk {
    zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoader");
    }

    @Override
    public final int zza(IObjectWrapper iObjectWrapper, String string2, boolean bl) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        parcel.writeString(string2);
        zzew.zza(parcel, bl);
        iObjectWrapper = this.zza(3, parcel);
        int n = iObjectWrapper.readInt();
        iObjectWrapper.recycle();
        return n;
    }

    @Override
    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String object, int n) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        parcel.writeString((String)object);
        parcel.writeInt(n);
        iObjectWrapper = this.zza(2, parcel);
        object = IObjectWrapper.zza.zzaq(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return object;
    }
}


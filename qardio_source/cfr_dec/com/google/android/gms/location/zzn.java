/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.location.zzm;
import com.google.android.gms.location.zzo;

public class zzn
extends zzev
implements zzm {
    public static zzm zzbc(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.location.IDeviceOrientationListener");
        if (iInterface instanceof zzm) {
            return (zzm)iInterface;
        }
        return new zzo(iBinder);
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        throw new NoSuchMethodError();
    }
}


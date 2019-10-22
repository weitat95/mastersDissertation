/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.location.zzs;
import com.google.android.gms.location.zzu;

public abstract class zzt
extends zzev
implements zzs {
    public zzt() {
        this.attachInterface((IInterface)this, "com.google.android.gms.location.ILocationListener");
    }

    public static zzs zzbe(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.location.ILocationListener");
        if (iInterface instanceof zzs) {
            return (zzs)iInterface;
        }
        return new zzu(iBinder);
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        if (n == 1) {
            this.onLocationChanged((Location)zzew.zza(parcel, Location.CREATOR));
            return true;
        }
        return false;
    }
}


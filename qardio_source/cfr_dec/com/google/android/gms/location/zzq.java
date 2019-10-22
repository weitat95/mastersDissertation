/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzp;
import com.google.android.gms.location.zzr;

public abstract class zzq
extends zzev
implements zzp {
    public static zzp zzbd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.location.ILocationCallback");
        if (iInterface instanceof zzp) {
            return (zzp)iInterface;
        }
        return new zzr(iBinder);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        switch (n) {
            default: {
                return false;
            }
            case 1: {
                this.onLocationResult(zzew.zza(parcel, LocationResult.CREATOR));
                do {
                    return true;
                    break;
                } while (true);
            }
            case 2: 
        }
        this.onLocationAvailability(zzew.zza(parcel, LocationAvailability.CREATOR));
        return true;
    }
}


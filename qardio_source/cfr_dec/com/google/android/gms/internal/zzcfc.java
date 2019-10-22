/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzcfb;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.location.LocationSettingsResult;

public abstract class zzcfc
extends zzev
implements zzcfb {
    public zzcfc() {
        this.attachInterface((IInterface)this, "com.google.android.gms.location.internal.ISettingsCallbacks");
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        if (n == 1) {
            this.zza(zzew.zza(parcel, LocationSettingsResult.CREATOR));
            return true;
        }
        return false;
    }
}


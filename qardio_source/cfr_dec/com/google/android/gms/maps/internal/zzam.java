/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.maps.internal.zzal;

public abstract class zzam
extends zzev
implements zzal {
    public zzam() {
        this.attachInterface((IInterface)this, "com.google.android.gms.maps.internal.IOnMapLoadedCallback");
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        if (n == 1) {
            this.onMapLoaded();
            parcel2.writeNoException();
            return true;
        }
        return false;
    }
}


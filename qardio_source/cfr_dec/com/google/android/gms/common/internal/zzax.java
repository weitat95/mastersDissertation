/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzaw;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzax
extends zzev
implements zzaw {
    public zzax() {
        this.attachInterface((IInterface)this, "com.google.android.gms.common.internal.IGmsCallbacks");
    }

    /*
     * Enabled aggressive block sorting
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
                this.zza(parcel.readInt(), parcel.readStrongBinder(), (Bundle)zzew.zza(parcel, Bundle.CREATOR));
                break;
            }
            case 2: {
                this.zza(parcel.readInt(), (Bundle)zzew.zza(parcel, Bundle.CREATOR));
            }
        }
        parcel2.writeNoException();
        return true;
    }
}


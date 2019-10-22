/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzdkz;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzdla
extends zzev
implements zzdkz {
    public zzdla() {
        this.attachInterface((IInterface)this, "com.google.android.gms.wallet.fragment.internal.IWalletFragmentStateListener");
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        if (n == 2) {
            this.zza(parcel.readInt(), parcel.readInt(), (Bundle)zzew.zza(parcel, Bundle.CREATOR));
            parcel2.writeNoException();
            return true;
        }
        return false;
    }
}


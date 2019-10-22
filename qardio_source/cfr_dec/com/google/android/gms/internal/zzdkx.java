/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzdkw;
import com.google.android.gms.internal.zzdky;
import com.google.android.gms.internal.zzev;

public abstract class zzdkx
extends zzev
implements zzdkw {
    public static zzdkw zzbq(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
        if (iInterface instanceof zzdkw) {
            return (zzdkw)iInterface;
        }
        return new zzdky(iBinder);
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        throw new NoSuchMethodError();
    }
}


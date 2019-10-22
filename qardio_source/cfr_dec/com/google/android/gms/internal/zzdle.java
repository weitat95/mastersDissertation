/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzk;
import com.google.android.gms.internal.zzdkw;
import com.google.android.gms.internal.zzdkx;
import com.google.android.gms.internal.zzdkz;
import com.google.android.gms.internal.zzdld;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public final class zzdle
extends zzeu
implements zzdld {
    zzdle(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
    }

    @Override
    public final zzdkw zza(IObjectWrapper iObjectWrapper, zzk object, WalletFragmentOptions walletFragmentOptions, zzdkz zzdkz2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        zzew.zza(parcel, object);
        zzew.zza(parcel, walletFragmentOptions);
        zzew.zza(parcel, zzdkz2);
        iObjectWrapper = this.zza(1, parcel);
        object = zzdkx.zzbq(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return object;
    }
}


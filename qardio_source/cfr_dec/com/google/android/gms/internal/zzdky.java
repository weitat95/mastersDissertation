/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzdkw;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public final class zzdky
extends zzeu
implements zzdkw {
    zzdky(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
    }

    @Override
    public final void initialize(WalletFragmentInitParams walletFragmentInitParams) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, walletFragmentInitParams);
        this.zzb(10, parcel);
    }

    @Override
    public final void onActivityResult(int n, int n2, Intent intent) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeInt(n);
        parcel.writeInt(n2);
        zzew.zza(parcel, (Parcelable)intent);
        this.zzb(9, parcel);
    }

    @Override
    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, (Parcelable)bundle);
        this.zzb(2, parcel);
    }

    @Override
    public final IObjectWrapper onCreateView(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, Bundle bundle) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        zzew.zza(parcel, iObjectWrapper2);
        zzew.zza(parcel, (Parcelable)bundle);
        iObjectWrapper = this.zza(3, parcel);
        iObjectWrapper2 = IObjectWrapper.zza.zzaq(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return iObjectWrapper2;
    }

    @Override
    public final void onPause() throws RemoteException {
        this.zzb(6, this.zzbe());
    }

    @Override
    public final void onResume() throws RemoteException {
        this.zzb(5, this.zzbe());
    }

    @Override
    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, (Parcelable)bundle);
        parcel = this.zza(8, parcel);
        if (parcel.readInt() != 0) {
            bundle.readFromParcel(parcel);
        }
        parcel.recycle();
    }

    @Override
    public final void onStart() throws RemoteException {
        this.zzb(4, this.zzbe());
    }

    @Override
    public final void onStop() throws RemoteException {
        this.zzb(7, this.zzbe());
    }

    @Override
    public final void setEnabled(boolean bl) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, bl);
        this.zzb(12, parcel);
    }

    @Override
    public final void updateMaskedWallet(MaskedWallet maskedWallet) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, maskedWallet);
        this.zzb(14, parcel);
    }

    @Override
    public final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, maskedWalletRequest);
        this.zzb(11, parcel);
    }

    @Override
    public final void zza(IObjectWrapper iObjectWrapper, WalletFragmentOptions walletFragmentOptions, Bundle bundle) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, iObjectWrapper);
        zzew.zza(parcel, walletFragmentOptions);
        zzew.zza(parcel, (Parcelable)bundle);
        this.zzb(1, parcel);
    }
}


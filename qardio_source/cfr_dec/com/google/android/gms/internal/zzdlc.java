/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzdlb;
import com.google.android.gms.internal.zzdlf;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;

public final class zzdlc
extends zzeu
implements zzdlb {
    zzdlc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wallet.internal.IOwService");
    }

    @Override
    public final void zza(FullWalletRequest fullWalletRequest, Bundle bundle, zzdlf zzdlf2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, fullWalletRequest);
        zzew.zza(parcel, (Parcelable)bundle);
        zzew.zza(parcel, zzdlf2);
        this.zzc(2, parcel);
    }

    @Override
    public final void zza(IsReadyToPayRequest isReadyToPayRequest, Bundle bundle, zzdlf zzdlf2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, isReadyToPayRequest);
        zzew.zza(parcel, (Parcelable)bundle);
        zzew.zza(parcel, zzdlf2);
        this.zzc(14, parcel);
    }

    @Override
    public final void zza(MaskedWalletRequest maskedWalletRequest, Bundle bundle, zzdlf zzdlf2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, maskedWalletRequest);
        zzew.zza(parcel, (Parcelable)bundle);
        zzew.zza(parcel, zzdlf2);
        this.zzc(1, parcel);
    }

    @Override
    public final void zza(String string2, String string3, Bundle bundle, zzdlf zzdlf2) throws RemoteException {
        Parcel parcel = this.zzbe();
        parcel.writeString(string2);
        parcel.writeString(string3);
        zzew.zza(parcel, (Parcelable)bundle);
        zzew.zza(parcel, zzdlf2);
        this.zzc(3, parcel);
    }
}


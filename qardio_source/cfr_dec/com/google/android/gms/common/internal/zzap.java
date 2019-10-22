/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzap
extends zzeu
implements zzan {
    zzap(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IAccountAccessor");
    }

    @Override
    public final Account getAccount() throws RemoteException {
        Parcel parcel = this.zza(2, this.zzbe());
        Account account = (Account)zzew.zza(parcel, Account.CREATOR);
        parcel.recycle();
        return account;
    }
}


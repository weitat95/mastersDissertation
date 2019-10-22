/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.PaymentData;

public interface zzdlf
extends IInterface {
    public void zza(int var1, FullWallet var2, Bundle var3) throws RemoteException;

    public void zza(int var1, MaskedWallet var2, Bundle var3) throws RemoteException;

    public void zza(int var1, boolean var2, Bundle var3) throws RemoteException;

    public void zza(Status var1, PaymentData var2, Bundle var3) throws RemoteException;

    public void zza(Status var1, boolean var2, Bundle var3) throws RemoteException;

    public void zzg(int var1, Bundle var2) throws RemoteException;
}


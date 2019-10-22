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
import com.google.android.gms.internal.zzdlf;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;

public interface zzdlb
extends IInterface {
    public void zza(FullWalletRequest var1, Bundle var2, zzdlf var3) throws RemoteException;

    public void zza(IsReadyToPayRequest var1, Bundle var2, zzdlf var3) throws RemoteException;

    public void zza(MaskedWalletRequest var1, Bundle var2, zzdlf var3) throws RemoteException;

    public void zza(String var1, String var2, Bundle var3, zzdlf var4) throws RemoteException;
}


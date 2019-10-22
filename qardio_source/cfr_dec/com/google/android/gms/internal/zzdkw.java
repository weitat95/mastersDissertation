/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public interface zzdkw
extends IInterface {
    public void initialize(WalletFragmentInitParams var1) throws RemoteException;

    public void onActivityResult(int var1, int var2, Intent var3) throws RemoteException;

    public void onCreate(Bundle var1) throws RemoteException;

    public IObjectWrapper onCreateView(IObjectWrapper var1, IObjectWrapper var2, Bundle var3) throws RemoteException;

    public void onPause() throws RemoteException;

    public void onResume() throws RemoteException;

    public void onSaveInstanceState(Bundle var1) throws RemoteException;

    public void onStart() throws RemoteException;

    public void onStop() throws RemoteException;

    public void setEnabled(boolean var1) throws RemoteException;

    public void updateMaskedWallet(MaskedWallet var1) throws RemoteException;

    public void updateMaskedWalletRequest(MaskedWalletRequest var1) throws RemoteException;

    public void zza(IObjectWrapper var1, WalletFragmentOptions var2, Bundle var3) throws RemoteException;
}


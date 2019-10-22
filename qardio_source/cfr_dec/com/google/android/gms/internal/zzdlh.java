/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.internal.zzdlj;
import com.google.android.gms.internal.zzdlk;
import com.google.android.gms.internal.zzdll;
import com.google.android.gms.internal.zzdln;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.Payments;

@SuppressLint(value={"MissingRemoteException"})
public final class zzdlh
implements Payments {
    @Override
    public final void changeMaskedWallet(GoogleApiClient googleApiClient, String string2, String string3, int n) {
        googleApiClient.zzd(new zzdll(this, googleApiClient, string2, string3, n));
    }

    @Override
    public final PendingResult<BooleanResult> isReadyToPay(GoogleApiClient googleApiClient, IsReadyToPayRequest isReadyToPayRequest) {
        return googleApiClient.zzd(new zzdln(this, googleApiClient, isReadyToPayRequest));
    }

    @Override
    public final void loadFullWallet(GoogleApiClient googleApiClient, FullWalletRequest fullWalletRequest, int n) {
        googleApiClient.zzd(new zzdlk(this, googleApiClient, fullWalletRequest, n));
    }

    @Override
    public final void loadMaskedWallet(GoogleApiClient googleApiClient, MaskedWalletRequest maskedWalletRequest, int n) {
        googleApiClient.zzd(new zzdlj(this, googleApiClient, maskedWalletRequest, n));
    }
}


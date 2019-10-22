/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.internal.zzdlh;
import com.google.android.gms.internal.zzdlo;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.Wallet;

final class zzdlk
extends Wallet.zzb {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ FullWalletRequest zzlfm;

    zzdlk(zzdlh zzdlh2, GoogleApiClient googleApiClient, FullWalletRequest fullWalletRequest, int n) {
        this.zzlfm = fullWalletRequest;
        this.val$requestCode = n;
        super(googleApiClient);
    }

    @Override
    protected final void zza(zzdlo zzdlo2) {
        zzdlo2.zza(this.zzlfm, this.val$requestCode);
        ((BasePendingResult)this).setResult(Status.zzfni);
    }
}


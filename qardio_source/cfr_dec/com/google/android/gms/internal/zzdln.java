/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzdlh;
import com.google.android.gms.internal.zzdlo;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.Wallet;

final class zzdln
extends Wallet.zza<BooleanResult> {
    private /* synthetic */ IsReadyToPayRequest zzldl;

    zzdln(zzdlh zzdlh2, GoogleApiClient googleApiClient, IsReadyToPayRequest isReadyToPayRequest) {
        this.zzldl = isReadyToPayRequest;
        super(googleApiClient);
    }

    @Override
    protected final void zza(zzdlo zzdlo2) {
        zzdlo2.zza(this.zzldl, this);
    }

    @Override
    protected final /* synthetic */ Result zzb(Status status) {
        return new BooleanResult(status, false);
    }
}


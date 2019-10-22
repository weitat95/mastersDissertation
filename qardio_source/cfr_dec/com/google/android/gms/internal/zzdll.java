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
import com.google.android.gms.wallet.Wallet;

final class zzdll
extends Wallet.zzb {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ String zzlfn;
    private /* synthetic */ String zzlfo;

    zzdll(zzdlh zzdlh2, GoogleApiClient googleApiClient, String string2, String string3, int n) {
        this.zzlfn = string2;
        this.zzlfo = string3;
        this.val$requestCode = n;
        super(googleApiClient);
    }

    @Override
    protected final void zza(zzdlo zzdlo2) {
        zzdlo2.zzc(this.zzlfn, this.zzlfo, this.val$requestCode);
        ((BasePendingResult)this).setResult(Status.zzfni);
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.internal.zzbw;
import com.google.android.gms.wearable.internal.zzcg;
import com.google.android.gms.wearable.internal.zzhg;
import com.google.android.gms.wearable.internal.zzn;

final class zzbx
extends zzn<DataApi.DataItemResult> {
    private /* synthetic */ PutDataRequest zzljr;

    zzbx(zzbw zzbw2, GoogleApiClient googleApiClient, PutDataRequest putDataRequest) {
        this.zzljr = putDataRequest;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        ((zzhg)zzb2).zza(this, this.zzljr);
    }

    @Override
    public final /* synthetic */ Result zzb(Status status) {
        return new zzcg(status, null);
    }
}


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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.internal.zzbw;
import com.google.android.gms.wearable.internal.zzek;
import com.google.android.gms.wearable.internal.zzep;
import com.google.android.gms.wearable.internal.zzgw;
import com.google.android.gms.wearable.internal.zzhg;
import com.google.android.gms.wearable.internal.zzn;

final class zzbz
extends zzn<DataItemBuffer> {
    zzbz(zzbw zzbw2, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        ((zzep)((zzhg)zzb2).zzakn()).zza(new zzgw(this));
    }

    @Override
    protected final /* synthetic */ Result zzb(Status status) {
        return new DataItemBuffer(DataHolder.zzca(status.getStatusCode()));
    }
}


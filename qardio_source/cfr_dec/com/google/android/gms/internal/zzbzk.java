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
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzaj;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.internal.zzbwb;
import com.google.android.gms.internal.zzbwe;
import com.google.android.gms.internal.zzbxq;
import com.google.android.gms.internal.zzbxw;
import com.google.android.gms.internal.zzbzi;
import com.google.android.gms.internal.zzbzj;
import com.google.android.gms.internal.zzbzo;

final class zzbzk
extends zzbwe<ListSubscriptionsResult> {
    private /* synthetic */ DataType zzhfd;

    zzbzk(zzbzi zzbzi2, GoogleApiClient googleApiClient, DataType dataType) {
        this.zzhfd = dataType;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzb2 = (zzbwb)zzb2;
        zzbzo zzbzo2 = new zzbzo(this, null);
        ((zzbxq)((zzd)((Object)zzb2)).zzakn()).zza(new zzaj(this.zzhfd, zzbzo2));
    }

    @Override
    protected final /* synthetic */ Result zzb(Status status) {
        return ListSubscriptionsResult.zzae(status);
    }
}


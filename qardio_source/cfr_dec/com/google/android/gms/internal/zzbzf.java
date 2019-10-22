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
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzg;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.internal.zzbvv;
import com.google.android.gms.internal.zzbvy;
import com.google.android.gms.internal.zzbwt;
import com.google.android.gms.internal.zzbxo;
import com.google.android.gms.internal.zzbyy;
import com.google.android.gms.internal.zzbzg;

final class zzbzf
extends zzbvy<DailyTotalResult> {
    private /* synthetic */ DataType zzhfd;
    private /* synthetic */ boolean zzhfe;

    zzbzf(zzbyy zzbyy2, GoogleApiClient googleApiClient, DataType dataType, boolean bl) {
        this.zzhfd = dataType;
        this.zzhfe = bl;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzb2 = (zzbvv)zzb2;
        zzg zzg2 = new zzg(new zzbzg(this), this.zzhfd, this.zzhfe);
        ((zzbxo)((zzd)((Object)zzb2)).zzakn()).zza(zzg2);
    }

    @Override
    protected final /* synthetic */ Result zzb(Status status) {
        return DailyTotalResult.zza(status, this.zzhfd);
    }
}


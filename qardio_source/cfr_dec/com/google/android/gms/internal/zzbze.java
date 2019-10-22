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
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.internal.zzbvv;
import com.google.android.gms.internal.zzbvy;
import com.google.android.gms.internal.zzbww;
import com.google.android.gms.internal.zzbxo;
import com.google.android.gms.internal.zzbyy;
import com.google.android.gms.internal.zzbyz;
import com.google.android.gms.internal.zzbzh;
import java.util.List;

final class zzbze
extends zzbvy<DataReadResult> {
    private /* synthetic */ DataReadRequest zzhfc;

    zzbze(zzbyy zzbyy2, GoogleApiClient googleApiClient, DataReadRequest dataReadRequest) {
        this.zzhfc = dataReadRequest;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzb2 = (zzbvv)zzb2;
        zzbzh zzbzh2 = new zzbzh(this, null);
        ((zzbxo)((zzd)((Object)zzb2)).zzakn()).zza(new DataReadRequest(this.zzhfc, zzbzh2));
    }

    @Override
    protected final /* synthetic */ Result zzb(Status status) {
        return DataReadResult.zza(status, this.zzhfc.getDataTypes(), this.zzhfc.getDataSources());
    }
}


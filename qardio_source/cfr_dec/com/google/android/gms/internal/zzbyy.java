/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.HistoryApi;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.zzb;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.internal.zzbyz;
import com.google.android.gms.internal.zzbza;
import com.google.android.gms.internal.zzbze;
import com.google.android.gms.internal.zzbzf;
import java.util.List;

public final class zzbyy
implements HistoryApi {
    private final PendingResult<DailyTotalResult> zza(GoogleApiClient googleApiClient, DataType dataType, boolean bl) {
        return googleApiClient.zzd(new zzbzf(this, googleApiClient, dataType, bl));
    }

    @Override
    public final PendingResult<Status> deleteData(GoogleApiClient googleApiClient, DataDeleteRequest dataDeleteRequest) {
        return googleApiClient.zzd(new zzbza(this, googleApiClient, dataDeleteRequest));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final PendingResult<Status> insertData(GoogleApiClient googleApiClient, DataSet dataSet) {
        zzbq.checkNotNull(dataSet, "Must set the data set");
        boolean bl = !dataSet.getDataPoints().isEmpty();
        zzbq.zza(bl, "Cannot use an empty data set");
        zzbq.checkNotNull(dataSet.getDataSource().zzaql(), "Must set the app package name for the data source");
        return googleApiClient.zzd(new zzbyz(this, googleApiClient, dataSet, false));
    }

    @Override
    public final PendingResult<DailyTotalResult> readDailyTotal(GoogleApiClient googleApiClient, DataType dataType) {
        return this.zza(googleApiClient, dataType, false);
    }

    @Override
    public final PendingResult<DataReadResult> readData(GoogleApiClient googleApiClient, DataReadRequest dataReadRequest) {
        return googleApiClient.zzd(new zzbze(this, googleApiClient, dataReadRequest));
    }
}


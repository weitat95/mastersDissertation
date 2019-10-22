/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.fitness;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResult;

public interface HistoryApi {
    public PendingResult<Status> deleteData(GoogleApiClient var1, DataDeleteRequest var2);

    public PendingResult<Status> insertData(GoogleApiClient var1, DataSet var2);

    public PendingResult<DailyTotalResult> readDailyTotal(GoogleApiClient var1, DataType var2);

    public PendingResult<DataReadResult> readData(GoogleApiClient var1, DataReadRequest var2);
}


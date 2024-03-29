/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.fitness;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

public interface RecordingApi {
    public PendingResult<ListSubscriptionsResult> listSubscriptions(GoogleApiClient var1, DataType var2);

    public PendingResult<Status> subscribe(GoogleApiClient var1, DataType var2);
}


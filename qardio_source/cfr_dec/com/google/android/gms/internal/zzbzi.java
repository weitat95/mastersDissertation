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
import com.google.android.gms.fitness.RecordingApi;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.internal.zzbzk;
import com.google.android.gms.internal.zzbzl;

public final class zzbzi
implements RecordingApi {
    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, Subscription subscription) {
        return googleApiClient.zzd(new zzbzl(this, googleApiClient, subscription));
    }

    @Override
    public final PendingResult<ListSubscriptionsResult> listSubscriptions(GoogleApiClient googleApiClient, DataType dataType) {
        return googleApiClient.zzd(new zzbzk(this, googleApiClient, dataType));
    }

    @Override
    public final PendingResult<Status> subscribe(GoogleApiClient googleApiClient, DataType dataType) {
        return this.zza(googleApiClient, new Subscription.zza().zza(dataType).zzaqr());
    }
}


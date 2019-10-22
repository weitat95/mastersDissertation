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
import com.google.android.gms.fitness.ConfigApi;
import com.google.android.gms.internal.zzbyt;

public final class zzbyq
implements ConfigApi {
    @Override
    public final PendingResult<Status> disableFit(GoogleApiClient googleApiClient) {
        return googleApiClient.zze(new zzbyt(this, googleApiClient));
    }
}


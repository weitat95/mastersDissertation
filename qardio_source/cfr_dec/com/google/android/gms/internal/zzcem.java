/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

abstract class zzcem
extends LocationServices.zza<Status> {
    public zzcem(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}


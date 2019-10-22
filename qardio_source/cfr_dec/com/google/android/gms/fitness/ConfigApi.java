/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.fitness;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

public interface ConfigApi {
    public PendingResult<Status> disableFit(GoogleApiClient var1);
}


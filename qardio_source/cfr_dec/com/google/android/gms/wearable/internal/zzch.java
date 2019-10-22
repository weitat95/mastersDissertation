/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;

public final class zzch
implements DataApi.DeleteDataItemsResult {
    private final Status mStatus;
    private final int zzljx;

    public zzch(Status status, int n) {
        this.mStatus = status;
        this.zzljx = n;
    }

    @Override
    public final Status getStatus() {
        return this.mStatus;
    }
}


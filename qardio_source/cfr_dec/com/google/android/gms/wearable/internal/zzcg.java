/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;

public final class zzcg
implements DataApi.DataItemResult {
    private final Status mStatus;
    private final DataItem zzljw;

    public zzcg(Status status, DataItem dataItem) {
        this.mStatus = status;
        this.zzljw = dataItem;
    }

    @Override
    public final Status getStatus() {
        return this.mStatus;
    }
}


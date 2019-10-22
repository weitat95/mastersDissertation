/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageApi;

public final class zzey
implements MessageApi.SendMessageResult {
    private final Status mStatus;
    private final int zzgiq;

    public zzey(Status status, int n) {
        this.mStatus = status;
        this.zzgiq = n;
    }

    @Override
    public final Status getStatus() {
        return this.mStatus;
    }
}


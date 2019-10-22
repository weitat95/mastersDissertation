/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzg;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.internal.zzcy;

public class DataEventBuffer
extends zzg<DataEvent>
implements Result {
    private final Status mStatus;

    public DataEventBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.mStatus = new Status(dataHolder.getStatusCode());
    }

    @Override
    public Status getStatus() {
        return this.mStatus;
    }

    @Override
    protected final String zzaka() {
        return "path";
    }

    @Override
    protected final /* synthetic */ Object zzl(int n, int n2) {
        return new zzcy(this.zzfqt, n, n2);
    }
}


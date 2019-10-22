/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.internal.zzgm;

final class zzgw
extends zzgm<DataItemBuffer> {
    public zzgw(zzn<DataItemBuffer> zzn2) {
        super(zzn2);
    }

    @Override
    public final void zzat(DataHolder dataHolder) {
        this.zzav(new DataItemBuffer(dataHolder));
    }
}


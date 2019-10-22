/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.wearable.DataItemAsset;

public final class zzdb
extends zzc
implements DataItemAsset {
    public zzdb(DataHolder dataHolder, int n) {
        super(dataHolder, n);
    }

    @Override
    public final String getDataItemKey() {
        return this.getString("asset_key");
    }

    @Override
    public final String getId() {
        return this.getString("asset_id");
    }
}


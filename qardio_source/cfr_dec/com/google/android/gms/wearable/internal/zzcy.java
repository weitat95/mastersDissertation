/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.internal.zzdf;

public final class zzcy
extends zzc
implements DataEvent {
    private final int zzhwi;

    public zzcy(DataHolder dataHolder, int n, int n2) {
        super(dataHolder, n);
        this.zzhwi = n2;
    }

    @Override
    public final DataItem getDataItem() {
        return new zzdf(this.zzfqt, this.zzfvx, this.zzhwi);
    }

    @Override
    public final int getType() {
        return this.getInteger("event_type");
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String toString() {
        String string2 = this.getType() == 1 ? "changed" : (this.getType() == 2 ? "deleted" : "unknown");
        String string3 = String.valueOf(this.getDataItem());
        return new StringBuilder(String.valueOf(string2).length() + 32 + String.valueOf(string3).length()).append("DataEventRef{ type=").append(string2).append(", dataitem=").append(string3).append(" }").toString();
    }
}


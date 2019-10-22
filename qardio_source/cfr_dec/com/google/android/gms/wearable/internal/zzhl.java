/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;

final class zzhl
implements zzcl<DataApi.DataListener> {
    private /* synthetic */ DataHolder zzlhn;

    zzhl(DataHolder dataHolder) {
        this.zzlhn = dataHolder;
    }

    @Override
    public final void zzahz() {
        this.zzlhn.close();
    }

    @Override
    public final /* synthetic */ void zzu(Object object) {
        object = (DataApi.DataListener)object;
        try {
            object.onDataChanged(new DataEventBuffer(this.zzlhn));
            return;
        }
        finally {
            this.zzlhn.close();
        }
    }
}


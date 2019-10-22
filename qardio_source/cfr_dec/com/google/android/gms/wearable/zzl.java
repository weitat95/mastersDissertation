/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.WearableListenerService;

final class zzl
implements Runnable {
    private /* synthetic */ DataHolder zzlhn;
    private /* synthetic */ WearableListenerService.zzd zzlho;

    zzl(WearableListenerService.zzd zzd2, DataHolder dataHolder) {
        this.zzlho = zzd2;
        this.zzlhn = dataHolder;
    }

    @Override
    public final void run() {
        DataEventBuffer dataEventBuffer = new DataEventBuffer(this.zzlhn);
        try {
            this.zzlho.WearableListenerService.this.onDataChanged(dataEventBuffer);
            return;
        }
        finally {
            dataEventBuffer.release();
        }
    }
}


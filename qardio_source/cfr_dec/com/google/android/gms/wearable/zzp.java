/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;
import java.util.List;

final class zzp
implements Runnable {
    private /* synthetic */ WearableListenerService.zzd zzlho;
    private /* synthetic */ List zzlhr;

    zzp(WearableListenerService.zzd zzd2, List list) {
        this.zzlho = zzd2;
        this.zzlhr = list;
    }

    @Override
    public final void run() {
        this.zzlho.WearableListenerService.this.onConnectedNodes(this.zzlhr);
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzfo;

final class zzn
implements Runnable {
    private /* synthetic */ WearableListenerService.zzd zzlho;
    private /* synthetic */ zzfo zzlhq;

    zzn(WearableListenerService.zzd zzd2, zzfo zzfo2) {
        this.zzlho = zzd2;
        this.zzlhq = zzfo2;
    }

    @Override
    public final void run() {
        this.zzlho.WearableListenerService.this.onPeerConnected(this.zzlhq);
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzfe;

final class zzm
implements Runnable {
    private /* synthetic */ WearableListenerService.zzd zzlho;
    private /* synthetic */ zzfe zzlhp;

    zzm(WearableListenerService.zzd zzd2, zzfe zzfe2) {
        this.zzlho = zzd2;
        this.zzlhp = zzfe2;
    }

    @Override
    public final void run() {
        this.zzlho.WearableListenerService.this.onMessageReceived(this.zzlhp);
    }
}


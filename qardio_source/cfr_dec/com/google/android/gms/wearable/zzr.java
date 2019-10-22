/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzl;
import com.google.android.gms.wearable.zzd;

final class zzr
implements Runnable {
    private /* synthetic */ WearableListenerService.zzd zzlho;
    private /* synthetic */ zzl zzlht;

    zzr(WearableListenerService.zzd zzd2, zzl zzl2) {
        this.zzlho = zzd2;
        this.zzlht = zzl2;
    }

    @Override
    public final void run() {
        this.zzlho.WearableListenerService.this.onNotificationReceived((zzd)((Object)this.zzlht));
    }
}


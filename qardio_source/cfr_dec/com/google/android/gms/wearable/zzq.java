/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzah;

final class zzq
implements Runnable {
    private /* synthetic */ WearableListenerService.zzd zzlho;
    private /* synthetic */ zzah zzlhs;

    zzq(WearableListenerService.zzd zzd2, zzah zzah2) {
        this.zzlho = zzd2;
        this.zzlhs = zzah2;
    }

    @Override
    public final void run() {
        this.zzlho.WearableListenerService.this.onCapabilityChanged(this.zzlhs);
    }
}


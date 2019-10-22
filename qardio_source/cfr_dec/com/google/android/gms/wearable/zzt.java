/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzaw;

final class zzt
implements Runnable {
    private /* synthetic */ WearableListenerService.zzd zzlho;
    private /* synthetic */ zzaw zzlhv;

    zzt(WearableListenerService.zzd zzd2, zzaw zzaw2) {
        this.zzlho = zzd2;
        this.zzlhv = zzaw2;
    }

    @Override
    public final void run() {
        this.zzlhv.zza(this.zzlho.WearableListenerService.this);
        this.zzlhv.zza(WearableListenerService.zzc(this.zzlho.WearableListenerService.this));
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.zzb;

final class zzs
implements Runnable {
    private /* synthetic */ WearableListenerService.zzd zzlho;
    private /* synthetic */ zzi zzlhu;

    zzs(WearableListenerService.zzd zzd2, zzi zzi2) {
        this.zzlho = zzd2;
        this.zzlhu = zzi2;
    }

    @Override
    public final void run() {
        this.zzlho.WearableListenerService.this.onEntityUpdate((zzb)((Object)this.zzlhu));
    }
}


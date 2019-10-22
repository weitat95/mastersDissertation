/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.Intent
 */
package com.google.android.gms.iid;

import android.content.BroadcastReceiver;
import android.content.Intent;
import com.google.android.gms.iid.zze;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

final class zzd {
    final Intent intent;
    private final BroadcastReceiver.PendingResult zzieu;
    private boolean zziev = false;
    private final ScheduledFuture<?> zziew;

    zzd(Intent intent, BroadcastReceiver.PendingResult pendingResult, ScheduledExecutorService scheduledExecutorService) {
        this.intent = intent;
        this.zzieu = pendingResult;
        this.zziew = scheduledExecutorService.schedule(new zze(this, intent), 9500L, TimeUnit.MILLISECONDS);
    }

    final void finish() {
        synchronized (this) {
            if (!this.zziev) {
                this.zzieu.finish();
                this.zziew.cancel(false);
                this.zziev = true;
            }
            return;
        }
    }
}


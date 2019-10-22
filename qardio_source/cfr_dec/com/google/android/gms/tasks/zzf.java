/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zze;

final class zzf
implements Runnable {
    private /* synthetic */ Task zzkua;
    private /* synthetic */ zze zzkue;

    zzf(zze zze2, Task task) {
        this.zzkue = zze2;
        this.zzkua = task;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        Object object = zze.zza(this.zzkue);
        synchronized (object) {
            if (zze.zzb(this.zzkue) != null) {
                zze.zzb(this.zzkue).onComplete(this.zzkua);
            }
            return;
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzg;

final class zzh
implements Runnable {
    private /* synthetic */ Task zzkua;
    private /* synthetic */ zzg zzkug;

    zzh(zzg zzg2, Task task) {
        this.zzkug = zzg2;
        this.zzkua = task;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        Object object = zzg.zza(this.zzkug);
        synchronized (object) {
            if (zzg.zzb(this.zzkug) != null) {
                zzg.zzb(this.zzkug).onFailure(this.zzkua.getException());
            }
            return;
        }
    }
}


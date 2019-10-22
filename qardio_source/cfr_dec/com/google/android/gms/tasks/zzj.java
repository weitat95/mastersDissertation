/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzi;

final class zzj
implements Runnable {
    private /* synthetic */ Task zzkua;
    private /* synthetic */ zzi zzkui;

    zzj(zzi zzi2, Task task) {
        this.zzkui = zzi2;
        this.zzkua = task;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        Object object = zzi.zza(this.zzkui);
        synchronized (object) {
            if (zzi.zzb(this.zzkui) != null) {
                zzi.zzb(this.zzkui).onSuccess(this.zzkua.getResult());
            }
            return;
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzh;
import com.google.android.gms.tasks.zzk;
import java.util.concurrent.Executor;

final class zzg<TResult>
implements zzk<TResult> {
    private final Object mLock = new Object();
    private final Executor zzkev;
    private OnFailureListener zzkuf;

    public zzg(Executor executor, OnFailureListener onFailureListener) {
        this.zzkev = executor;
        this.zzkuf = onFailureListener;
    }

    static /* synthetic */ Object zza(zzg zzg2) {
        return zzg2.mLock;
    }

    static /* synthetic */ OnFailureListener zzb(zzg zzg2) {
        return zzg2.zzkuf;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void onComplete(Task<TResult> task) {
        if (task.isSuccessful()) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzkuf == null) {
                return;
            }
        }
        this.zzkev.execute(new zzh(this, task));
    }
}


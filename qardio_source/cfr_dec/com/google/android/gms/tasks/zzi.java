/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzj;
import com.google.android.gms.tasks.zzk;
import java.util.concurrent.Executor;

final class zzi<TResult>
implements zzk<TResult> {
    private final Object mLock = new Object();
    private final Executor zzkev;
    private OnSuccessListener<? super TResult> zzkuh;

    public zzi(Executor executor, OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzkev = executor;
        this.zzkuh = onSuccessListener;
    }

    static /* synthetic */ Object zza(zzi zzi2) {
        return zzi2.mLock;
    }

    static /* synthetic */ OnSuccessListener zzb(zzi zzi2) {
        return zzi2.zzkuh;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void onComplete(Task<TResult> task) {
        if (!task.isSuccessful()) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzkuh == null) {
                return;
            }
        }
        this.zzkev.execute(new zzj(this, task));
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzf;
import com.google.android.gms.tasks.zzk;
import java.util.concurrent.Executor;

final class zze<TResult>
implements zzk<TResult> {
    private final Object mLock = new Object();
    private final Executor zzkev;
    private OnCompleteListener<TResult> zzkud;

    public zze(Executor executor, OnCompleteListener<TResult> onCompleteListener) {
        this.zzkev = executor;
        this.zzkud = onCompleteListener;
    }

    static /* synthetic */ Object zza(zze zze2) {
        return zze2.mLock;
    }

    static /* synthetic */ OnCompleteListener zzb(zze zze2) {
        return zze2.zzkud;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void onComplete(Task<TResult> task) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzkud == null) {
                return;
            }
        }
        this.zzkev.execute(new zzf(this, task));
    }
}


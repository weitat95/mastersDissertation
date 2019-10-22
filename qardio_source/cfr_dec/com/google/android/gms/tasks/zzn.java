/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zze;
import com.google.android.gms.tasks.zzg;
import com.google.android.gms.tasks.zzi;
import com.google.android.gms.tasks.zzk;
import com.google.android.gms.tasks.zzl;
import java.util.concurrent.Executor;

final class zzn<TResult>
extends Task<TResult> {
    private final Object mLock = new Object();
    private final zzl<TResult> zzkun = new zzl();
    private boolean zzkuo;
    private TResult zzkup;
    private Exception zzkuq;

    zzn() {
    }

    private final void zzbjk() {
        zzbq.zza(this.zzkuo, "Task is not yet complete");
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzbjl() {
        boolean bl = !this.zzkuo;
        zzbq.zza(bl, "Task is already complete");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzbjm() {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.zzkuo) {
                return;
            }
        }
        this.zzkun.zzb(this);
    }

    @Override
    public final Task<TResult> addOnCompleteListener(Executor executor, OnCompleteListener<TResult> onCompleteListener) {
        this.zzkun.zza(new zze<TResult>(executor, onCompleteListener));
        this.zzbjm();
        return this;
    }

    @Override
    public final Task<TResult> addOnFailureListener(Executor executor, OnFailureListener onFailureListener) {
        this.zzkun.zza(new zzg(executor, onFailureListener));
        this.zzbjm();
        return this;
    }

    @Override
    public final Task<TResult> addOnSuccessListener(Executor executor, OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzkun.zza(new zzi<TResult>(executor, onSuccessListener));
        this.zzbjm();
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final Exception getException() {
        Object object = this.mLock;
        synchronized (object) {
            return this.zzkuq;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final TResult getResult() {
        Object object = this.mLock;
        synchronized (object) {
            this.zzbjk();
            if (this.zzkuq != null) {
                throw new RuntimeExecutionException(this.zzkuq);
            }
            TResult TResult = this.zzkup;
            return TResult;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final boolean isComplete() {
        Object object = this.mLock;
        synchronized (object) {
            return this.zzkuo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final boolean isSuccessful() {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.zzkuo) return false;
            if (this.zzkuq != null) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setException(Exception exception) {
        zzbq.checkNotNull(exception, "Exception must not be null");
        Object object = this.mLock;
        synchronized (object) {
            this.zzbjl();
            this.zzkuo = true;
            this.zzkuq = exception;
        }
        this.zzkun.zzb(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setResult(TResult TResult) {
        Object object = this.mLock;
        synchronized (object) {
            this.zzbjl();
            this.zzkuo = true;
            this.zzkup = TResult;
        }
        this.zzkun.zzb(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean trySetException(Exception exception) {
        zzbq.checkNotNull(exception, "Exception must not be null");
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzkuo) {
                return false;
            }
            this.zzkuo = true;
            this.zzkuq = exception;
        }
        this.zzkun.zzb(this);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean trySetResult(TResult TResult) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzkuo) {
                return false;
            }
            this.zzkuo = true;
            this.zzkup = TResult;
        }
        this.zzkun.zzb(this);
        return true;
    }
}


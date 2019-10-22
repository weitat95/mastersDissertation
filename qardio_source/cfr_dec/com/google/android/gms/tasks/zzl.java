/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzk;
import java.util.ArrayDeque;
import java.util.Queue;

final class zzl<TResult> {
    private final Object mLock = new Object();
    private Queue<zzk<TResult>> zzkuj;
    private boolean zzkuk;

    zzl() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zza(zzk<TResult> zzk2) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzkuj == null) {
                this.zzkuj = new ArrayDeque<zzk<TResult>>();
            }
            this.zzkuj.add(zzk2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzb(Task<TResult> task) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzkuj == null || this.zzkuk) {
                return;
            }
            this.zzkuk = true;
        }
        do {
            zzk<TResult> zzk2;
            object = this.mLock;
            synchronized (object) {
                zzk2 = this.zzkuj.poll();
                if (zzk2 == null) {
                    this.zzkuk = false;
                    return;
                }
            }
            zzk2.onComplete(task);
        } while (true);
    }
}


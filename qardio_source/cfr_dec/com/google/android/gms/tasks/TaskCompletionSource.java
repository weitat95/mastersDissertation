/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzn;

public class TaskCompletionSource<TResult> {
    private final zzn<TResult> zzkul = new zzn();

    public Task<TResult> getTask() {
        return this.zzkul;
    }

    public void setException(Exception exception) {
        this.zzkul.setException(exception);
    }

    public void setResult(TResult TResult) {
        this.zzkul.setResult(TResult);
    }

    public boolean trySetException(Exception exception) {
        return this.zzkul.trySetException(exception);
    }

    public boolean trySetResult(TResult TResult) {
        return this.zzkul.trySetResult(TResult);
    }
}


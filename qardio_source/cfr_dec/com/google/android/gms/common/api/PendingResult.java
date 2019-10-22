/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import java.util.concurrent.TimeUnit;

public abstract class PendingResult<R extends Result> {
    public abstract R await();

    public abstract R await(long var1, TimeUnit var3);

    public abstract void cancel();

    public abstract boolean isCanceled();

    public abstract void setResultCallback(ResultCallback<? super R> var1);

    public void zza(zza zza2) {
        throw new UnsupportedOperationException();
    }

    public Integer zzagv() {
        throw new UnsupportedOperationException();
    }

    public static interface zza {
        public void zzr(Status var1);
    }

}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.analytics;

import android.util.Log;
import com.google.android.gms.analytics.zzj;
import java.util.concurrent.FutureTask;

final class zzl
extends FutureTask<T> {
    private /* synthetic */ zzj.zza zzdqd;

    zzl(zzj.zza zza2, Runnable runnable, Object object) {
        this.zzdqd = zza2;
        super(runnable, object);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected final void setException(Throwable throwable) {
        Object object = zzj.zzb(this.zzdqd.zzj.this);
        if (object != null) {
            object.uncaughtException(Thread.currentThread(), throwable);
        } else if (Log.isLoggable((String)"GAv4", (int)6)) {
            object = String.valueOf(throwable);
            Log.e((String)"GAv4", (String)new StringBuilder(String.valueOf(object).length() + 37).append("MeasurementExecutor: job failed with ").append((String)object).toString());
        }
        super.setException(throwable);
    }
}


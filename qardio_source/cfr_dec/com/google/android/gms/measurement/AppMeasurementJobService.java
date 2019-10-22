/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.job.JobParameters
 *  android.app.job.JobService
 *  android.content.Intent
 */
package com.google.android.gms.measurement;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import com.google.android.gms.internal.zzcla;
import com.google.android.gms.internal.zzcle;

@TargetApi(value=24)
public final class AppMeasurementJobService
extends JobService
implements zzcle {
    private zzcla<AppMeasurementJobService> zziwq;

    private final zzcla<AppMeasurementJobService> zzawh() {
        if (this.zziwq == null) {
            this.zziwq = new zzcla<AppMeasurementJobService>(this);
        }
        return this.zziwq;
    }

    @Override
    public final boolean callServiceStopSelfResult(int n) {
        throw new UnsupportedOperationException();
    }

    public final void onCreate() {
        super.onCreate();
        this.zzawh().onCreate();
    }

    public final void onDestroy() {
        this.zzawh().onDestroy();
        super.onDestroy();
    }

    public final void onRebind(Intent intent) {
        this.zzawh().onRebind(intent);
    }

    public final boolean onStartJob(JobParameters jobParameters) {
        return this.zzawh().onStartJob(jobParameters);
    }

    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public final boolean onUnbind(Intent intent) {
        return this.zzawh().onUnbind(intent);
    }

    @TargetApi(value=24)
    @Override
    public final void zza(JobParameters jobParameters, boolean bl) {
        this.jobFinished(jobParameters, false);
    }

    @Override
    public final void zzm(Intent intent) {
    }
}


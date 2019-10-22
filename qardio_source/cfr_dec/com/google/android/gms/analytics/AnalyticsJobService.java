/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.job.JobParameters
 *  android.app.job.JobService
 *  android.content.Intent
 */
package com.google.android.gms.analytics;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import com.google.android.gms.internal.zzasd;
import com.google.android.gms.internal.zzasg;

@TargetApi(value=24)
public final class AnalyticsJobService
extends JobService
implements zzasg {
    private zzasd<AnalyticsJobService> zzdoj;

    private final zzasd<AnalyticsJobService> zzuo() {
        if (this.zzdoj == null) {
            this.zzdoj = new zzasd<AnalyticsJobService>(this);
        }
        return this.zzdoj;
    }

    @Override
    public final boolean callServiceStopSelfResult(int n) {
        return this.stopSelfResult(n);
    }

    public final void onCreate() {
        super.onCreate();
        this.zzuo().onCreate();
    }

    public final void onDestroy() {
        this.zzuo().onDestroy();
        super.onDestroy();
    }

    public final int onStartCommand(Intent intent, int n, int n2) {
        return this.zzuo().onStartCommand(intent, n, n2);
    }

    public final boolean onStartJob(JobParameters jobParameters) {
        return this.zzuo().onStartJob(jobParameters);
    }

    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @TargetApi(value=24)
    @Override
    public final void zza(JobParameters jobParameters, boolean bl) {
        this.jobFinished(jobParameters, false);
    }
}


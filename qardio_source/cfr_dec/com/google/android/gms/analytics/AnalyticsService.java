/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.app.job.JobParameters
 *  android.content.Intent
 *  android.os.IBinder
 */
package com.google.android.gms.analytics;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.internal.zzasd;
import com.google.android.gms.internal.zzasg;

public final class AnalyticsService
extends Service
implements zzasg {
    private zzasd<AnalyticsService> zzdoj;

    private final zzasd<AnalyticsService> zzuo() {
        if (this.zzdoj == null) {
            this.zzdoj = new zzasd<AnalyticsService>(this);
        }
        return this.zzdoj;
    }

    @Override
    public final boolean callServiceStopSelfResult(int n) {
        return this.stopSelfResult(n);
    }

    public final IBinder onBind(Intent intent) {
        this.zzuo();
        return null;
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

    @Override
    public final void zza(JobParameters jobParameters, boolean bl) {
        throw new UnsupportedOperationException();
    }
}


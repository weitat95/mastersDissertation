/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.app.job.JobParameters
 *  android.content.Intent
 *  android.os.IBinder
 */
package com.google.android.gms.measurement;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.internal.zzcla;
import com.google.android.gms.internal.zzcle;
import com.google.android.gms.measurement.AppMeasurementReceiver;

public final class AppMeasurementService
extends Service
implements zzcle {
    private zzcla<AppMeasurementService> zziwq;

    private final zzcla<AppMeasurementService> zzawh() {
        if (this.zziwq == null) {
            this.zziwq = new zzcla<AppMeasurementService>(this);
        }
        return this.zziwq;
    }

    @Override
    public final boolean callServiceStopSelfResult(int n) {
        return this.stopSelfResult(n);
    }

    public final IBinder onBind(Intent intent) {
        return this.zzawh().onBind(intent);
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

    public final int onStartCommand(Intent intent, int n, int n2) {
        return this.zzawh().onStartCommand(intent, n, n2);
    }

    public final boolean onUnbind(Intent intent) {
        return this.zzawh().onUnbind(intent);
    }

    @Override
    public final void zza(JobParameters jobParameters, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void zzm(Intent intent) {
        AppMeasurementReceiver.completeWakefulIntent(intent);
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.job.JobParameters
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.os.PersistableBundle
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PersistableBundle;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcir;
import com.google.android.gms.internal.zzclb;
import com.google.android.gms.internal.zzclc;
import com.google.android.gms.internal.zzcld;
import com.google.android.gms.internal.zzcle;
import com.google.android.gms.internal.zzclq;

public final class zzcla<T extends Context> {
    private final T zzdyu;

    public zzcla(T t) {
        zzbq.checkNotNull(t);
        this.zzdyu = t;
    }

    private final zzchm zzawy() {
        return zzcim.zzdx(this.zzdyu).zzawy();
    }

    private final void zzk(Runnable runnable) {
        zzcim zzcim2 = zzcim.zzdx(this.zzdyu);
        zzcim2.zzawy();
        zzcim2.zzawx().zzg(new zzcld(this, zzcim2, runnable));
    }

    public static boolean zzk(Context context, boolean bl) {
        zzbq.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 24) {
            return zzclq.zzt(context, "com.google.android.gms.measurement.AppMeasurementJobService");
        }
        return zzclq.zzt(context, "com.google.android.gms.measurement.AppMeasurementService");
    }

    public final IBinder onBind(Intent object) {
        if (object == null) {
            this.zzawy().zzazd().log("onBind called with null intent");
            return null;
        }
        if ("com.google.android.gms.measurement.START".equals(object = object.getAction())) {
            return new zzcir(zzcim.zzdx(this.zzdyu));
        }
        this.zzawy().zzazf().zzj("onBind received unknown action", object);
        return null;
    }

    public final void onCreate() {
        zzcim.zzdx(this.zzdyu).zzawy().zzazj().log("Local AppMeasurementService is starting up");
    }

    public final void onDestroy() {
        zzcim.zzdx(this.zzdyu).zzawy().zzazj().log("Local AppMeasurementService is shutting down");
    }

    public final void onRebind(Intent object) {
        if (object == null) {
            this.zzawy().zzazd().log("onRebind called with null intent");
            return;
        }
        object = object.getAction();
        this.zzawy().zzazj().zzj("onRebind called. action", object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int onStartCommand(Intent intent, int n, int n2) {
        zzchm zzchm2 = zzcim.zzdx(this.zzdyu).zzawy();
        if (intent == null) {
            zzchm2.zzazf().log("AppMeasurementService started with null intent");
            return 2;
        } else {
            String string2 = intent.getAction();
            zzchm2.zzazj().zze("Local AppMeasurementService called. startId, action", n2, string2);
            if (!"com.google.android.gms.measurement.UPLOAD".equals(string2)) return 2;
            {
                this.zzk(new zzclb(this, n2, zzchm2, intent));
                return 2;
            }
        }
    }

    @TargetApi(value=24)
    public final boolean onStartJob(JobParameters jobParameters) {
        zzchm zzchm2 = zzcim.zzdx(this.zzdyu).zzawy();
        String string2 = jobParameters.getExtras().getString("action");
        zzchm2.zzazj().zzj("Local AppMeasurementJobService called. action", string2);
        if ("com.google.android.gms.measurement.UPLOAD".equals(string2)) {
            this.zzk(new zzclc(this, zzchm2, jobParameters));
        }
        return true;
    }

    public final boolean onUnbind(Intent object) {
        if (object == null) {
            this.zzawy().zzazd().log("onUnbind called with null intent");
            return true;
        }
        object = object.getAction();
        this.zzawy().zzazj().zzj("onUnbind called for intent. action", object);
        return true;
    }

    final /* synthetic */ void zza(int n, zzchm zzchm2, Intent intent) {
        if (((zzcle)this.zzdyu).callServiceStopSelfResult(n)) {
            zzchm2.zzazj().zzj("Local AppMeasurementService processed last upload request. StartId", n);
            this.zzawy().zzazj().log("Completed wakeful intent.");
            ((zzcle)this.zzdyu).zzm(intent);
        }
    }

    final /* synthetic */ void zza(zzchm zzchm2, JobParameters jobParameters) {
        zzchm2.zzazj().log("AppMeasurementJobService processed last upload request.");
        ((zzcle)this.zzdyu).zza(jobParameters, false);
    }
}


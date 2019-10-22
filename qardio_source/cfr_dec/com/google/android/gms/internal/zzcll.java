/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.app.job.JobInfo
 *  android.app.job.JobInfo$Builder
 *  android.app.job.JobScheduler
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.PersistableBundle
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgs;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzchc;
import com.google.android.gms.internal.zzchd;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcid;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzcla;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclm;
import com.google.android.gms.internal.zzclq;

public final class zzcll
extends zzcjl {
    private final AlarmManager zzdvu = (AlarmManager)((zzcjk)this).getContext().getSystemService("alarm");
    private Integer zzdvv;
    private final zzcgs zzjjg;

    protected zzcll(zzcim zzcim2) {
        super(zzcim2);
        this.zzjjg = new zzclm(this, zzcim2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final int getJobId() {
        if (this.zzdvv == null) {
            String string2 = String.valueOf(((zzcjk)this).getContext().getPackageName());
            string2 = string2.length() != 0 ? "measurement".concat(string2) : new String("measurement");
            this.zzdvv = string2.hashCode();
        }
        return this.zzdvv;
    }

    @TargetApi(value=24)
    private final void zzbax() {
        JobScheduler jobScheduler = (JobScheduler)((zzcjk)this).getContext().getSystemService("jobscheduler");
        ((zzcjk)this).zzawy().zzazj().zzj("Cancelling job. JobID", this.getJobId());
        jobScheduler.cancel(this.getJobId());
    }

    private final PendingIntent zzzf() {
        Intent intent = new Intent().setClassName(((zzcjk)this).getContext(), "com.google.android.gms.measurement.AppMeasurementReceiver");
        intent.setAction("com.google.android.gms.measurement.UPLOAD");
        return PendingIntent.getBroadcast((Context)((zzcjk)this).getContext(), (int)0, (Intent)intent, (int)0);
    }

    public final void cancel() {
        this.zzxf();
        this.zzdvu.cancel(this.zzzf());
        this.zzjjg.cancel();
        if (Build.VERSION.SDK_INT >= 24) {
            this.zzbax();
        }
    }

    @Override
    protected final boolean zzaxz() {
        this.zzdvu.cancel(this.zzzf());
        if (Build.VERSION.SDK_INT >= 24) {
            this.zzbax();
        }
        return false;
    }

    public final void zzs(long l) {
        this.zzxf();
        if (!zzcid.zzbk(((zzcjk)this).getContext())) {
            ((zzcjk)this).zzawy().zzazi().log("Receiver not registered/enabled");
        }
        if (!zzcla.zzk(((zzcjk)this).getContext(), false)) {
            ((zzcjk)this).zzawy().zzazi().log("Service not registered/enabled");
        }
        this.cancel();
        long l2 = ((zzcjk)this).zzws().elapsedRealtime();
        if (l < Math.max(0L, zzchc.zzjba.get()) && !this.zzjjg.zzdx()) {
            ((zzcjk)this).zzawy().zzazj().log("Scheduling upload with DelayedRunnable");
            this.zzjjg.zzs(l);
        }
        if (Build.VERSION.SDK_INT >= 24) {
            ((zzcjk)this).zzawy().zzazj().log("Scheduling upload with JobScheduler");
            ComponentName componentName = new ComponentName(((zzcjk)this).getContext(), "com.google.android.gms.measurement.AppMeasurementJobService");
            JobScheduler jobScheduler = (JobScheduler)((zzcjk)this).getContext().getSystemService("jobscheduler");
            componentName = new JobInfo.Builder(this.getJobId(), componentName);
            componentName.setMinimumLatency(l);
            componentName.setOverrideDeadline(l << 1);
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putString("action", "com.google.android.gms.measurement.UPLOAD");
            componentName.setExtras(persistableBundle);
            componentName = componentName.build();
            ((zzcjk)this).zzawy().zzazj().zzj("Scheduling job. JobID", this.getJobId());
            jobScheduler.schedule((JobInfo)componentName);
            return;
        }
        ((zzcjk)this).zzawy().zzazj().log("Scheduling upload with AlarmManager");
        this.zzdvu.setInexactRepeating(2, l2 + l, Math.max(zzchc.zzjav.get(), l), this.zzzf());
    }
}


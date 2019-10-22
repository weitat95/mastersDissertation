/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.app.job.JobInfo
 *  android.app.job.JobInfo$Builder
 *  android.app.job.JobScheduler
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.PersistableBundle
 */
package com.google.android.gms.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PersistableBundle;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzard;

public final class zzari
extends zzaqa {
    private boolean zzdvs;
    private boolean zzdvt;
    private final AlarmManager zzdvu = (AlarmManager)this.getContext().getSystemService("alarm");
    private Integer zzdvv;

    protected zzari(zzaqc zzaqc2) {
        super(zzaqc2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final int getJobId() {
        if (this.zzdvv == null) {
            String string2 = String.valueOf(this.getContext().getPackageName());
            string2 = string2.length() != 0 ? "analytics".concat(string2) : new String("analytics");
            this.zzdvv = string2.hashCode();
        }
        return this.zzdvv;
    }

    private final PendingIntent zzzf() {
        Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        intent.setComponent(new ComponentName(this.getContext(), "com.google.android.gms.analytics.AnalyticsReceiver"));
        return PendingIntent.getBroadcast((Context)this.getContext(), (int)0, (Intent)intent, (int)0);
    }

    public final void cancel() {
        this.zzdvt = false;
        this.zzdvu.cancel(this.zzzf());
        if (Build.VERSION.SDK_INT >= 24) {
            JobScheduler jobScheduler = (JobScheduler)this.getContext().getSystemService("jobscheduler");
            this.zza("Cancelling job. JobID", this.getJobId());
            jobScheduler.cancel(this.getJobId());
        }
    }

    public final void schedule() {
        long l;
        long l2;
        block3: {
            block2: {
                this.zzxf();
                zzbq.zza(this.zzdvs, "Receiver not registered");
                l = zzard.zzyt();
                if (l <= 0L) break block2;
                this.cancel();
                l2 = this.zzws().elapsedRealtime();
                this.zzdvt = true;
                if (Build.VERSION.SDK_INT < 24) break block3;
                this.zzdu("Scheduling upload with JobScheduler");
                ComponentName componentName = new ComponentName(this.getContext(), "com.google.android.gms.analytics.AnalyticsJobService");
                JobScheduler jobScheduler = (JobScheduler)this.getContext().getSystemService("jobscheduler");
                componentName = new JobInfo.Builder(this.getJobId(), componentName);
                componentName.setMinimumLatency(l);
                componentName.setOverrideDeadline(l << 1);
                PersistableBundle persistableBundle = new PersistableBundle();
                persistableBundle.putString("action", "com.google.android.gms.analytics.ANALYTICS_DISPATCH");
                componentName.setExtras(persistableBundle);
                componentName = componentName.build();
                this.zza("Scheduling job. JobID", this.getJobId());
                jobScheduler.schedule((JobInfo)componentName);
            }
            return;
        }
        this.zzdu("Scheduling upload with AlarmManager");
        this.zzdvu.setInexactRepeating(2, l2 + l, l, this.zzzf());
    }

    public final boolean zzdx() {
        return this.zzdvt;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected final void zzvf() {
        ActivityInfo activityInfo;
        try {
            this.cancel();
            if (zzard.zzyt() <= 0L || (activityInfo = this.getContext().getPackageManager().getReceiverInfo(new ComponentName(this.getContext(), "com.google.android.gms.analytics.AnalyticsReceiver"), 2)) == null) return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return;
        }
        if (!activityInfo.enabled) return;
        this.zzdu("Receiver registered for local dispatch.");
        this.zzdvs = true;
    }

    public final boolean zzze() {
        return this.zzdvs;
    }
}


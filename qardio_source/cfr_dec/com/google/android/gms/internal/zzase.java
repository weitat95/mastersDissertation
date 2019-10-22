/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.job.JobParameters
 */
package com.google.android.gms.internal;

import android.app.job.JobParameters;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzarj;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzasd;
import com.google.android.gms.internal.zzasf;

final class zzase
implements zzarj {
    final /* synthetic */ Integer zzdyw;
    private /* synthetic */ zzaqc zzdyx;
    final /* synthetic */ zzarv zzdyy;
    final /* synthetic */ JobParameters zzdyz;
    final /* synthetic */ zzasd zzdza;

    zzase(zzasd zzasd2, Integer n, zzaqc zzaqc2, zzarv zzarv2, JobParameters jobParameters) {
        this.zzdza = zzasd2;
        this.zzdyw = n;
        this.zzdyx = zzaqc2;
        this.zzdyy = zzarv2;
        this.zzdyz = jobParameters;
    }

    @Override
    public final void zzd(Throwable throwable) {
        zzasd.zzb(this.zzdza).post((Runnable)new zzasf(this));
    }
}


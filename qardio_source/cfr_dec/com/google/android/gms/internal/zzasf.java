/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.job.JobParameters
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.app.job.JobParameters;
import android.os.Build;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzasd;
import com.google.android.gms.internal.zzase;
import com.google.android.gms.internal.zzasg;

final class zzasf
implements Runnable {
    private /* synthetic */ zzase zzdzb;

    zzasf(zzase zzase2) {
        this.zzdzb = zzase2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void run() {
        if (this.zzdzb.zzdyw != null) {
            if (!((zzasg)zzasd.zza(this.zzdzb.zzdza)).callServiceStopSelfResult(this.zzdzb.zzdyw)) return;
            {
                this.zzdzb.zzdyy.zzdu("Local AnalyticsService processed last dispatch request");
                return;
            }
        } else {
            if (Build.VERSION.SDK_INT < 24) return;
            {
                this.zzdzb.zzdyy.zzdu("AnalyticsJobService processed last dispatch request");
                ((zzasg)zzasd.zza(this.zzdzb.zzdza)).zza(this.zzdzb.zzdyz, false);
                return;
            }
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.job.JobParameters
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Handler
 *  android.os.PersistableBundle
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzarj;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzasc;
import com.google.android.gms.internal.zzase;
import com.google.android.gms.internal.zzasg;
import com.google.android.gms.internal.zzasl;
import com.google.android.gms.internal.zzcxt;

public final class zzasd<T extends Context> {
    private static Boolean zzdyv;
    private final Handler mHandler;
    private final T zzdyu;

    public zzasd(T t) {
        zzbq.checkNotNull(t);
        this.zzdyu = t;
        this.mHandler = new Handler();
    }

    static /* synthetic */ Context zza(zzasd zzasd2) {
        return zzasd2.zzdyu;
    }

    private final void zza(Integer n, JobParameters jobParameters) {
        zzaqc zzaqc2 = zzaqc.zzbm(this.zzdyu);
        zzarv zzarv2 = zzaqc2.zzwt();
        zzaqc2.zzwx().zza(new zzase(this, n, zzaqc2, zzarv2, jobParameters));
    }

    static /* synthetic */ Handler zzb(zzasd zzasd2) {
        return zzasd2.mHandler;
    }

    public static boolean zzbo(Context context) {
        zzbq.checkNotNull(context);
        if (zzdyv != null) {
            return zzdyv;
        }
        boolean bl = zzasl.zzt(context, "com.google.android.gms.analytics.AnalyticsService");
        zzdyv = bl;
        return bl;
    }

    public final void onCreate() {
        zzaqc.zzbm(this.zzdyu).zzwt().zzdu("Local AnalyticsService is starting up");
    }

    public final void onDestroy() {
        zzaqc.zzbm(this.zzdyu).zzwt().zzdu("Local AnalyticsService is shutting down");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int onStartCommand(Intent object, int n, int n2) {
        Object object2;
        block10: {
            zzcxt zzcxt2;
            try {
                object2 = zzasc.sLock;
                synchronized (object2) {
                    zzcxt2 = zzasc.zzdyt;
                    if (zzcxt2 == null) break block10;
                }
            }
            catch (SecurityException securityException) {
                break block10;
            }
            {
                if (zzcxt2.isHeld()) {
                    zzcxt2.release();
                }
            }
        }
        object2 = zzaqc.zzbm(this.zzdyu).zzwt();
        if (object == null) {
            ((zzapz)object2).zzdx("AnalyticsService started with null intent");
            return 2;
        } else {
            object = object.getAction();
            ((zzapz)object2).zza("Local AnalyticsService called. startId, action", n2, object);
            if (!"com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(object)) return 2;
            {
                this.zza(n2, null);
                return 2;
            }
        }
    }

    @TargetApi(value=24)
    public final boolean onStartJob(JobParameters jobParameters) {
        zzarv zzarv2 = zzaqc.zzbm(this.zzdyu).zzwt();
        String string2 = jobParameters.getExtras().getString("action");
        zzarv2.zza("Local AnalyticsJobService called. action", string2);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(string2)) {
            this.zza(null, jobParameters);
        }
        return true;
    }
}


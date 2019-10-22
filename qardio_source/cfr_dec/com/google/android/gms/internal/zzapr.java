/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzapu;
import com.google.android.gms.internal.zzapv;
import com.google.android.gms.internal.zzapx;
import com.google.android.gms.internal.zzapy;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqe;
import com.google.android.gms.internal.zzaqf;
import com.google.android.gms.internal.zzaqo;
import com.google.android.gms.internal.zzarj;
import com.google.android.gms.internal.zzarq;
import com.google.android.gms.internal.zzasc;
import com.google.android.gms.internal.zzasd;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class zzapr
extends zzaqa {
    private final zzaqo zzdss;

    public zzapr(zzaqc zzaqc2, zzaqe zzaqe2) {
        super(zzaqc2);
        zzbq.checkNotNull(zzaqe2);
        this.zzdss = new zzaqo(zzaqc2, zzaqe2);
    }

    static /* synthetic */ zzaqo zza(zzapr zzapr2) {
        return zzapr2.zzdss;
    }

    final void onServiceConnected() {
        zzj.zzve();
        this.zzdss.onServiceConnected();
    }

    public final void start() {
        this.zzdss.start();
    }

    public final long zza(zzaqf zzaqf2) {
        this.zzxf();
        zzbq.checkNotNull(zzaqf2);
        zzj.zzve();
        long l = this.zzdss.zza(zzaqf2, true);
        if (l == 0L) {
            this.zzdss.zzb(zzaqf2);
        }
        return l;
    }

    public final void zza(zzarj zzarj2) {
        this.zzxf();
        this.zzwv().zzc(new zzapx(this, zzarj2));
    }

    public final void zza(zzarq zzarq2) {
        zzbq.checkNotNull(zzarq2);
        this.zzxf();
        this.zzb("Hit delivery requested", zzarq2);
        this.zzwv().zzc(new zzapv(this, zzarq2));
    }

    public final void zza(String string2, Runnable runnable) {
        zzbq.zzh(string2, "campaign param can't be empty");
        this.zzwv().zzc(new zzapu(this, string2, runnable));
    }

    @Override
    protected final void zzvf() {
        this.zzdss.initialize();
    }

    public final void zzwn() {
        this.zzxf();
        Context context = this.getContext();
        if (zzasc.zzbk(context) && zzasd.zzbo(context)) {
            Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            intent.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
            context.startService(intent);
            return;
        }
        this.zza((zzarj)null);
    }

    public final boolean zzwo() {
        this.zzxf();
        Future<Void> future = this.zzwv().zza(new zzapy(this));
        try {
            future.get(4L, TimeUnit.SECONDS);
            return true;
        }
        catch (InterruptedException interruptedException) {
            this.zzd("syncDispatchLocalHits interrupted", interruptedException);
            return false;
        }
        catch (ExecutionException executionException) {
            this.zze("syncDispatchLocalHits failed", executionException);
            return false;
        }
        catch (TimeoutException timeoutException) {
            this.zzd("syncDispatchLocalHits timed out", timeoutException);
            return false;
        }
    }

    public final void zzwp() {
        this.zzxf();
        zzj.zzve();
        zzaqo zzaqo2 = this.zzdss;
        zzj.zzve();
        zzaqo2.zzxf();
        zzaqo2.zzdu("Service disconnected");
    }

    final void zzwq() {
        zzj.zzve();
        this.zzdss.zzwq();
    }
}


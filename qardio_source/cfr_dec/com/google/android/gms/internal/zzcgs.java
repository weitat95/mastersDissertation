/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgt;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcim;

abstract class zzcgs {
    private static volatile Handler zzdvp;
    private volatile long zzdvq;
    private final zzcim zziwf;
    private boolean zzizd;
    private final Runnable zzz;

    zzcgs(zzcim zzcim2) {
        zzbq.checkNotNull(zzcim2);
        this.zziwf = zzcim2;
        this.zzizd = true;
        this.zzz = new zzcgt(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final Handler getHandler() {
        if (zzdvp != null) {
            return zzdvp;
        }
        synchronized (zzcgs.class) {
            if (zzdvp != null) return zzdvp;
            zzdvp = new Handler(this.zziwf.getContext().getMainLooper());
            return zzdvp;
        }
    }

    static /* synthetic */ long zza(zzcgs zzcgs2, long l) {
        zzcgs2.zzdvq = 0L;
        return 0L;
    }

    static /* synthetic */ zzcim zza(zzcgs zzcgs2) {
        return zzcgs2.zziwf;
    }

    static /* synthetic */ boolean zzb(zzcgs zzcgs2) {
        return zzcgs2.zzizd;
    }

    public final void cancel() {
        this.zzdvq = 0L;
        this.getHandler().removeCallbacks(this.zzz);
    }

    public abstract void run();

    public final boolean zzdx() {
        return this.zzdvq != 0L;
    }

    public final void zzs(long l) {
        this.cancel();
        if (l >= 0L) {
            this.zzdvq = this.zziwf.zzws().currentTimeMillis();
            if (!this.getHandler().postDelayed(this.zzz, l)) {
                this.zziwf.zzawy().zzazd().zzj("Failed to schedule delayed post. time", l);
            }
        }
    }
}


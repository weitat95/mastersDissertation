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
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzarg;
import com.google.android.gms.internal.zzarv;

abstract class zzarf {
    private static volatile Handler zzdvp;
    private final zzaqc zzdta;
    private volatile long zzdvq;
    private final Runnable zzz;

    zzarf(zzaqc zzaqc2) {
        zzbq.checkNotNull(zzaqc2);
        this.zzdta = zzaqc2;
        this.zzz = new zzarg(this);
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
        synchronized (zzarf.class) {
            if (zzdvp != null) return zzdvp;
            zzdvp = new Handler(this.zzdta.getContext().getMainLooper());
            return zzdvp;
        }
    }

    static /* synthetic */ long zza(zzarf zzarf2, long l) {
        zzarf2.zzdvq = 0L;
        return 0L;
    }

    static /* synthetic */ zzaqc zza(zzarf zzarf2) {
        return zzarf2.zzdta;
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
            this.zzdvq = this.zzdta.zzws().currentTimeMillis();
            if (!this.getHandler().postDelayed(this.zzz, l)) {
                this.zzdta.zzwt().zze("Failed to schedule delayed post. time", l);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzt(long l) {
        block6: {
            block5: {
                long l2 = 0L;
                if (!this.zzdx()) break block5;
                if (l < 0L) {
                    this.cancel();
                    return;
                }
                if ((l -= Math.abs(this.zzdta.zzws().currentTimeMillis() - this.zzdvq)) < 0L) {
                    l = l2;
                }
                this.getHandler().removeCallbacks(this.zzz);
                if (!this.getHandler().postDelayed(this.zzz, l)) break block6;
            }
            return;
        }
        this.zzdta.zzwt().zze("Failed to adjust delayed post. time", l);
    }

    public final long zzzb() {
        if (this.zzdvq == 0L) {
            return 0L;
        }
        return Math.abs(this.zzdta.zzws().currentTimeMillis() - this.zzdvq);
    }
}


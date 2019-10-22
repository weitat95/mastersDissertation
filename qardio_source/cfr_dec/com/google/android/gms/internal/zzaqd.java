/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzarv;

final class zzaqd
implements Thread.UncaughtExceptionHandler {
    private /* synthetic */ zzaqc zzdtr;

    zzaqd(zzaqc zzaqc2) {
        this.zzdtr = zzaqc2;
    }

    @Override
    public final void uncaughtException(Thread object, Throwable throwable) {
        object = this.zzdtr.zzxh();
        if (object != null) {
            ((zzapz)object).zze("Job execution failed", throwable);
        }
    }
}


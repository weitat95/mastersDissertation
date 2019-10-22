/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcjk;

final class zzcij
implements Thread.UncaughtExceptionHandler {
    private final String zzjep;
    private /* synthetic */ zzcih zzjeq;

    public zzcij(zzcih zzcih2, String string2) {
        this.zzjeq = zzcih2;
        zzbq.checkNotNull(string2);
        this.zzjep = string2;
    }

    @Override
    public final void uncaughtException(Thread thread, Throwable throwable) {
        synchronized (this) {
            ((zzcjk)this.zzjeq).zzawy().zzazd().zzj(this.zzjep, throwable);
            return;
        }
    }
}


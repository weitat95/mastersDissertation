/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcii;
import com.google.android.gms.internal.zzcjk;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

final class zzcik<V>
extends FutureTask<V>
implements Comparable<zzcik> {
    private final String zzjep;
    private /* synthetic */ zzcih zzjeq;
    private final long zzjer;
    final boolean zzjes;

    zzcik(zzcih zzcih2, Runnable runnable, boolean bl, String string2) {
        this.zzjeq = zzcih2;
        super(runnable, null);
        zzbq.checkNotNull(string2);
        this.zzjer = zzcih.zzazu().getAndIncrement();
        this.zzjep = string2;
        this.zzjes = false;
        if (this.zzjer == Long.MAX_VALUE) {
            ((zzcjk)zzcih2).zzawy().zzazd().log("Tasks index overflow");
        }
    }

    zzcik(Callable<V> callable, boolean bl, String string2) {
        this.zzjeq = var1_1;
        super(callable);
        zzbq.checkNotNull(string2);
        this.zzjer = zzcih.zzazu().getAndIncrement();
        this.zzjep = string2;
        this.zzjes = bl;
        if (this.zzjer == Long.MAX_VALUE) {
            var1_1.zzawy().zzazd().log("Tasks index overflow");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ int compareTo(Object object) {
        object = (zzcik)object;
        if (this.zzjes != ((zzcik)object).zzjes) {
            if (this.zzjes) return -1;
            return 1;
        }
        if (this.zzjer < ((zzcik)object).zzjer) {
            return -1;
        }
        if (this.zzjer > ((zzcik)object).zzjer) {
            return 1;
        }
        ((zzcjk)this.zzjeq).zzawy().zzaze().zzj("Two tasks share the same index. index", this.zzjer);
        return 0;
    }

    @Override
    protected final void setException(Throwable throwable) {
        ((zzcjk)this.zzjeq).zzawy().zzazd().zzj(this.zzjep, throwable);
        if (throwable instanceof zzcii) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), throwable);
        }
        super.setException(throwable);
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;

final class zzclk {
    private long mStartTime;
    private final zzd zzata;

    public zzclk(zzd zzd2) {
        zzbq.checkNotNull(zzd2);
        this.zzata = zzd2;
    }

    public final void clear() {
        this.mStartTime = 0L;
    }

    public final void start() {
        this.mStartTime = this.zzata.elapsedRealtime();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean zzu(long l) {
        return this.mStartTime == 0L || this.zzata.elapsedRealtime() - this.mStartTime >= 3600000L;
    }
}


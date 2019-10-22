/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.job.JobParameters
 */
package com.google.android.gms.internal;

import android.app.job.JobParameters;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcla;

final class zzclc
implements Runnable {
    private final zzcla zzjiv;
    private final zzchm zzjiz;
    private final JobParameters zzjja;

    zzclc(zzcla zzcla2, zzchm zzchm2, JobParameters jobParameters) {
        this.zzjiv = zzcla2;
        this.zzjiz = zzchm2;
        this.zzjja = jobParameters;
    }

    @Override
    public final void run() {
        this.zzjiv.zza(this.zzjiz, this.zzjja);
    }
}


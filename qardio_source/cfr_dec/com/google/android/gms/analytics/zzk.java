/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.analytics.zzj;
import java.util.Iterator;

final class zzk
implements Runnable {
    private /* synthetic */ zzg zzdqb;
    private /* synthetic */ zzj zzdqc;

    zzk(zzj zzj2, zzg zzg2) {
        this.zzdqc = zzj2;
        this.zzdqb = zzg2;
    }

    @Override
    public final void run() {
        this.zzdqb.zzuy().zza(this.zzdqb);
        Iterator iterator = zzj.zza(this.zzdqc).iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        zzj.zza(this.zzdqc, this.zzdqb);
    }
}


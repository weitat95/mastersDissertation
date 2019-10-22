/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class zzi<T extends zzi> {
    private final zzj zzdps;
    protected final zzg zzdpt;
    private final List<Object> zzdpu;

    protected zzi(zzj object, zzd zzd2) {
        zzbq.checkNotNull(object);
        this.zzdps = object;
        this.zzdpu = new ArrayList<Object>();
        object = new zzg(this, zzd2);
        ((zzg)object).zzva();
        this.zzdpt = object;
    }

    protected void zza(zzg zzg2) {
    }

    protected final void zzd(zzg object) {
        object = this.zzdpu.iterator();
        while (object.hasNext()) {
            object.next();
        }
    }

    public zzg zzun() {
        zzg zzg2 = this.zzdpt.zzus();
        this.zzd(zzg2);
        return zzg2;
    }

    protected final zzj zzvb() {
        return this.zzdps;
    }
}


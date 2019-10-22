/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzara;
import com.google.android.gms.internal.zzarc;
import com.google.android.gms.internal.zzarp;
import com.google.android.gms.internal.zzarv;

final class zzaro
implements zzarc<zzarp> {
    private final zzaqc zzdta;
    private final zzarp zzdxp;

    public zzaro(zzaqc zzaqc2) {
        this.zzdta = zzaqc2;
        this.zzdxp = new zzarp();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void zzc(String object, boolean bl) {
        if (!"ga_dryRun".equals(object)) {
            this.zzdta.zzwt().zzd("Bool xml configuration name not recognized", object);
            return;
        }
        object = this.zzdxp;
        int n = bl ? 1 : 0;
        ((zzarp)object).zzdxs = n;
    }

    @Override
    public final void zzd(String string2, int n) {
        if ("ga_dispatchPeriod".equals(string2)) {
            this.zzdxp.zzdxr = n;
            return;
        }
        this.zzdta.zzwt().zzd("Int xml configuration name not recognized", string2);
    }

    @Override
    public final void zzi(String string2, String string3) {
    }

    @Override
    public final void zzj(String string2, String string3) {
        if ("ga_appName".equals(string2)) {
            this.zzdxp.zzdqz = string3;
            return;
        }
        if ("ga_appVersion".equals(string2)) {
            this.zzdxp.zzdra = string3;
            return;
        }
        if ("ga_logLevel".equals(string2)) {
            this.zzdxp.zzdxq = string3;
            return;
        }
        this.zzdta.zzwt().zzd("String xml configuration name not recognized", string2);
    }

    @Override
    public final /* synthetic */ zzara zzyo() {
        return this.zzdxp;
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzj;
import com.google.android.gms.internal.zzapi;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;

public final class zzarh
extends zzaqa {
    zzarh(zzaqc zzaqc2) {
        super(zzaqc2);
    }

    @Override
    protected final void zzvf() {
    }

    public final zzapi zzzc() {
        this.zzxf();
        return this.zzwv().zzvd();
    }

    public final String zzzd() {
        this.zzxf();
        zzapi zzapi2 = this.zzzc();
        int n = zzapi2.zzchl;
        int n2 = zzapi2.zzchm;
        return new StringBuilder(23).append(n).append("x").append(n2).toString();
    }
}


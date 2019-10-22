/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzj;
import com.google.android.gms.internal.zzapd;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzasm;

public final class zzaqn
extends zzaqa {
    private final zzapd zzdpz = new zzapd();

    zzaqn(zzaqc zzaqc2) {
        super(zzaqc2);
    }

    @Override
    protected final void zzvf() {
        this.zzwv().zzvc().zza(this.zzdpz);
        Object object = this.zzwz();
        String string2 = ((zzasm)object).zzvi();
        if (string2 != null) {
            this.zzdpz.setAppName(string2);
        }
        if ((object = ((zzasm)object).zzvj()) != null) {
            this.zzdpz.setAppVersion((String)object);
        }
    }

    public final zzapd zzxy() {
        this.zzxf();
        return this.zzdpz;
    }
}


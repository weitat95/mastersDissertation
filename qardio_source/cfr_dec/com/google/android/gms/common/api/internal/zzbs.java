/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.api.internal.zzbt;
import com.google.android.gms.common.internal.zzp;

final class zzbs
implements zzp {
    final /* synthetic */ zzbo zzftr;

    zzbs(zzbo zzbo2) {
        this.zzftr = zzbo2;
    }

    @Override
    public final void zzajf() {
        zzbm.zza(this.zzftr.zzfti).post((Runnable)new zzbt(this));
    }
}


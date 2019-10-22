/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzao;
import com.google.android.gms.common.zzf;

final class zzap
implements Runnable {
    private /* synthetic */ zzao zzfrl;

    zzap(zzao zzao2) {
        this.zzfrl = zzao2;
    }

    @Override
    public final void run() {
        zzf.zzce(zzao.zza(this.zzfrl));
    }
}


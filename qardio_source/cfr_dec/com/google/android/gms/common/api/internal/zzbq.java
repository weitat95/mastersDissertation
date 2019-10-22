/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzbo;

final class zzbq
implements Runnable {
    private /* synthetic */ zzbo zzftr;

    zzbq(zzbo zzbo2) {
        this.zzftr = zzbo2;
    }

    @Override
    public final void run() {
        zzbo.zzd(this.zzftr);
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.api.internal.zzbs;

final class zzbt
implements Runnable {
    private /* synthetic */ zzbs zzftt;

    zzbt(zzbs zzbs2) {
        this.zzftt = zzbs2;
    }

    @Override
    public final void run() {
        zzbo.zze(this.zzftt.zzftr).disconnect();
    }
}


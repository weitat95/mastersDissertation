/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcib;
import com.google.android.gms.internal.zzcim;

final class zzchn
implements Runnable {
    private /* synthetic */ String zzjcd;
    private /* synthetic */ zzchm zzjce;

    zzchn(zzchm zzchm2, String string2) {
        this.zzjce = zzchm2;
        this.zzjcd = string2;
    }

    @Override
    public final void run() {
        zzchx zzchx2 = this.zzjce.zziwf.zzawz();
        if (!zzchx2.isInitialized()) {
            this.zzjce.zzk(6, "Persisted config not initialized. Not logging error/warn");
            return;
        }
        zzchx2.zzjcq.zzf(this.zzjcd, 1L);
    }
}


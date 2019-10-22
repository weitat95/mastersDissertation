/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzcku;

final class zzckx
implements Runnable {
    private /* synthetic */ zzcku zzjit;
    private /* synthetic */ zzche zzjiu;

    zzckx(zzcku zzcku2, zzche zzche2) {
        this.zzjit = zzcku2;
        this.zzjiu = zzche2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        zzcku zzcku2 = this.zzjit;
        synchronized (zzcku2) {
            zzcku.zza(this.zzjit, false);
            if (!this.zzjit.zzjij.isConnected()) {
                ((zzcjk)this.zzjit.zzjij).zzawy().zzazi().log("Connected to remote service");
                this.zzjit.zzjij.zza(this.zzjiu);
            }
            return;
        }
    }
}


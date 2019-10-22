/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzfo;

final class zzfq
implements Runnable {
    private /* synthetic */ zzfo zzkjw;

    zzfq(zzfo zzfo2) {
        this.zzkjw = zzfo2;
    }

    @Override
    public final void run() {
        zzfo.zze(this.zzkjw).dispatch();
    }
}


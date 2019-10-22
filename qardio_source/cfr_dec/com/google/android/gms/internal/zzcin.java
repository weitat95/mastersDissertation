/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcim;

final class zzcin
implements Runnable {
    private /* synthetic */ zzcim zzjgh;

    zzcin(zzcim zzcim2) {
        this.zzjgh = zzcim2;
    }

    @Override
    public final void run() {
        zzcim.zza(this.zzjgh);
        this.zzjgh.start();
    }
}


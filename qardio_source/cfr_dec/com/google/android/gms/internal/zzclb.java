/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.internal;

import android.content.Intent;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcla;

final class zzclb
implements Runnable {
    private final zzcla zzjiv;
    private final int zzjiw;
    private final zzchm zzjix;
    private final Intent zzjiy;

    zzclb(zzcla zzcla2, int n, zzchm zzchm2, Intent intent) {
        this.zzjiv = zzcla2;
        this.zzjiw = n;
        this.zzjix = zzchm2;
        this.zzjiy = intent;
    }

    @Override
    public final void run() {
        this.zzjiv.zza(this.zzjiw, this.zzjix, this.zzjiy);
    }
}


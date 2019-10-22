/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgs;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckg;

final class zzckm
extends zzcgs {
    private /* synthetic */ zzckg zzjij;

    zzckm(zzckg zzckg2, zzcim zzcim2) {
        this.zzjij = zzckg2;
        super(zzcim2);
    }

    @Override
    public final void run() {
        ((zzcjk)this.zzjij).zzawy().zzazf().log("Tasks have been queued for a long time");
    }
}


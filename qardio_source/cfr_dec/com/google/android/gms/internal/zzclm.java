/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.internal.zzcgs;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcll;

final class zzclm
extends zzcgs {
    private /* synthetic */ zzcll zzjjh;

    zzclm(zzcll zzcll2, zzcim zzcim2) {
        this.zzjjh = zzcll2;
        super(zzcim2);
    }

    @Override
    public final void run() {
        this.zzjjh.cancel();
        ((zzcjk)this.zzjjh).zzawy().zzazj().log("Sending upload intent from DelayedRunnable");
        Intent intent = new Intent().setClassName(((zzcjk)this.zzjjh).getContext(), "com.google.android.gms.measurement.AppMeasurementReceiver");
        intent.setAction("com.google.android.gms.measurement.UPLOAD");
        ((zzcjk)this.zzjjh).getContext().sendBroadcast(intent);
    }
}


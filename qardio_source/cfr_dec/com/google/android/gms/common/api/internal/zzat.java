/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zzar;
import com.google.android.gms.common.api.internal.zzbh;
import com.google.android.gms.common.api.internal.zzbj;
import com.google.android.gms.common.internal.zzj;

final class zzat
extends zzbj {
    private /* synthetic */ zzj zzfrq;

    zzat(zzar zzar2, zzbh zzbh2, zzj zzj2) {
        this.zzfrq = zzj2;
        super(zzbh2);
    }

    @Override
    public final void zzaib() {
        this.zzfrq.zzf(new ConnectionResult(16, null));
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaqg;
import com.google.android.gms.internal.zzaqi;
import com.google.android.gms.internal.zzarr;

final class zzaqj
implements Runnable {
    private /* synthetic */ zzarr zzdue;
    private /* synthetic */ zzaqi zzduf;

    zzaqj(zzaqi zzaqi2, zzarr zzarr2) {
        this.zzduf = zzaqi2;
        this.zzdue = zzarr2;
    }

    @Override
    public final void run() {
        if (!this.zzduf.zzdub.isConnected()) {
            this.zzduf.zzdub.zzdv("Connected to service after a timeout");
            zzaqg.zza(this.zzduf.zzdub, this.zzdue);
        }
    }
}


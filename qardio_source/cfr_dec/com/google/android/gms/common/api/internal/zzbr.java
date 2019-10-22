/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zzbo;

final class zzbr
implements Runnable {
    private /* synthetic */ zzbo zzftr;
    private /* synthetic */ ConnectionResult zzfts;

    zzbr(zzbo zzbo2, ConnectionResult connectionResult) {
        this.zzftr = zzbo2;
        this.zzfts = connectionResult;
    }

    @Override
    public final void run() {
        this.zzftr.onConnectionFailed(this.zzfts);
    }
}


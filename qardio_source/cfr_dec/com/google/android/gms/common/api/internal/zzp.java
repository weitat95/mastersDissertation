/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbq;

final class zzp {
    private final int zzfon;
    private final ConnectionResult zzfoo;

    zzp(ConnectionResult connectionResult, int n) {
        zzbq.checkNotNull(connectionResult);
        this.zzfoo = connectionResult;
        this.zzfon = n;
    }

    final int zzahe() {
        return this.zzfon;
    }

    final ConnectionResult zzahf() {
        return this.zzfoo;
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.api.internal.zzbu;
import com.google.android.gms.common.internal.zzan;
import java.util.Collections;
import java.util.Set;

final class zzbv
implements Runnable {
    private /* synthetic */ ConnectionResult zzfts;
    private /* synthetic */ zzbu zzftv;

    zzbv(zzbu zzbu2, ConnectionResult connectionResult) {
        this.zzftv = zzbu2;
        this.zzfts = connectionResult;
    }

    @Override
    public final void run() {
        if (this.zzfts.isSuccess()) {
            zzbu.zza(this.zzftv, true);
            if (zzbu.zza(this.zzftv).zzaay()) {
                zzbu.zzb(this.zzftv);
                return;
            }
            zzbu.zza(this.zzftv).zza(null, Collections.<Scope>emptySet());
            return;
        }
        ((zzbo)zzbm.zzj(this.zzftv.zzfti).get(zzbu.zzc(this.zzftv))).onConnectionFailed(this.zzfts);
    }
}


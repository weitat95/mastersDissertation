/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzao;
import com.google.android.gms.common.api.internal.zzap;
import com.google.android.gms.common.api.internal.zzav;
import com.google.android.gms.internal.zzcxj;

final class zzax
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private /* synthetic */ zzao zzfrl;

    private zzax(zzao zzao2) {
        this.zzfrl = zzao2;
    }

    /* synthetic */ zzax(zzao zzao2, zzap zzap2) {
        this(zzao2);
    }

    @Override
    public final void onConnected(Bundle bundle) {
        zzao.zzf(this.zzfrl).zza(new zzav(this.zzfrl));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        zzao.zzc(this.zzfrl).lock();
        try {
            if (zzao.zzb(this.zzfrl, connectionResult)) {
                zzao.zzi(this.zzfrl);
                zzao.zzj(this.zzfrl);
                do {
                    return;
                    break;
                } while (true);
            }
            zzao.zza(this.zzfrl, connectionResult);
            return;
        }
        finally {
            zzao.zzc(this.zzfrl).unlock();
        }
    }

    @Override
    public final void onConnectionSuspended(int n) {
    }
}


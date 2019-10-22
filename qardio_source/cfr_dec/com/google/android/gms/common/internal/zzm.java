/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzj;
import java.util.Set;

public final class zzm
implements zzj {
    private /* synthetic */ zzd zzfza;

    public zzm(zzd zzd2) {
        this.zzfza = zzd2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void zzf(ConnectionResult connectionResult) {
        if (connectionResult.isSuccess()) {
            this.zzfza.zza(null, this.zzfza.zzakp());
            return;
        } else {
            if (zzd.zzg(this.zzfza) == null) return;
            {
                zzd.zzg(this.zzfza).onConnectionFailed(connectionResult);
                return;
            }
        }
    }
}


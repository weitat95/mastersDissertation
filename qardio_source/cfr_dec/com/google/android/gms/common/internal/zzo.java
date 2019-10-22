/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzj;

public final class zzo
extends zze {
    private /* synthetic */ zzd zzfza;

    public zzo(zzd zzd2, int n, Bundle bundle) {
        this.zzfza = zzd2;
        super(zzd2, n, null);
    }

    @Override
    protected final boolean zzakr() {
        this.zzfza.zzfym.zzf(ConnectionResult.zzfkr);
        return true;
    }

    @Override
    protected final void zzj(ConnectionResult connectionResult) {
        this.zzfza.zzfym.zzf(connectionResult);
        this.zzfza.onConnectionFailed(connectionResult);
    }
}


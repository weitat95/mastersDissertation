/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zzcd;
import com.google.android.gms.common.api.internal.zzv;
import com.google.android.gms.common.api.internal.zzw;

final class zzx
implements zzcd {
    private /* synthetic */ zzv zzfpu;

    private zzx(zzv zzv2) {
        this.zzfpu = zzv2;
    }

    /* synthetic */ zzx(zzv zzv2, zzw zzw2) {
        this(zzv2);
    }

    @Override
    public final void zzc(ConnectionResult connectionResult) {
        zzv.zza(this.zzfpu).lock();
        try {
            zzv.zza(this.zzfpu, connectionResult);
            zzv.zzb(this.zzfpu);
            return;
        }
        finally {
            zzv.zza(this.zzfpu).unlock();
        }
    }

    @Override
    public final void zzf(int n, boolean bl) {
        zzv.zza(this.zzfpu).lock();
        try {
            if (zzv.zzc(this.zzfpu) || zzv.zzd(this.zzfpu) == null || !zzv.zzd(this.zzfpu).isSuccess()) {
                zzv.zza(this.zzfpu, false);
                zzv.zza(this.zzfpu, n, bl);
                return;
            }
            zzv.zza(this.zzfpu, true);
            zzv.zze(this.zzfpu).onConnectionSuspended(n);
            return;
        }
        finally {
            zzv.zza(this.zzfpu).unlock();
        }
    }

    @Override
    public final void zzj(Bundle bundle) {
        zzv.zza(this.zzfpu).lock();
        try {
            zzv.zza(this.zzfpu, bundle);
            zzv.zza(this.zzfpu, ConnectionResult.zzfkr);
            zzv.zzb(this.zzfpu);
            return;
        }
        finally {
            zzv.zza(this.zzfpu).unlock();
        }
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 */
package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzae;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.api.internal.zzm;

public final class zzc<A extends zzm<? extends Result, Api.zzb>>
extends zza {
    private A zzfnp;

    public zzc(int n, A a2) {
        super(n);
        this.zzfnp = a2;
    }

    @Override
    public final void zza(zzae zzae2, boolean bl) {
        zzae2.zza((BasePendingResult<? extends Result>)this.zzfnp, bl);
    }

    @Override
    public final void zza(zzbo<?> zzbo2) throws DeadObjectException {
        ((zzm)((Object)this.zzfnp)).zzb((Api.zze)zzbo2.zzahp());
    }

    @Override
    public final void zzs(Status status) {
        ((zzm)this.zzfnp).zzu(status);
    }
}


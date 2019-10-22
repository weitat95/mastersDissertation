/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import android.support.v4.util.ArraySet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.api.internal.zzo;

public class zzah
extends zzo {
    private zzbm zzfmi;
    private final ArraySet<zzh<?>> zzfqs;

    private final void zzahy() {
        if (!this.zzfqs.isEmpty()) {
            this.zzfmi.zza(this);
        }
    }

    @Override
    public final void onResume() {
        super.onResume();
        this.zzahy();
    }

    @Override
    public final void onStart() {
        super.onStart();
        this.zzahy();
    }

    @Override
    public final void onStop() {
        super.onStop();
        this.zzfmi.zzb(this);
    }

    @Override
    protected final void zza(ConnectionResult connectionResult, int n) {
        this.zzfmi.zza(connectionResult, n);
    }

    @Override
    protected final void zzagz() {
        this.zzfmi.zzagz();
    }

    final ArraySet<zzh<?>> zzahx() {
        return this.zzfqs;
    }
}


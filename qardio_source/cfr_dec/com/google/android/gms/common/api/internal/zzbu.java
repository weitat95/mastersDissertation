/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.api.internal.zzbv;
import com.google.android.gms.common.api.internal.zzcy;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzj;
import java.util.Set;

final class zzbu
implements zzcy,
zzj {
    private Set<Scope> zzehs = null;
    private final zzh<?> zzfmf;
    private final Api.zze zzfpv;
    private zzan zzfrh = null;
    final /* synthetic */ zzbm zzfti;
    private boolean zzftu = false;

    public zzbu(Api.zze zze2, zzh<?> zzh2) {
        this.zzfti = var1_1;
        this.zzfpv = zze2;
        this.zzfmf = zzh2;
    }

    static /* synthetic */ Api.zze zza(zzbu zzbu2) {
        return zzbu2.zzfpv;
    }

    static /* synthetic */ boolean zza(zzbu zzbu2, boolean bl) {
        zzbu2.zzftu = true;
        return true;
    }

    private final void zzajg() {
        if (this.zzftu && this.zzfrh != null) {
            this.zzfpv.zza(this.zzfrh, this.zzehs);
        }
    }

    static /* synthetic */ void zzb(zzbu zzbu2) {
        zzbu2.zzajg();
    }

    static /* synthetic */ zzh zzc(zzbu zzbu2) {
        return zzbu2.zzfmf;
    }

    @Override
    public final void zzb(zzan zzan2, Set<Scope> set) {
        if (zzan2 == null || set == null) {
            Log.wtf((String)"GoogleApiManager", (String)"Received null response from onSignInSuccess", (Throwable)new Exception());
            this.zzh(new ConnectionResult(4));
            return;
        }
        this.zzfrh = zzan2;
        this.zzehs = set;
        this.zzajg();
    }

    @Override
    public final void zzf(ConnectionResult connectionResult) {
        zzbm.zza(this.zzfti).post((Runnable)new zzbv(this, connectionResult));
    }

    @Override
    public final void zzh(ConnectionResult connectionResult) {
        ((zzbo)zzbm.zzj(this.zzfti).get(this.zzfmf)).zzh(connectionResult);
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.api.internal.zzcv;
import com.google.android.gms.common.api.internal.zzt;
import com.google.android.gms.common.api.internal.zzu;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.internal.zzcxe;

public final class zzz<O extends Api.ApiOptions>
extends GoogleApi<O> {
    private final Api.zza<? extends zzcxd, zzcxe> zzfmz;
    private final Api.zze zzfpv;
    private final zzt zzfpw;
    private final zzr zzfpx;

    public zzz(Context context, Api<O> api, Looper looper, Api.zze zze2, zzt zzt2, zzr zzr2, Api.zza<? extends zzcxd, zzcxe> zza2) {
        super(context, api, looper);
        this.zzfpv = zze2;
        this.zzfpw = zzt2;
        this.zzfpx = zzr2;
        this.zzfmz = zza2;
        this.zzfmi.zza(this);
    }

    @Override
    public final Api.zze zza(Looper looper, zzbo<O> zzbo2) {
        this.zzfpw.zza(zzbo2);
        return this.zzfpv;
    }

    @Override
    public final zzcv zza(Context context, Handler handler) {
        return new zzcv(context, handler, this.zzfpx, this.zzfmz);
    }

    public final Api.zze zzahp() {
        return this.zzfpv;
    }
}


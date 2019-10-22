/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzak;
import com.google.android.gms.common.api.internal.zzdg;
import com.google.android.gms.common.api.internal.zzm;

public final class zzbw<O extends Api.ApiOptions>
extends zzak {
    private final GoogleApi<O> zzftw;

    public zzbw(GoogleApi<O> googleApi) {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.zzftw = googleApi;
    }

    @Override
    public final Context getContext() {
        return this.zzftw.getApplicationContext();
    }

    @Override
    public final Looper getLooper() {
        return this.zzftw.getLooper();
    }

    @Override
    public final void zza(zzdg zzdg2) {
    }

    @Override
    public final void zzb(zzdg zzdg2) {
    }

    @Override
    public final <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        return this.zzftw.zza(t);
    }

    @Override
    public final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        return this.zzftw.zzb(t);
    }
}


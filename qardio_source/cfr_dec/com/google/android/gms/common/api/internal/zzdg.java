/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.api.internal.zzcs;
import com.google.android.gms.common.api.internal.zzdh;
import com.google.android.gms.common.api.internal.zzdi;
import com.google.android.gms.common.internal.zzbq;
import java.lang.ref.WeakReference;
import java.util.concurrent.Future;

public final class zzdg<R extends Result>
extends TransformedResult<R>
implements ResultCallback<R> {
    private final Object zzfou;
    private final WeakReference<GoogleApiClient> zzfow;
    private ResultTransform<? super R, ? extends Result> zzfux;
    private zzdg<? extends Result> zzfuy;
    private volatile ResultCallbacks<? super R> zzfuz;
    private PendingResult<R> zzfva;
    private Status zzfvb;
    private final zzdi zzfvc;
    private boolean zzfvd;

    static /* synthetic */ void zza(zzdg zzdg2, Result result) {
        zzdg.zzd(result);
    }

    static /* synthetic */ void zza(zzdg zzdg2, Status status) {
        zzdg2.zzd(status);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzajr() {
        block6: {
            block5: {
                if (this.zzfux == null && this.zzfuz == null) break block5;
                GoogleApiClient googleApiClient = (GoogleApiClient)this.zzfow.get();
                if (!this.zzfvd && this.zzfux != null && googleApiClient != null) {
                    googleApiClient.zza(this);
                    this.zzfvd = true;
                }
                if (this.zzfvb != null) {
                    this.zzx(this.zzfvb);
                    return;
                }
                if (this.zzfva != null) break block6;
            }
            return;
        }
        this.zzfva.setResultCallback(this);
    }

    private final boolean zzajt() {
        GoogleApiClient googleApiClient = (GoogleApiClient)this.zzfow.get();
        return this.zzfuz != null && googleApiClient != null;
    }

    static /* synthetic */ ResultTransform zzc(zzdg zzdg2) {
        return zzdg2.zzfux;
    }

    static /* synthetic */ zzdi zzd(zzdg zzdg2) {
        return zzdg2.zzfvc;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void zzd(Result object) {
        if (!(object instanceof Releasable)) return;
        try {
            ((Releasable)object).release();
            return;
        }
        catch (RuntimeException runtimeException) {
            object = String.valueOf(object);
            Log.w((String)"TransformedResultImpl", (String)new StringBuilder(String.valueOf(object).length() + 18).append("Unable to release ").append((String)object).toString(), (Throwable)runtimeException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzd(Status status) {
        Object object = this.zzfou;
        synchronized (object) {
            this.zzfvb = status;
            this.zzx(this.zzfvb);
            return;
        }
    }

    static /* synthetic */ WeakReference zze(zzdg zzdg2) {
        return zzdg2.zzfow;
    }

    static /* synthetic */ Object zzf(zzdg zzdg2) {
        return zzdg2.zzfou;
    }

    static /* synthetic */ zzdg zzg(zzdg zzdg2) {
        return zzdg2.zzfuy;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzx(Status status) {
        Object object = this.zzfou;
        synchronized (object) {
            if (this.zzfux != null) {
                status = this.zzfux.onFailure(status);
                zzbq.checkNotNull(status, "onFailure must not return null");
                super.zzd(status);
            } else if (this.zzajt()) {
                this.zzfuz.onFailure(status);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void onResult(R r) {
        Object object = this.zzfou;
        synchronized (object) {
            if (r.getStatus().isSuccess()) {
                if (this.zzfux != null) {
                    zzcs.zzaip().submit(new zzdh(this, (Result)r));
                } else if (this.zzajt()) {
                    this.zzfuz.onSuccess(r);
                }
            } else {
                this.zzd(r.getStatus());
                zzdg.zzd(r);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zza(PendingResult<?> pendingResult) {
        Object object = this.zzfou;
        synchronized (object) {
            this.zzfva = pendingResult;
            this.zzajr();
            return;
        }
    }

    final void zzajs() {
        this.zzfuz = null;
    }
}


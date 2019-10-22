/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zzcw;
import com.google.android.gms.common.api.internal.zzcx;
import com.google.android.gms.common.api.internal.zzcy;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbt;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzcxa;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.internal.zzcxe;
import com.google.android.gms.internal.zzcxi;
import com.google.android.gms.internal.zzcxj;
import com.google.android.gms.internal.zzcxn;
import com.google.android.gms.internal.zzcxq;
import java.util.Set;

public final class zzcv
extends zzcxi
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private static Api.zza<? extends zzcxd, zzcxe> zzfut = zzcxa.zzebg;
    private final Context mContext;
    private final Handler mHandler;
    private Set<Scope> zzehs;
    private final Api.zza<? extends zzcxd, zzcxe> zzfls;
    private zzr zzfpx;
    private zzcxd zzfrd;
    private zzcy zzfuu;

    public zzcv(Context context, Handler handler, zzr zzr2) {
        this(context, handler, zzr2, zzfut);
    }

    public zzcv(Context context, Handler handler, zzr zzr2, Api.zza<? extends zzcxd, zzcxe> zza2) {
        this.mContext = context;
        this.mHandler = handler;
        this.zzfpx = zzbq.checkNotNull(zzr2, "ClientSettings must not be null");
        this.zzehs = zzr2.zzakv();
        this.zzfls = zza2;
    }

    static /* synthetic */ zzcy zza(zzcv zzcv2) {
        return zzcv2.zzfuu;
    }

    static /* synthetic */ void zza(zzcv zzcv2, zzcxq zzcxq2) {
        zzcv2.zzc(zzcxq2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzc(zzcxq zzbfm2) {
        Object object = ((zzcxq)zzbfm2).zzahf();
        if (((ConnectionResult)object).isSuccess()) {
            object = ((zzcxq)zzbfm2).zzbdi();
            zzbfm2 = ((zzbt)object).zzahf();
            if (!((ConnectionResult)zzbfm2).isSuccess()) {
                object = String.valueOf(zzbfm2);
                Log.wtf((String)"SignInCoordinator", (String)new StringBuilder(String.valueOf(object).length() + 48).append("Sign-in succeeded with resolve account failure: ").append((String)object).toString(), (Throwable)new Exception());
                this.zzfuu.zzh((ConnectionResult)zzbfm2);
                this.zzfrd.disconnect();
                return;
            }
            this.zzfuu.zzb(((zzbt)object).zzalp(), this.zzehs);
        } else {
            this.zzfuu.zzh((ConnectionResult)object);
        }
        this.zzfrd.disconnect();
    }

    @Override
    public final void onConnected(Bundle bundle) {
        this.zzfrd.zza(this);
    }

    @Override
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzfuu.zzh(connectionResult);
    }

    @Override
    public final void onConnectionSuspended(int n) {
        this.zzfrd.disconnect();
    }

    public final void zza(zzcy zzcy2) {
        if (this.zzfrd != null) {
            this.zzfrd.disconnect();
        }
        this.zzfpx.zzc(System.identityHashCode(this));
        this.zzfrd = this.zzfls.zza(this.mContext, this.mHandler.getLooper(), this.zzfpx, this.zzfpx.zzalb(), this, this);
        this.zzfuu = zzcy2;
        if (this.zzehs == null || this.zzehs.isEmpty()) {
            this.mHandler.post((Runnable)new zzcw(this));
            return;
        }
        this.zzfrd.connect();
    }

    public final zzcxd zzaje() {
        return this.zzfrd;
    }

    public final void zzajq() {
        if (this.zzfrd != null) {
            this.zzfrd.disconnect();
        }
    }

    @Override
    public final void zzb(zzcxq zzcxq2) {
        this.mHandler.post((Runnable)new zzcx(this, zzcxq2));
    }
}


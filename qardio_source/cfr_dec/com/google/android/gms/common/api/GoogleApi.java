/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.api.internal.zzbw;
import com.google.android.gms.common.api.internal.zzcv;
import com.google.android.gms.common.api.internal.zzcz;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.common.api.zzd;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzs;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class GoogleApi<O extends Api.ApiOptions> {
    private final Context mContext;
    private final int mId;
    private final Looper zzall;
    private final Api<O> zzfin;
    private final O zzfme;
    private final zzh<O> zzfmf;
    private final GoogleApiClient zzfmg;
    private final zzcz zzfmh;
    protected final zzbm zzfmi;

    protected GoogleApi(Context context, Api<O> api, Looper looper) {
        zzbq.checkNotNull(context, "Null context is not permitted.");
        zzbq.checkNotNull(api, "Api must not be null.");
        zzbq.checkNotNull(looper, "Looper must not be null.");
        this.mContext = context.getApplicationContext();
        this.zzfin = api;
        this.zzfme = null;
        this.zzall = looper;
        this.zzfmf = zzh.zzb(api);
        this.zzfmg = new zzbw(this);
        this.zzfmi = zzbm.zzcj(this.mContext);
        this.mId = this.zzfmi.zzais();
        this.zzfmh = new zzg();
    }

    public GoogleApi(Context context, Api<O> api, O o, zza zza2) {
        zzbq.checkNotNull(context, "Null context is not permitted.");
        zzbq.checkNotNull(api, "Api must not be null.");
        zzbq.checkNotNull(zza2, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = context.getApplicationContext();
        this.zzfin = api;
        this.zzfme = o;
        this.zzall = zza2.zzfml;
        this.zzfmf = zzh.zza(this.zzfin, this.zzfme);
        this.zzfmg = new zzbw(this);
        this.zzfmi = zzbm.zzcj(this.mContext);
        this.mId = this.zzfmi.zzais();
        this.zzfmh = zza2.zzfmk;
        this.zzfmi.zza(this);
    }

    @Deprecated
    public GoogleApi(Context context, Api<O> api, O o, zzcz zzcz2) {
        this(context, api, o, new zzd().zza(zzcz2).zzagq());
    }

    private final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zza(int n, T t) {
        t.zzahi();
        this.zzfmi.zza(this, n, t);
        return t;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final zzs zzagp() {
        Object object;
        zzs zzs2 = new zzs();
        object = this.zzfme instanceof Api.ApiOptions.HasGoogleSignInAccountOptions && (object = ((Api.ApiOptions.HasGoogleSignInAccountOptions)this.zzfme).getGoogleSignInAccount()) != null ? ((GoogleSignInAccount)object).getAccount() : (this.zzfme instanceof Api.ApiOptions.HasAccountOptions ? ((Api.ApiOptions.HasAccountOptions)this.zzfme).getAccount() : null);
        zzs2 = zzs2.zze((Account)object);
        if (this.zzfme instanceof Api.ApiOptions.HasGoogleSignInAccountOptions && (object = ((Api.ApiOptions.HasGoogleSignInAccountOptions)this.zzfme).getGoogleSignInAccount()) != null) {
            object = ((GoogleSignInAccount)object).zzabb();
            return zzs2.zze((Collection<Scope>)object);
        }
        object = Collections.emptySet();
        return zzs2.zze((Collection<Scope>)object);
    }

    public final Context getApplicationContext() {
        return this.mContext;
    }

    public final int getInstanceId() {
        return this.mId;
    }

    public final Looper getLooper() {
        return this.zzall;
    }

    public Api.zze zza(Looper looper, zzbo<O> zzbo2) {
        zzr zzr2 = this.zzagp().zzgf(this.mContext.getPackageName()).zzgg(this.mContext.getClass().getName()).zzald();
        return this.zzfin.zzage().zza(this.mContext, looper, zzr2, this.zzfme, zzbo2, zzbo2);
    }

    public zzcv zza(Context context, Handler handler) {
        return new zzcv(context, handler, this.zzagp().zzald());
    }

    public final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zza(T t) {
        return this.zza(0, t);
    }

    public final Api<O> zzagl() {
        return this.zzfin;
    }

    public final zzh<O> zzagn() {
        return this.zzfmf;
    }

    public final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zzb(T t) {
        return this.zza(1, t);
    }

    public static final class zza {
        public static final zza zzfmj = new zzd().zzagq();
        public final zzcz zzfmk;
        public final Looper zzfml;

        private zza(zzcz zzcz2, Account account, Looper looper) {
            this.zzfmk = zzcz2;
            this.zzfml = looper;
        }

        /* synthetic */ zza(zzcz zzcz2, Account account, Looper looper, zzc zzc2) {
            this(zzcz2, null, looper);
        }
    }

}


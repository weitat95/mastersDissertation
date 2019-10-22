/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zzap;
import com.google.android.gms.common.api.internal.zzaq;
import com.google.android.gms.common.api.internal.zzar;
import com.google.android.gms.common.api.internal.zzau;
import com.google.android.gms.common.api.internal.zzax;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzbh;
import com.google.android.gms.common.api.internal.zzbi;
import com.google.android.gms.common.api.internal.zzbl;
import com.google.android.gms.common.api.internal.zzcd;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzbt;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.internal.zzcxe;
import com.google.android.gms.internal.zzcxq;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public final class zzao
implements zzbh {
    private final Context mContext;
    private final Api.zza<? extends zzcxd, zzcxe> zzfmz;
    private final Lock zzfps;
    private final zzr zzfpx;
    private final Map<Api<?>, Boolean> zzfqa;
    private final zzf zzfqc;
    private ConnectionResult zzfql;
    private final zzbi zzfqv;
    private int zzfqy;
    private int zzfqz = 0;
    private int zzfra;
    private final Bundle zzfrb = new Bundle();
    private final Set<Api.zzc> zzfrc = new HashSet<Api.zzc>();
    private zzcxd zzfrd;
    private boolean zzfre;
    private boolean zzfrf;
    private boolean zzfrg;
    private zzan zzfrh;
    private boolean zzfri;
    private boolean zzfrj;
    private ArrayList<Future<?>> zzfrk = new ArrayList();

    public zzao(zzbi zzbi2, zzr zzr2, Map<Api<?>, Boolean> map, zzf zzf2, Api.zza<? extends zzcxd, zzcxe> zza2, Lock lock, Context context) {
        this.zzfqv = zzbi2;
        this.zzfpx = zzr2;
        this.zzfqa = map;
        this.zzfqc = zzf2;
        this.zzfmz = zza2;
        this.zzfps = lock;
        this.mContext = context;
    }

    static /* synthetic */ Context zza(zzao zzao2) {
        return zzao2.mContext;
    }

    static /* synthetic */ void zza(zzao zzao2, ConnectionResult connectionResult) {
        zzao2.zze(connectionResult);
    }

    static /* synthetic */ void zza(zzao zzao2, ConnectionResult connectionResult, Api api, boolean bl) {
        zzao2.zzb(connectionResult, api, bl);
    }

    static /* synthetic */ void zza(zzao zzao2, zzcxq zzcxq2) {
        zzao2.zza(zzcxq2);
    }

    private final void zza(zzcxq zzbfm2) {
        if (!this.zzbt(0)) {
            return;
        }
        Object object = ((zzcxq)zzbfm2).zzahf();
        if (((ConnectionResult)object).isSuccess()) {
            object = ((zzcxq)zzbfm2).zzbdi();
            zzbfm2 = ((zzbt)object).zzahf();
            if (!((ConnectionResult)zzbfm2).isSuccess()) {
                object = String.valueOf(zzbfm2);
                Log.wtf((String)"GoogleApiClientConnecting", (String)new StringBuilder(String.valueOf(object).length() + 48).append("Sign-in succeeded with resolve account failure: ").append((String)object).toString(), (Throwable)new Exception());
                this.zze((ConnectionResult)zzbfm2);
                return;
            }
            this.zzfrg = true;
            this.zzfrh = ((zzbt)object).zzalp();
            this.zzfri = ((zzbt)object).zzalq();
            this.zzfrj = ((zzbt)object).zzalr();
            this.zzaid();
            return;
        }
        if (this.zzd((ConnectionResult)object)) {
            this.zzaif();
            this.zzaid();
            return;
        }
        this.zze((ConnectionResult)object);
    }

    static /* synthetic */ boolean zza(zzao zzao2, int n) {
        return zzao2.zzbt(0);
    }

    private final boolean zzaic() {
        --this.zzfra;
        if (this.zzfra > 0) {
            return false;
        }
        if (this.zzfra < 0) {
            Log.w((String)"GoogleApiClientConnecting", (String)this.zzfqv.zzfpi.zzaim());
            Log.wtf((String)"GoogleApiClientConnecting", (String)"GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", (Throwable)new Exception());
            this.zze(new ConnectionResult(8, null));
            return false;
        }
        if (this.zzfql != null) {
            this.zzfqv.zzfst = this.zzfqy;
            this.zze(this.zzfql);
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzaid() {
        ArrayList<Api.zze> arrayList;
        block6: {
            block5: {
                if (this.zzfra != 0 || this.zzfrf && !this.zzfrg) break block5;
                arrayList = new ArrayList<Api.zze>();
                this.zzfqz = 1;
                this.zzfra = this.zzfqv.zzfsb.size();
                for (Api.zzc<?> zzc2 : this.zzfqv.zzfsb.keySet()) {
                    if (this.zzfqv.zzfsq.containsKey(zzc2)) {
                        if (!this.zzaic()) continue;
                        this.zzaie();
                        continue;
                    }
                    arrayList.add(this.zzfqv.zzfsb.get(zzc2));
                }
                if (!arrayList.isEmpty()) break block6;
            }
            return;
        }
        this.zzfrk.add(zzbl.zzaip().submit(new zzau(this, arrayList)));
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzaie() {
        this.zzfqv.zzaio();
        zzbl.zzaip().execute(new zzap(this));
        if (this.zzfrd != null) {
            if (this.zzfri) {
                this.zzfrd.zza(this.zzfrh, this.zzfrj);
            }
            this.zzbg(false);
        }
        for (Api.zzc<?> zzc2 : this.zzfqv.zzfsq.keySet()) {
            this.zzfqv.zzfsb.get(zzc2).disconnect();
        }
        Object object = this.zzfrb.isEmpty() ? null : this.zzfrb;
        this.zzfqv.zzfsu.zzj((Bundle)object);
    }

    private final void zzaif() {
        this.zzfrf = false;
        this.zzfqv.zzfpi.zzfsc = Collections.emptySet();
        for (Api.zzc zzc2 : this.zzfrc) {
            if (this.zzfqv.zzfsq.containsKey(zzc2)) continue;
            this.zzfqv.zzfsq.put(zzc2, new ConnectionResult(17, null));
        }
    }

    private final void zzaig() {
        ArrayList<Future<?>> arrayList = this.zzfrk;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            Future<?> future = arrayList.get(i);
            future.cancel(true);
        }
        this.zzfrk.clear();
    }

    private final Set<Scope> zzaih() {
        if (this.zzfpx == null) {
            return Collections.emptySet();
        }
        HashSet<Scope> hashSet = new HashSet<Scope>(this.zzfpx.zzakv());
        Map<Api<?>, zzt> map = this.zzfpx.zzakx();
        for (Api<?> api : map.keySet()) {
            if (this.zzfqv.zzfsq.containsKey(api.zzagf())) continue;
            hashSet.addAll(map.get(api).zzehs);
        }
        return hashSet;
    }

    static /* synthetic */ zzf zzb(zzao zzao2) {
        return zzao2.zzfqc;
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final void zzb(ConnectionResult var1_1, Api<?> var2_2, boolean var3_3) {
        block5: {
            block7: {
                block6: {
                    var5_4 = true;
                    var6_5 = var2_2.zzagd().getPriority();
                    if (!var3_3) ** GOTO lbl-1000
                    if (!var1_1.hasResolution()) break block6;
                    var4_6 = true;
lbl6:
                    // 3 sources
                    while (var4_6) lbl-1000:
                    // 2 sources
                    {
                        var4_6 = var5_4;
                        if (this.zzfql != null) {
                            if (var6_5 >= this.zzfqy) break block5;
                            var4_6 = var5_4;
                        }
lbl11:
                        // 4 sources
                        do {
                            if (var4_6) {
                                this.zzfql = var1_1;
                                this.zzfqy = var6_5;
                            }
                            this.zzfqv.zzfsq.put(var2_2.zzagf(), var1_1);
                            return;
                            break;
                        } while (true);
                    }
                    break block5;
                }
                if (this.zzfqc.zzbp(var1_1.getErrorCode()) == null) break block7;
                var4_6 = true;
                ** GOTO lbl6
            }
            var4_6 = false;
            ** GOTO lbl6
        }
        var4_6 = false;
        ** while (true)
    }

    static /* synthetic */ boolean zzb(zzao zzao2, ConnectionResult connectionResult) {
        return zzao2.zzd(connectionResult);
    }

    private final void zzbg(boolean bl) {
        if (this.zzfrd != null) {
            if (this.zzfrd.isConnected() && bl) {
                this.zzfrd.zzbdb();
            }
            this.zzfrd.disconnect();
            this.zzfrh = null;
        }
    }

    private final boolean zzbt(int n) {
        if (this.zzfqz != n) {
            Log.w((String)"GoogleApiClientConnecting", (String)this.zzfqv.zzfpi.zzaim());
            String string2 = String.valueOf(this);
            Log.w((String)"GoogleApiClientConnecting", (String)new StringBuilder(String.valueOf(string2).length() + 23).append("Unexpected callback in ").append(string2).toString());
            int n2 = this.zzfra;
            Log.w((String)"GoogleApiClientConnecting", (String)new StringBuilder(33).append("mRemainingConnections=").append(n2).toString());
            string2 = zzao.zzbu(this.zzfqz);
            String string3 = zzao.zzbu(n);
            Log.wtf((String)"GoogleApiClientConnecting", (String)new StringBuilder(String.valueOf(string2).length() + 70 + String.valueOf(string3).length()).append("GoogleApiClient connecting is in step ").append(string2).append(" but received callback for step ").append(string3).toString(), (Throwable)new Exception());
            this.zze(new ConnectionResult(8, null));
            return false;
        }
        return true;
    }

    private static String zzbu(int n) {
        switch (n) {
            default: {
                return "UNKNOWN";
            }
            case 0: {
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            }
            case 1: 
        }
        return "STEP_GETTING_REMOTE_SERVICE";
    }

    static /* synthetic */ Lock zzc(zzao zzao2) {
        return zzao2.zzfps;
    }

    static /* synthetic */ zzbi zzd(zzao zzao2) {
        return zzao2.zzfqv;
    }

    private final boolean zzd(ConnectionResult connectionResult) {
        return this.zzfre && !connectionResult.hasResolution();
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zze(ConnectionResult connectionResult) {
        this.zzaig();
        boolean bl = !connectionResult.hasResolution();
        this.zzbg(bl);
        this.zzfqv.zzg(connectionResult);
        this.zzfqv.zzfsu.zzc(connectionResult);
    }

    static /* synthetic */ boolean zze(zzao zzao2) {
        return zzao2.zzfrf;
    }

    static /* synthetic */ zzcxd zzf(zzao zzao2) {
        return zzao2.zzfrd;
    }

    static /* synthetic */ Set zzg(zzao zzao2) {
        return zzao2.zzaih();
    }

    static /* synthetic */ zzan zzh(zzao zzao2) {
        return zzao2.zzfrh;
    }

    static /* synthetic */ void zzi(zzao zzao2) {
        zzao2.zzaif();
    }

    static /* synthetic */ void zzj(zzao zzao2) {
        zzao2.zzaid();
    }

    static /* synthetic */ boolean zzk(zzao zzao2) {
        return zzao2.zzaic();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void begin() {
        this.zzfqv.zzfsq.clear();
        this.zzfrf = false;
        this.zzfql = null;
        this.zzfqz = 0;
        this.zzfre = true;
        this.zzfrg = false;
        this.zzfri = false;
        HashMap<Api.zze, zzaq> hashMap = new HashMap<Api.zze, zzaq>();
        Object object = this.zzfqa.keySet().iterator();
        boolean bl = false;
        while (object.hasNext()) {
            Api<?> api = object.next();
            Api.zze zze2 = this.zzfqv.zzfsb.get(api.zzagf());
            boolean bl2 = api.zzagd().getPriority() == 1;
            boolean bl3 = this.zzfqa.get(api);
            if (zze2.zzaay()) {
                this.zzfrf = true;
                if (bl3) {
                    this.zzfrc.add(api.zzagf());
                } else {
                    this.zzfre = false;
                }
            }
            hashMap.put(zze2, new zzaq(this, api, bl3));
            bl = bl2 | bl;
        }
        if (bl) {
            this.zzfrf = false;
        }
        if (this.zzfrf) {
            this.zzfpx.zzc(System.identityHashCode(this.zzfqv.zzfpi));
            object = new zzax(this, null);
            this.zzfrd = this.zzfmz.zza(this.mContext, ((GoogleApiClient)this.zzfqv.zzfpi).getLooper(), this.zzfpx, this.zzfpx.zzalb(), (GoogleApiClient.ConnectionCallbacks)object, (GoogleApiClient.OnConnectionFailedListener)object);
        }
        this.zzfra = this.zzfqv.zzfsb.size();
        this.zzfrk.add(zzbl.zzaip().submit(new zzar(this, hashMap)));
    }

    @Override
    public final void connect() {
    }

    @Override
    public final boolean disconnect() {
        this.zzaig();
        this.zzbg(true);
        this.zzfqv.zzg(null);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void onConnected(Bundle bundle) {
        block5: {
            block4: {
                if (!this.zzbt(1)) break block4;
                if (bundle != null) {
                    this.zzfrb.putAll(bundle);
                }
                if (this.zzaic()) break block5;
            }
            return;
        }
        this.zzaie();
    }

    @Override
    public final void onConnectionSuspended(int n) {
        this.zze(new ConnectionResult(8, null));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean bl) {
        block3: {
            block2: {
                if (!this.zzbt(1)) break block2;
                this.zzb(connectionResult, api, bl);
                if (this.zzaic()) break block3;
            }
            return;
        }
        this.zzaie();
    }

    @Override
    public final <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        this.zzfqv.zzfpi.zzfqg.add(t);
        return t;
    }

    @Override
    public final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}


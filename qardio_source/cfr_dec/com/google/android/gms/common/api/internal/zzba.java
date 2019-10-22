/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzaa;
import com.google.android.gms.common.api.internal.zzbb;
import com.google.android.gms.common.api.internal.zzbf;
import com.google.android.gms.common.api.internal.zzbg;
import com.google.android.gms.common.api.internal.zzbi;
import com.google.android.gms.common.api.internal.zzbx;
import com.google.android.gms.common.api.internal.zzcc;
import com.google.android.gms.common.api.internal.zzcd;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.common.api.internal.zzdg;
import com.google.android.gms.common.api.internal.zzdj;
import com.google.android.gms.common.api.internal.zzdm;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.api.internal.zzt;
import com.google.android.gms.common.api.internal.zzv;
import com.google.android.gms.common.internal.zzae;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.internal.zzcxe;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public final class zzba
extends GoogleApiClient
implements zzcd {
    private final Context mContext;
    private final Looper zzall;
    private final int zzfmw;
    private final GoogleApiAvailability zzfmy;
    private Api.zza<? extends zzcxd, zzcxe> zzfmz;
    private boolean zzfnc;
    private final Lock zzfps;
    private zzr zzfpx;
    private Map<Api<?>, Boolean> zzfqa;
    final Queue<zzm<?, ?>> zzfqg = new LinkedList();
    private final zzae zzfru;
    private zzcc zzfrv = null;
    private volatile boolean zzfrw;
    private long zzfrx = 120000L;
    private long zzfry = 5000L;
    private final zzbf zzfrz;
    private zzbx zzfsa;
    final Map<Api.zzc<?>, Api.zze> zzfsb;
    Set<Scope> zzfsc = new HashSet<Scope>();
    private final zzcm zzfsd = new zzcm();
    private final ArrayList<zzt> zzfse;
    private Integer zzfsf = null;
    Set<zzdg> zzfsg = null;
    final zzdj zzfsh;
    private final zzaf zzfsi = new zzbb(this);

    /*
     * WARNING - void declaration
     */
    public zzba(Context object, Lock object22, Looper looper, zzr zzr2, GoogleApiAvailability googleApiAvailability, Api.zza<? extends zzcxd, zzcxe> zza2, Map<Api<?>, Boolean> map, List<GoogleApiClient.ConnectionCallbacks> list, List<GoogleApiClient.OnConnectionFailedListener> list2, Map<Api.zzc<?>, Api.zze> map2, int n, int n2, ArrayList<zzt> arrayList, boolean bl) {
        void var4_8;
        void var9_13;
        void var11_15;
        void var6_10;
        void var8_12;
        void var10_14;
        void var7_11;
        void var5_9;
        void var13_17;
        void var3_7;
        this.mContext = object;
        this.zzfps = object22;
        this.zzfnc = false;
        this.zzfru = new zzae((Looper)var3_7, this.zzfsi);
        this.zzall = var3_7;
        this.zzfrz = new zzbf(this, (Looper)var3_7);
        this.zzfmy = var5_9;
        this.zzfmw = var11_15;
        if (this.zzfmw >= 0) {
            void var12_16;
            this.zzfsf = (int)var12_16;
        }
        this.zzfqa = var7_11;
        this.zzfsb = var10_14;
        this.zzfse = var13_17;
        this.zzfsh = new zzdj(this.zzfsb);
        for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : var8_12) {
            this.zzfru.registerConnectionCallbacks(connectionCallbacks);
        }
        for (GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener : var9_13) {
            this.zzfru.registerConnectionFailedListener(onConnectionFailedListener);
        }
        this.zzfpx = var4_8;
        this.zzfmz = var6_10;
    }

    private final void resume() {
        this.zzfps.lock();
        try {
            if (this.zzfrw) {
                this.zzaii();
            }
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    public static int zza(Iterable<Api.zze> object, boolean bl) {
        int n = 1;
        object = object.iterator();
        boolean bl2 = false;
        int n2 = 0;
        while (object.hasNext()) {
            Api.zze zze2 = (Api.zze)object.next();
            if (zze2.zzaay()) {
                n2 = 1;
            }
            if (!zze2.zzabj()) continue;
            bl2 = true;
        }
        if (n2 != 0) {
            n2 = n;
            if (bl2) {
                n2 = n;
                if (bl) {
                    n2 = 2;
                }
            }
            return n2;
        }
        return 3;
    }

    static /* synthetic */ void zza(zzba zzba2) {
        zzba2.resume();
    }

    private final void zzaii() {
        this.zzfru.zzalj();
        this.zzfrv.connect();
    }

    private final void zzaij() {
        this.zzfps.lock();
        try {
            if (this.zzaik()) {
                this.zzaii();
            }
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    static /* synthetic */ void zzb(zzba zzba2) {
        zzba2.zzaij();
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzbv(int n) {
        block14: {
            block13: {
                if (this.zzfsf == null) {
                    this.zzfsf = n;
                } else if (this.zzfsf != n) {
                    String string2 = zzba.zzbw(n);
                    String string3 = zzba.zzbw(this.zzfsf);
                    throw new IllegalStateException(new StringBuilder(String.valueOf(string2).length() + 51 + String.valueOf(string3).length()).append("Cannot use sign-in mode: ").append(string2).append(". Mode was already set to ").append(string3).toString());
                }
                if (this.zzfrv != null) {
                    return;
                }
                Iterator<Api.zze> iterator = this.zzfsb.values().iterator();
                n = 0;
                boolean bl = false;
                while (iterator.hasNext()) {
                    Api.zze zze2 = iterator.next();
                    if (zze2.zzaay()) {
                        bl = true;
                    }
                    if (!zze2.zzabj()) continue;
                    n = 1;
                }
                switch (this.zzfsf) {
                    case 1: {
                        if (!bl) {
                            throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                        }
                        if (n == 0) break;
                        throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                    }
                    case 2: {
                        if (!bl) break;
                        if (this.zzfnc) {
                            this.zzfrv = new zzaa(this.mContext, this.zzfps, this.zzall, this.zzfmy, this.zzfsb, this.zzfpx, this.zzfqa, this.zzfmz, this.zzfse, this, true);
                            return;
                        }
                        break block13;
                    }
                }
                if (this.zzfnc && n == 0) {
                    this.zzfrv = new zzaa(this.mContext, this.zzfps, this.zzall, this.zzfmy, this.zzfsb, this.zzfpx, this.zzfqa, this.zzfmz, this.zzfse, this, false);
                    return;
                }
                break block14;
            }
            this.zzfrv = zzv.zza(this.mContext, this, this.zzfps, this.zzall, this.zzfmy, this.zzfsb, this.zzfpx, this.zzfqa, this.zzfmz, this.zzfse);
            return;
        }
        this.zzfrv = new zzbi(this.mContext, this, this.zzfps, this.zzall, this.zzfmy, this.zzfsb, this.zzfpx, this.zzfqa, this.zzfmz, this.zzfse, this);
    }

    private static String zzbw(int n) {
        switch (n) {
            default: {
                return "UNKNOWN";
            }
            case 3: {
                return "SIGN_IN_MODE_NONE";
            }
            case 1: {
                return "SIGN_IN_MODE_REQUIRED";
            }
            case 2: 
        }
        return "SIGN_IN_MODE_OPTIONAL";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final ConnectionResult blockingConnect(long l, TimeUnit object) {
        boolean bl = false;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            bl = true;
        }
        zzbq.zza(bl, "blockingConnect must not be called on the UI thread");
        zzbq.checkNotNull(object, "TimeUnit must not be null");
        this.zzfps.lock();
        try {
            if (this.zzfsf == null) {
                this.zzfsf = zzba.zza(this.zzfsb.values(), false);
            } else if (this.zzfsf == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            this.zzbv(this.zzfsf);
            this.zzfru.zzalj();
            ConnectionResult connectionResult = this.zzfrv.blockingConnect(l, (TimeUnit)((Object)object));
            return connectionResult;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void connect() {
        boolean bl = false;
        this.zzfps.lock();
        try {
            if (this.zzfmw >= 0) {
                if (this.zzfsf != null) {
                    bl = true;
                }
                zzbq.zza(bl, "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzfsf == null) {
                this.zzfsf = zzba.zza(this.zzfsb.values(), false);
            } else if (this.zzfsf == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            ((GoogleApiClient)this).connect(this.zzfsf);
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void connect(int n) {
        boolean bl = true;
        this.zzfps.lock();
        boolean bl2 = bl;
        if (n != 3) {
            bl2 = bl;
            if (n != 1) {
                bl2 = n == 2 ? bl : false;
            }
        }
        try {
            zzbq.checkArgument(bl2, new StringBuilder(33).append("Illegal sign-in mode: ").append(n).toString());
            this.zzbv(n);
            this.zzaii();
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void disconnect() {
        this.zzfps.lock();
        try {
            this.zzfsh.release();
            if (this.zzfrv != null) {
                this.zzfrv.disconnect();
            }
            this.zzfsd.release();
            for (zzm zzm2 : this.zzfqg) {
                ((BasePendingResult)zzm2).zza((zzdm)null);
                ((PendingResult)zzm2).cancel();
            }
            this.zzfqg.clear();
            zzcc zzcc2 = this.zzfrv;
            if (zzcc2 == null) {
                this.zzfps.unlock();
                return;
            }
            this.zzaik();
            this.zzfru.zzali();
            this.zzfps.unlock();
            return;
        }
        catch (Throwable throwable) {
            this.zzfps.unlock();
            throw throwable;
        }
    }

    @Override
    public final void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.append(string2).append("mContext=").println((Object)this.mContext);
        printWriter.append(string2).append("mResuming=").print(this.zzfrw);
        printWriter.append(" mWorkQueue.size()=").print(this.zzfqg.size());
        zzdj zzdj2 = this.zzfsh;
        printWriter.append(" mUnconsumedApiCalls.size()=").println(zzdj2.zzfvi.size());
        if (this.zzfrv != null) {
            this.zzfrv.dump(string2, fileDescriptor, printWriter, arrstring);
        }
    }

    @Override
    public final Context getContext() {
        return this.mContext;
    }

    @Override
    public final Looper getLooper() {
        return this.zzall;
    }

    @Override
    public final boolean isConnected() {
        return this.zzfrv != null && this.zzfrv.isConnected();
    }

    @Override
    public final boolean isConnecting() {
        return this.zzfrv != null && this.zzfrv.isConnecting();
    }

    @Override
    public final void reconnect() {
        ((GoogleApiClient)this).disconnect();
        ((GoogleApiClient)this).connect();
    }

    @Override
    public final void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzfru.registerConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public final void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzfru.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Override
    public final void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzfru.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @Override
    public final void zza(zzdg zzdg2) {
        this.zzfps.lock();
        try {
            if (this.zzfsg == null) {
                this.zzfsg = new HashSet<zzdg>();
            }
            this.zzfsg.add(zzdg2);
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    final boolean zzaik() {
        if (!this.zzfrw) {
            return false;
        }
        this.zzfrw = false;
        this.zzfrz.removeMessages(2);
        this.zzfrz.removeMessages(1);
        if (this.zzfsa != null) {
            this.zzfsa.unregister();
            this.zzfsa = null;
        }
        return true;
    }

    final boolean zzail() {
        boolean bl;
        block4: {
            bl = false;
            this.zzfps.lock();
            try {
                Set<zzdg> set = this.zzfsg;
                if (set != null) break block4;
                this.zzfps.unlock();
                return false;
            }
            catch (Throwable throwable) {
                this.zzfps.unlock();
                throw throwable;
            }
        }
        boolean bl2 = this.zzfsg.isEmpty();
        if (!bl2) {
            bl = true;
        }
        this.zzfps.unlock();
        return bl;
    }

    final String zzaim() {
        StringWriter stringWriter = new StringWriter();
        ((GoogleApiClient)this).dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void zzb(zzdg zzdg2) {
        this.zzfps.lock();
        try {
            if (this.zzfsg == null) {
                Log.wtf((String)"GoogleApiClientImpl", (String)"Attempted to remove pending transform when no transforms are registered.", (Throwable)new Exception());
                return;
            }
            if (!this.zzfsg.remove(zzdg2)) {
                Log.wtf((String)"GoogleApiClientImpl", (String)"Failed to remove pending transform - this may lead to memory leaks!", (Throwable)new Exception());
                return;
            }
            if (this.zzail()) return;
            this.zzfrv.zzahk();
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    @Override
    public final void zzc(ConnectionResult connectionResult) {
        if (!zzf.zze(this.mContext, connectionResult.getErrorCode())) {
            this.zzaik();
        }
        if (!this.zzfrw) {
            this.zzfru.zzk(connectionResult);
            this.zzfru.zzali();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        boolean bl = t.zzagf() != null;
        zzbq.checkArgument(bl, "This task can not be enqueued (it's probably a Batch or malformed)");
        bl = this.zzfsb.containsKey(t.zzagf());
        String string2 = t.zzagl() != null ? t.zzagl().getName() : "the API";
        zzbq.checkArgument(bl, new StringBuilder(String.valueOf(string2).length() + 65).append("GoogleApiClient is not configured to use ").append(string2).append(" required for this call.").toString());
        this.zzfps.lock();
        try {
            if (this.zzfrv == null) {
                this.zzfqg.add(t);
                return t;
            }
            t = this.zzfrv.zzd(t);
            return t;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        boolean bl = t.zzagf() != null;
        zzbq.checkArgument(bl, "This task can not be executed (it's probably a Batch or malformed)");
        bl = this.zzfsb.containsKey(t.zzagf());
        Object object = t.zzagl() != null ? t.zzagl().getName() : "the API";
        zzbq.checkArgument(bl, new StringBuilder(String.valueOf(object).length() + 65).append("GoogleApiClient is not configured to use ").append((String)object).append(" required for this call.").toString());
        this.zzfps.lock();
        try {
            if (this.zzfrv == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            }
            if (this.zzfrw) {
                this.zzfqg.add(t);
                while (!this.zzfqg.isEmpty()) {
                    object = this.zzfqg.remove();
                    this.zzfsh.zzb((BasePendingResult<? extends Result>)object);
                    ((zzm)object).zzu(Status.zzfnk);
                }
                return t;
            }
            t = this.zzfrv.zze(t);
            return t;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    @Override
    public final void zzf(int n, boolean bl) {
        if (n == 1 && !bl && !this.zzfrw) {
            this.zzfrw = true;
            if (this.zzfsa == null) {
                this.zzfsa = GoogleApiAvailability.zza(this.mContext.getApplicationContext(), new zzbg(this));
            }
            this.zzfrz.sendMessageDelayed(this.zzfrz.obtainMessage(1), this.zzfrx);
            this.zzfrz.sendMessageDelayed(this.zzfrz.obtainMessage(2), this.zzfry);
        }
        this.zzfsh.zzaju();
        this.zzfru.zzcg(n);
        this.zzfru.zzali();
        if (n == 2) {
            this.zzaii();
        }
    }

    @Override
    public final void zzj(Bundle bundle) {
        while (!this.zzfqg.isEmpty()) {
            ((GoogleApiClient)this).zze(this.zzfqg.remove());
        }
        this.zzfru.zzk(bundle);
    }
}


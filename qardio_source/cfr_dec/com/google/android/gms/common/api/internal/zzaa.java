/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzab;
import com.google.android.gms.common.api.internal.zzac;
import com.google.android.gms.common.api.internal.zzad;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzcc;
import com.google.android.gms.common.api.internal.zzdj;
import com.google.android.gms.common.api.internal.zzdm;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.api.internal.zzt;
import com.google.android.gms.common.api.internal.zzz;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzbha;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.internal.zzcxe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zzaa
implements zzcc {
    private final Looper zzall;
    private final zzbm zzfmi;
    private final Lock zzfps;
    private final zzr zzfpx;
    private final Map<Api.zzc<?>, zzz<?>> zzfpy = new HashMap();
    private final Map<Api.zzc<?>, zzz<?>> zzfpz = new HashMap();
    private final Map<Api<?>, Boolean> zzfqa;
    private final zzba zzfqb;
    private final zzf zzfqc;
    private final Condition zzfqd;
    private final boolean zzfqe;
    private final boolean zzfqf;
    private final Queue<zzm<?, ?>> zzfqg = new LinkedList();
    private boolean zzfqh;
    private Map<zzh<?>, ConnectionResult> zzfqi;
    private Map<zzh<?>, ConnectionResult> zzfqj;
    private zzad zzfqk;
    private ConnectionResult zzfql;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public zzaa(Context context, Lock hashMap, Looper looper, zzf hashMap2, Map<Api.zzc<?>, Api.zze> iterator, zzr zzr2, Map<Api<?>, Boolean> object, Api.zza<? extends zzcxd, zzcxe> zza2, ArrayList<zzt> object2, zzba zzz2, boolean bl) {
        Object object3;
        Object object4;
        int n;
        boolean bl2;
        this.zzfps = hashMap;
        this.zzall = looper;
        this.zzfqd = hashMap.newCondition();
        this.zzfqc = hashMap2;
        this.zzfqb = object4;
        this.zzfqa = object;
        this.zzfpx = zzr2;
        this.zzfqe = bl2;
        hashMap = new HashMap();
        for (Api api : object.keySet()) {
            hashMap.put(api.zzagf(), api);
        }
        hashMap2 = new HashMap();
        ArrayList arrayList = (ArrayList)object3;
        int n2 = arrayList.size();
        for (n = 0; n < n2; ++n) {
            object3 = arrayList.get(n);
            object3 = (zzt)object3;
            hashMap2.put(((zzt)object3).zzfin, object3);
        }
        iterator = iterator.entrySet().iterator();
        n2 = 1;
        n = 0;
        boolean bl3 = false;
        while (iterator.hasNext()) {
            void var8_13;
            int n3;
            Map.Entry entry = iterator.next();
            object4 = (Api)hashMap.get(entry.getKey());
            object3 = (Api.zze)entry.getValue();
            if (object3.zzagg()) {
                bl3 = true;
                if (!this.zzfqa.get(object4).booleanValue()) {
                    n = n2;
                    n2 = 1;
                } else {
                    n3 = n;
                    n = n2;
                    n2 = n3;
                }
            } else {
                n3 = 0;
                n2 = n;
                n = n3;
            }
            object4 = new zzz(context, object4, looper, (Api.zze)object3, (zzt)hashMap2.get(object4), zzr2, (Api.zza<? extends zzcxd, zzcxe>)((Api.zza<zzcxd, zzcxe>)((Api.zza<? extends zzcxd, zzcxe>)var8_13)));
            this.zzfpy.put((Api.zzc)entry.getKey(), (zzz<?>)object4);
            if (object3.zzaay()) {
                this.zzfpz.put((Api.zzc)entry.getKey(), (zzz<?>)object4);
            }
            n3 = n2;
            n2 = n;
            n = n3;
        }
        bl2 = bl3 && n2 == 0 && n == 0;
        this.zzfqf = bl2;
        this.zzfmi = zzbm.zzaiq();
    }

    static /* synthetic */ ConnectionResult zza(zzaa zzaa2, ConnectionResult connectionResult) {
        zzaa2.zzfql = connectionResult;
        return connectionResult;
    }

    static /* synthetic */ Map zza(zzaa zzaa2, Map map) {
        zzaa2.zzfqi = map;
        return map;
    }

    static /* synthetic */ Lock zza(zzaa zzaa2) {
        return zzaa2.zzfps;
    }

    static /* synthetic */ boolean zza(zzaa zzaa2, zzz zzz2, ConnectionResult connectionResult) {
        return zzaa2.zza(zzz2, connectionResult);
    }

    static /* synthetic */ boolean zza(zzaa zzaa2, boolean bl) {
        zzaa2.zzfqh = false;
        return false;
    }

    private final boolean zza(zzz<?> zzz2, ConnectionResult connectionResult) {
        return !connectionResult.isSuccess() && !connectionResult.hasResolution() && this.zzfqa.get(zzz2.zzagl()) != false && zzz2.zzahp().zzagg() && this.zzfqc.isUserResolvableError(connectionResult.getErrorCode());
    }

    private final void zzahr() {
        if (this.zzfpx == null) {
            this.zzfqb.zzfsc = Collections.emptySet();
            return;
        }
        HashSet<Scope> hashSet = new HashSet<Scope>(this.zzfpx.zzakv());
        Map<Api<?>, com.google.android.gms.common.internal.zzt> map = this.zzfpx.zzakx();
        for (Api<?> api : map.keySet()) {
            ConnectionResult connectionResult = this.getConnectionResult(api);
            if (connectionResult == null || !connectionResult.isSuccess()) continue;
            hashSet.addAll(map.get(api).zzehs);
        }
        this.zzfqb.zzfsc = hashSet;
    }

    private final void zzahs() {
        while (!this.zzfqg.isEmpty()) {
            this.zze(this.zzfqg.remove());
        }
        this.zzfqb.zzj(null);
    }

    private final ConnectionResult zzaht() {
        Iterator<zzz<?>> iterator = this.zzfpy.values().iterator();
        int n = 0;
        Object object = null;
        int n2 = 0;
        Object object2 = null;
        while (iterator.hasNext()) {
            int n3;
            Object object3 = iterator.next();
            Api api = ((GoogleApi)object3).zzagl();
            object3 = ((GoogleApi)object3).zzagn();
            if (((ConnectionResult)(object3 = this.zzfqi.get(object3))).isSuccess() || this.zzfqa.get(api).booleanValue() && !((ConnectionResult)object3).hasResolution() && !this.zzfqc.isUserResolvableError(((ConnectionResult)object3).getErrorCode())) continue;
            if (((ConnectionResult)object3).getErrorCode() == 4 && this.zzfqe) {
                n3 = api.zzagd().getPriority();
                if (object != null && n <= n3) continue;
                n = n3;
                object = object3;
                continue;
            }
            n3 = api.zzagd().getPriority();
            if (object2 != null && n2 <= n3) continue;
            object2 = object3;
            n2 = n3;
        }
        if (object2 != null && object != null && n2 > n) {
            return object;
        }
        return object2;
    }

    private final ConnectionResult zzb(Api.zzc<?> object) {
        block4: {
            this.zzfps.lock();
            object = this.zzfpy.get(object);
            if (this.zzfqi == null || object == null) break block4;
            object = this.zzfqi.get(((GoogleApi)object).zzagn());
            return object;
        }
        this.zzfps.unlock();
        return null;
        finally {
            this.zzfps.unlock();
        }
    }

    static /* synthetic */ Map zzb(zzaa zzaa2, Map map) {
        zzaa2.zzfqj = map;
        return map;
    }

    static /* synthetic */ boolean zzb(zzaa zzaa2) {
        return zzaa2.zzfqh;
    }

    static /* synthetic */ Map zzc(zzaa zzaa2) {
        return zzaa2.zzfpy;
    }

    static /* synthetic */ Map zzd(zzaa zzaa2) {
        return zzaa2.zzfqi;
    }

    static /* synthetic */ boolean zze(zzaa zzaa2) {
        return zzaa2.zzfqf;
    }

    static /* synthetic */ ConnectionResult zzf(zzaa zzaa2) {
        return zzaa2.zzaht();
    }

    static /* synthetic */ Map zzg(zzaa zzaa2) {
        return zzaa2.zzfqj;
    }

    private final <T extends zzm<? extends Result, ? extends Api.zzb>> boolean zzg(T t) {
        Api.zzc<? extends Api.zzb> zzc2 = t.zzagf();
        ConnectionResult connectionResult = this.zzb(zzc2);
        if (connectionResult != null && connectionResult.getErrorCode() == 4) {
            t.zzu(new Status(4, null, this.zzfmi.zza(this.zzfpy.get(zzc2).zzagn(), System.identityHashCode(this.zzfqb))));
            return true;
        }
        return false;
    }

    static /* synthetic */ ConnectionResult zzh(zzaa zzaa2) {
        return zzaa2.zzfql;
    }

    static /* synthetic */ void zzi(zzaa zzaa2) {
        zzaa2.zzahr();
    }

    static /* synthetic */ void zzj(zzaa zzaa2) {
        zzaa2.zzahs();
    }

    static /* synthetic */ zzba zzk(zzaa zzaa2) {
        return zzaa2.zzfqb;
    }

    static /* synthetic */ Condition zzl(zzaa zzaa2) {
        return zzaa2.zzfqd;
    }

    static /* synthetic */ Map zzm(zzaa zzaa2) {
        return zzaa2.zzfpz;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final ConnectionResult blockingConnect(long var1_1, TimeUnit var3_2) {
        this.connect();
        var1_1 = var3_2.toNanos(var1_1);
        while (this.isConnecting()) {
            if (var1_1 > 0L) ** GOTO lbl8
            try {
                this.disconnect();
                return new ConnectionResult(14, null);
lbl8:
                // 1 sources
                var1_1 = this.zzfqd.awaitNanos(var1_1);
            }
            catch (InterruptedException var3_3) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (this.isConnected()) {
            return ConnectionResult.zzfkr;
        }
        if (this.zzfql == null) return new ConnectionResult(13, null);
        return this.zzfql;
    }

    @Override
    public final void connect() {
        block4: {
            this.zzfps.lock();
            boolean bl = this.zzfqh;
            if (!bl) break block4;
            this.zzfps.unlock();
            return;
        }
        try {
            this.zzfqh = true;
            this.zzfqi = null;
            this.zzfqj = null;
            this.zzfqk = null;
            this.zzfql = null;
            this.zzfmi.zzagz();
            this.zzfmi.zza(this.zzfpy.values()).addOnCompleteListener(new zzbha(this.zzall), new zzac(this, null));
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
            this.zzfqh = false;
            this.zzfqi = null;
            this.zzfqj = null;
            if (this.zzfqk != null) {
                this.zzfqk.cancel();
                this.zzfqk = null;
            }
            this.zzfql = null;
            while (!this.zzfqg.isEmpty()) {
                zzm<?, ?> zzm2 = this.zzfqg.remove();
                ((BasePendingResult)zzm2).zza((zzdm)null);
                ((PendingResult)zzm2).cancel();
            }
            this.zzfqd.signalAll();
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    @Override
    public final void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    public final ConnectionResult getConnectionResult(Api<?> api) {
        return this.zzb(api.zzagf());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final boolean isConnected() {
        this.zzfps.lock();
        try {
            ConnectionResult connectionResult;
            boolean bl = this.zzfqi != null && (connectionResult = this.zzfql) == null;
            return bl;
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
    public final boolean isConnecting() {
        this.zzfps.lock();
        try {
            boolean bl;
            bl = this.zzfqi == null && (bl = this.zzfqh);
            return bl;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    @Override
    public final void zzahk() {
    }

    @Override
    public final <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        if (this.zzfqe && this.zzg(t)) {
            return t;
        }
        if (!this.isConnected()) {
            this.zzfqg.add(t);
            return t;
        }
        this.zzfqb.zzfsh.zzb(t);
        return this.zzfpy.get(t.zzagf()).zza(t);
    }

    @Override
    public final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        Api.zzc<A> zzc2 = t.zzagf();
        if (this.zzfqe && this.zzg(t)) {
            return t;
        }
        this.zzfqb.zzfsh.zzb(t);
        return this.zzfpy.get(zzc2).zzb(t);
    }
}


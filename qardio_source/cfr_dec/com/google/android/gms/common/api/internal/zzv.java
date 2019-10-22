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
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzbi;
import com.google.android.gms.common.api.internal.zzcc;
import com.google.android.gms.common.api.internal.zzcd;
import com.google.android.gms.common.api.internal.zzcu;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.api.internal.zzt;
import com.google.android.gms.common.api.internal.zzw;
import com.google.android.gms.common.api.internal.zzx;
import com.google.android.gms.common.api.internal.zzy;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.internal.zzcxe;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

final class zzv
implements zzcc {
    private final Context mContext;
    private final Looper zzall;
    private final zzba zzfpi;
    private final zzbi zzfpj;
    private final zzbi zzfpk;
    private final Map<Api.zzc<?>, zzbi> zzfpl;
    private final Set<zzcu> zzfpm = Collections.newSetFromMap(new WeakHashMap());
    private final Api.zze zzfpn;
    private Bundle zzfpo;
    private ConnectionResult zzfpp = null;
    private ConnectionResult zzfpq = null;
    private boolean zzfpr = false;
    private final Lock zzfps;
    private int zzfpt = 0;

    private zzv(Context object, zzba iterator, Lock lock, Looper looper, zzf zzf2, Map<Api.zzc<?>, Api.zze> map, Map<Api.zzc<?>, Api.zze> map2, zzr zzr2, Api.zza<? extends zzcxd, zzcxe> zza2, Api.zze zze2, ArrayList<zzt> arrayList, ArrayList<zzt> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.mContext = object;
        this.zzfpi = iterator;
        this.zzfps = lock;
        this.zzall = looper;
        this.zzfpn = zze2;
        this.zzfpj = new zzbi((Context)object, this.zzfpi, lock, looper, zzf2, map2, null, map4, null, arrayList2, new zzx(this, null));
        this.zzfpk = new zzbi((Context)object, this.zzfpi, lock, looper, zzf2, map, zzr2, map3, zza2, arrayList, new zzy(this, null));
        object = new ArrayMap();
        iterator = map2.keySet().iterator();
        while (iterator.hasNext()) {
            ((SimpleArrayMap)object).put((Api.zzc)iterator.next(), this.zzfpj);
        }
        iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            ((SimpleArrayMap)object).put(iterator.next(), this.zzfpk);
        }
        this.zzfpl = Collections.unmodifiableMap(object);
    }

    static /* synthetic */ ConnectionResult zza(zzv zzv2, ConnectionResult connectionResult) {
        zzv2.zzfpp = connectionResult;
        return connectionResult;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static zzv zza(Context context, zzba zzba2, Lock lock, Looper looper, zzf zzf2, Map<Api.zzc<?>, Api.zze> object, zzr zzr2, Map<Api<?>, Boolean> arrayList, Api.zza<? extends zzcxd, zzcxe> zza2, ArrayList<zzt> arrayList2) {
        Object object2;
        Object object3 = null;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        Object object4 = object.entrySet().iterator();
        object = object3;
        while (object4.hasNext()) {
            object2 = object4.next();
            object3 = (Api.zze)object2.getValue();
            if (object3.zzabj()) {
                object = object3;
            }
            if (object3.zzaay()) {
                arrayMap.put((Api.zzc)object2.getKey(), (Api.zze)object3);
                continue;
            }
            arrayMap2.put((Api.zzc)object2.getKey(), (Api.zze)object3);
        }
        boolean bl = !arrayMap.isEmpty();
        zzbq.zza(bl, "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        object3 = new ArrayMap();
        object4 = new ArrayMap();
        for (Api api : arrayList.keySet()) {
            Api.zzc<?> zzc2 = api.zzagf();
            if (arrayMap.containsKey(zzc2)) {
                object3.put(api, (Boolean)arrayList.get(api));
                continue;
            }
            if (!arrayMap2.containsKey(zzc2)) {
                throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }
            object4.put(api, (Boolean)arrayList.get(api));
        }
        arrayList = new ArrayList();
        object2 = new ArrayList<zzt>();
        int n = arrayList2.size();
        int n2 = 0;
        while (n2 < n) {
            zzt zzt2 = arrayList2.get(n2);
            ++n2;
            zzt zzt3 = zzt2;
            if (object3.containsKey(zzt3.zzfin)) {
                arrayList.add(zzt3);
                continue;
            }
            if (!object4.containsKey(zzt3.zzfin)) {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }
            ((ArrayList)object2).add((zzt)zzt3);
        }
        return new zzv(context, zzba2, lock, looper, zzf2, arrayMap, arrayMap2, zzr2, zza2, (Api.zze)object, arrayList, (ArrayList<zzt>)object2, (Map<Api<?>, Boolean>)object3, (Map<Api<?>, Boolean>)object4);
    }

    static /* synthetic */ Lock zza(zzv zzv2) {
        return zzv2.zzfps;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(ConnectionResult connectionResult) {
        block4: {
            switch (this.zzfpt) {
                default: {
                    Log.wtf((String)"CompositeGAC", (String)"Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", (Throwable)new Exception());
                    break block4;
                }
                case 2: {
                    this.zzfpi.zzc(connectionResult);
                    break;
                }
                case 1: 
            }
            this.zzahm();
        }
        this.zzfpt = 0;
    }

    static /* synthetic */ void zza(zzv zzv2, int n, boolean bl) {
        zzv2.zze(n, bl);
    }

    static /* synthetic */ void zza(zzv zzv2, Bundle bundle) {
        zzv2.zzi(bundle);
    }

    static /* synthetic */ boolean zza(zzv zzv2, boolean bl) {
        zzv2.zzfpr = bl;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzahl() {
        block9: {
            block10: {
                block11: {
                    if (!zzv.zzb(this.zzfpp)) break block9;
                    if (!zzv.zzb(this.zzfpq) && !this.zzahn()) break block10;
                    switch (this.zzfpt) {
                        default: {
                            Log.wtf((String)"CompositeGAC", (String)"Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", (Throwable)((Object)new AssertionError()));
                            break block11;
                        }
                        case 2: {
                            this.zzfpi.zzj(this.zzfpo);
                            break;
                        }
                        case 1: 
                    }
                    this.zzahm();
                }
                this.zzfpt = 0;
                return;
            }
            if (this.zzfpq == null) return;
            {
                if (this.zzfpt == 1) {
                    this.zzahm();
                    return;
                }
                this.zza(this.zzfpq);
                this.zzfpj.disconnect();
                return;
            }
        }
        if (this.zzfpp != null && zzv.zzb(this.zzfpq)) {
            this.zzfpk.disconnect();
            this.zza(this.zzfpp);
            return;
        }
        if (this.zzfpp == null || this.zzfpq == null) return;
        {
            ConnectionResult connectionResult = this.zzfpp;
            if (this.zzfpk.zzfst < this.zzfpj.zzfst) {
                connectionResult = this.zzfpq;
            }
            this.zza(connectionResult);
            return;
        }
    }

    private final void zzahm() {
        Iterator<zzcu> iterator = this.zzfpm.iterator();
        while (iterator.hasNext()) {
            iterator.next().zzabi();
        }
        this.zzfpm.clear();
    }

    private final boolean zzahn() {
        return this.zzfpq != null && this.zzfpq.getErrorCode() == 4;
    }

    private final PendingIntent zzaho() {
        if (this.zzfpn == null) {
            return null;
        }
        return PendingIntent.getActivity((Context)this.mContext, (int)System.identityHashCode(this.zzfpi), (Intent)this.zzfpn.getSignInIntent(), (int)134217728);
    }

    static /* synthetic */ ConnectionResult zzb(zzv zzv2, ConnectionResult connectionResult) {
        zzv2.zzfpq = connectionResult;
        return connectionResult;
    }

    static /* synthetic */ void zzb(zzv zzv2) {
        zzv2.zzahl();
    }

    private static boolean zzb(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    static /* synthetic */ boolean zzc(zzv zzv2) {
        return zzv2.zzfpr;
    }

    static /* synthetic */ ConnectionResult zzd(zzv zzv2) {
        return zzv2.zzfpq;
    }

    static /* synthetic */ zzbi zze(zzv zzv2) {
        return zzv2.zzfpk;
    }

    private final void zze(int n, boolean bl) {
        this.zzfpi.zzf(n, bl);
        this.zzfpq = null;
        this.zzfpp = null;
    }

    static /* synthetic */ zzbi zzf(zzv zzv2) {
        return zzv2.zzfpj;
    }

    private final boolean zzf(zzm<? extends Result, ? extends Api.zzb> object) {
        object = ((zzm)object).zzagf();
        zzbq.checkArgument(this.zzfpl.containsKey(object), "GoogleApiClient is not configured to use the API required for this call.");
        return this.zzfpl.get(object).equals(this.zzfpk);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzi(Bundle bundle) {
        if (this.zzfpo == null) {
            this.zzfpo = bundle;
            return;
        } else {
            if (bundle == null) return;
            {
                this.zzfpo.putAll(bundle);
                return;
            }
        }
    }

    @Override
    public final ConnectionResult blockingConnect(long l, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void connect() {
        this.zzfpt = 2;
        this.zzfpr = false;
        this.zzfpq = null;
        this.zzfpp = null;
        this.zzfpj.connect();
        this.zzfpk.connect();
    }

    @Override
    public final void disconnect() {
        this.zzfpq = null;
        this.zzfpp = null;
        this.zzfpt = 0;
        this.zzfpj.disconnect();
        this.zzfpk.disconnect();
        this.zzahm();
    }

    @Override
    public final void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.append(string2).append("authClient").println(":");
        this.zzfpk.dump(String.valueOf(string2).concat("  "), fileDescriptor, printWriter, arrstring);
        printWriter.append(string2).append("anonClient").println(":");
        this.zzfpj.dump(String.valueOf(string2).concat("  "), fileDescriptor, printWriter, arrstring);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final boolean isConnected() {
        var3_1 = true;
        this.zzfps.lock();
        try {
            if (!this.zzfpj.isConnected()) ** GOTO lbl-1000
            var2_2 = var3_1;
            if (this.zzfpk.isConnected() != false) return var2_2;
            var2_2 = var3_1;
            if (this.zzahn() != false) return var2_2;
            var1_3 = this.zzfpt;
            if (var1_3 == 1) {
                var2_2 = var3_1;
                return var2_2;
            } else lbl-1000:
            // 2 sources
            {
                var2_2 = false;
            }
            return var2_2;
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
            int n = this.zzfpt;
            boolean bl = n == 2;
            return bl;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    @Override
    public final void zzahk() {
        this.zzfpj.zzahk();
        this.zzfpk.zzahk();
    }

    @Override
    public final <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        if (this.zzf(t)) {
            if (this.zzahn()) {
                t.zzu(new Status(4, null, this.zzaho()));
                return t;
            }
            return this.zzfpk.zzd(t);
        }
        return this.zzfpj.zzd(t);
    }

    @Override
    public final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        if (this.zzf(t)) {
            if (this.zzahn()) {
                t.zzu(new Status(4, null, this.zzaho()));
                return t;
            }
            return this.zzfpk.zze(t);
        }
        return this.zzfpj.zze(t);
    }
}


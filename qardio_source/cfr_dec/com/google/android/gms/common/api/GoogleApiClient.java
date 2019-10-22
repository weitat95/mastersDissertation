/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 *  android.view.View
 */
package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzce;
import com.google.android.gms.common.api.internal.zzdg;
import com.google.android.gms.common.api.internal.zzi;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.api.internal.zzt;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzcxa;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.internal.zzcxe;
import com.google.android.gms.internal.zzcxn;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GoogleApiClient {
    private static final Set<GoogleApiClient> zzfmn = Collections.newSetFromMap(new WeakHashMap());

    public abstract ConnectionResult blockingConnect(long var1, TimeUnit var3);

    public abstract void connect();

    public void connect(int n) {
        throw new UnsupportedOperationException();
    }

    public abstract void disconnect();

    public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    public Context getContext() {
        throw new UnsupportedOperationException();
    }

    public Looper getLooper() {
        throw new UnsupportedOperationException();
    }

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract void reconnect();

    public abstract void registerConnectionCallbacks(ConnectionCallbacks var1);

    public abstract void registerConnectionFailedListener(OnConnectionFailedListener var1);

    public abstract void unregisterConnectionFailedListener(OnConnectionFailedListener var1);

    public void zza(zzdg zzdg2) {
        throw new UnsupportedOperationException();
    }

    public void zzb(zzdg zzdg2) {
        throw new UnsupportedOperationException();
    }

    public <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        throw new UnsupportedOperationException();
    }

    public <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        throw new UnsupportedOperationException();
    }

    public static final class Builder {
        private final Context mContext;
        private Looper zzall;
        private String zzebs;
        private Account zzebz;
        private final Set<Scope> zzfmo = new HashSet<Scope>();
        private final Set<Scope> zzfmp = new HashSet<Scope>();
        private int zzfmq;
        private View zzfmr;
        private String zzfms;
        private final Map<Api<?>, com.google.android.gms.common.internal.zzt> zzfmt = new ArrayMap();
        private final Map<Api<?>, Api.ApiOptions> zzfmu = new ArrayMap();
        private zzce zzfmv;
        private int zzfmw = -1;
        private OnConnectionFailedListener zzfmx;
        private GoogleApiAvailability zzfmy = GoogleApiAvailability.getInstance();
        private Api.zza<? extends zzcxd, zzcxe> zzfmz = zzcxa.zzebg;
        private final ArrayList<ConnectionCallbacks> zzfna = new ArrayList();
        private final ArrayList<OnConnectionFailedListener> zzfnb = new ArrayList();
        private boolean zzfnc = false;

        public Builder(Context context) {
            this.mContext = context;
            this.zzall = context.getMainLooper();
            this.zzebs = context.getPackageName();
            this.zzfms = context.getClass().getName();
        }

        public final Builder addApi(Api<? extends Api.ApiOptions.NotRequiredOptions> object) {
            zzbq.checkNotNull(object, "Api must not be null");
            this.zzfmu.put((Api<?>)object, (Api.ApiOptions)null);
            object = ((Api)object).zzagd().zzr(null);
            this.zzfmp.addAll((Collection<Scope>)object);
            this.zzfmo.addAll((Collection<Scope>)object);
            return this;
        }

        public final <O extends Api.ApiOptions.HasOptions> Builder addApi(Api<O> object, O o) {
            zzbq.checkNotNull(object, "Api must not be null");
            zzbq.checkNotNull(o, "Null options are not permitted for this Api");
            this.zzfmu.put((Api<?>)object, o);
            object = ((Api)object).zzagd().zzr(o);
            this.zzfmp.addAll((Collection<Scope>)object);
            this.zzfmo.addAll((Collection<Scope>)object);
            return this;
        }

        public final Builder addConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
            zzbq.checkNotNull(connectionCallbacks, "Listener must not be null");
            this.zzfna.add(connectionCallbacks);
            return this;
        }

        public final Builder addOnConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
            zzbq.checkNotNull(onConnectionFailedListener, "Listener must not be null");
            this.zzfnb.add(onConnectionFailedListener);
            return this;
        }

        public final Builder addScope(Scope scope) {
            zzbq.checkNotNull(scope, "Scope must not be null");
            this.zzfmo.add(scope);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public final GoogleApiClient build() {
            Object object;
            boolean bl = !this.zzfmu.isEmpty();
            zzbq.checkArgument(bl, "must call addApi() to add at least one API");
            zzr zzr2 = this.zzagu();
            Object object2 = null;
            Map<Api<?>, com.google.android.gms.common.internal.zzt> map = zzr2.zzakx();
            ArrayMap arrayMap = new ArrayMap();
            ArrayMap arrayMap2 = new ArrayMap();
            ArrayList<zzt> arrayList = new ArrayList<zzt>();
            Iterator<Api<?>> iterator = this.zzfmu.keySet().iterator();
            int n = 0;
            while (iterator.hasNext()) {
                Api<?> api = iterator.next();
                object = this.zzfmu.get(api);
                bl = map.get(api) != null;
                arrayMap.put(api, bl);
                zzt zzt2 = new zzt(api, bl);
                arrayList.add(zzt2);
                Api.zza<?, ?> zza2 = api.zzage();
                zzt2 = zza2.zza(this.mContext, this.zzall, zzr2, object, zzt2, zzt2);
                arrayMap2.put(api.zzagf(), (Api.zze)((Object)zzt2));
                if (zza2.getPriority() == 1) {
                    n = object != null ? 1 : 0;
                }
                if (zzt2.zzabj()) {
                    object = api;
                    if (object2 != null) {
                        object = api.getName();
                        object2 = ((Api)object2).getName();
                        throw new IllegalStateException(new StringBuilder(String.valueOf(object).length() + 21 + String.valueOf(object2).length()).append((String)object).append(" cannot be used with ").append((String)object2).toString());
                    }
                } else {
                    object = object2;
                }
                object2 = object;
            }
            if (object2 != null) {
                if (n != 0) {
                    object2 = ((Api)object2).getName();
                    throw new IllegalStateException(new StringBuilder(String.valueOf(object2).length() + 82).append("With using ").append((String)object2).append(", GamesOptions can only be specified within GoogleSignInOptions.Builder").toString());
                }
                bl = this.zzebz == null;
                zzbq.zza(bl, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", ((Api)object2).getName());
                zzbq.zza(this.zzfmo.equals(this.zzfmp), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", ((Api)object2).getName());
            }
            n = zzba.zza(arrayMap2.values(), true);
            object = new zzba(this.mContext, new ReentrantLock(), this.zzall, zzr2, this.zzfmy, this.zzfmz, arrayMap, this.zzfna, this.zzfnb, arrayMap2, this.zzfmw, n, arrayList, false);
            object2 = zzfmn;
            // MONITORENTER : object2
            zzfmn.add(object);
            // MONITOREXIT : object2
            if (this.zzfmw < 0) return object;
            zzi.zza(this.zzfmv).zza(this.zzfmw, (GoogleApiClient)object, this.zzfmx);
            return object;
        }

        public final zzr zzagu() {
            zzcxe zzcxe2 = zzcxe.zzkbs;
            if (this.zzfmu.containsKey(zzcxa.API)) {
                zzcxe2 = (zzcxe)this.zzfmu.get(zzcxa.API);
            }
            return new zzr(this.zzebz, this.zzfmo, this.zzfmt, this.zzfmq, this.zzfmr, this.zzebs, this.zzfms, zzcxe2);
        }
    }

    public static interface ConnectionCallbacks {
        public void onConnected(Bundle var1);

        public void onConnectionSuspended(int var1);
    }

    public static interface OnConnectionFailedListener {
        public void onConnectionFailed(ConnectionResult var1);
    }

}


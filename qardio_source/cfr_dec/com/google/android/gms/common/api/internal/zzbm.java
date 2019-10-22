/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.ArraySet;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzah;
import com.google.android.gms.common.api.internal.zzbn;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.api.internal.zzc;
import com.google.android.gms.common.api.internal.zzcp;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.api.internal.zzj;
import com.google.android.gms.common.api.internal.zzk;
import com.google.android.gms.common.api.internal.zzl;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.tasks.Task;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzbm
implements Handler.Callback {
    private static final Object sLock;
    public static final Status zzfsy;
    private static final Status zzfsz;
    private static zzbm zzftb;
    private final Context mContext;
    private final Handler mHandler;
    private final GoogleApiAvailability zzfmy;
    private final Map<zzh<?>, zzbo<?>> zzfpy;
    private long zzfrx = 120000L;
    private long zzfry = 5000L;
    private long zzfta = 10000L;
    private int zzftc = -1;
    private final AtomicInteger zzftd = new AtomicInteger(1);
    private final AtomicInteger zzfte = new AtomicInteger(0);
    private zzah zzftf = null;
    private final Set<zzh<?>> zzftg;
    private final Set<zzh<?>> zzfth;

    static {
        zzfsy = new Status(4, "Sign-out occurred while this API call was in progress.");
        zzfsz = new Status(4, "The user must be signed in to make this API call.");
        sLock = new Object();
    }

    private zzbm(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.zzfpy = new ConcurrentHashMap(5, 0.75f, 1);
        this.zzftg = new ArraySet();
        this.zzfth = new ArraySet();
        this.mContext = context;
        this.mHandler = new Handler(looper, (Handler.Callback)this);
        this.zzfmy = googleApiAvailability;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6));
    }

    static /* synthetic */ int zza(zzbm zzbm2, int n) {
        zzbm2.zzftc = n;
        return n;
    }

    static /* synthetic */ Handler zza(zzbm zzbm2) {
        return zzbm2.mHandler;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzbm zzaiq() {
        Object object = sLock;
        synchronized (object) {
            zzbq.checkNotNull(zzftb, "Must guarantee manager is non-null before using getInstance");
            return zzftb;
        }
    }

    private final void zzait() {
        for (zzh<?> zzh2 : this.zzfth) {
            this.zzfpy.remove(zzh2).signOut();
        }
        this.zzfth.clear();
    }

    static /* synthetic */ Status zzaiu() {
        return zzfsz;
    }

    static /* synthetic */ Object zzaiv() {
        return sLock;
    }

    static /* synthetic */ Context zzb(zzbm zzbm2) {
        return zzbm2.mContext;
    }

    private final void zzb(GoogleApi<?> googleApi) {
        zzbo zzbo2;
        zzh<?> zzh2 = googleApi.zzagn();
        zzbo zzbo3 = zzbo2 = this.zzfpy.get(zzh2);
        if (zzbo2 == null) {
            zzbo3 = new zzbo(this, googleApi);
            this.zzfpy.put(zzh2, zzbo3);
        }
        if (zzbo3.zzaay()) {
            this.zzfth.add(zzh2);
        }
        zzbo3.connect();
    }

    static /* synthetic */ long zzc(zzbm zzbm2) {
        return zzbm2.zzfry;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzbm zzcj(Context object) {
        Object object2 = sLock;
        synchronized (object2) {
            if (zzftb != null) return zzftb;
            HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
            handlerThread.start();
            handlerThread = handlerThread.getLooper();
            zzftb = new zzbm(object.getApplicationContext(), (Looper)handlerThread, GoogleApiAvailability.getInstance());
            return zzftb;
        }
    }

    static /* synthetic */ long zzd(zzbm zzbm2) {
        return zzbm2.zzfrx;
    }

    static /* synthetic */ zzah zze(zzbm zzbm2) {
        return zzbm2.zzftf;
    }

    static /* synthetic */ Set zzf(zzbm zzbm2) {
        return zzbm2.zzftg;
    }

    static /* synthetic */ GoogleApiAvailability zzg(zzbm zzbm2) {
        return zzbm2.zzfmy;
    }

    static /* synthetic */ long zzh(zzbm zzbm2) {
        return zzbm2.zzfta;
    }

    static /* synthetic */ int zzi(zzbm zzbm2) {
        return zzbm2.zzftc;
    }

    static /* synthetic */ Map zzj(zzbm zzbm2) {
        return zzbm2.zzfpy;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean handleMessage(Message object2) {
        int n;
        ConnectionResult connectionResult;
        block23: {
            switch (((Message)object2).what) {
                default: {
                    int n2 = ((Message)object2).what;
                    Log.w((String)"GoogleApiManager", (String)new StringBuilder(31).append("Unknown message id: ").append(n2).toString());
                    return false;
                }
                case 1: {
                    long l = (Boolean)((Message)object2).obj != false ? 10000L : 300000L;
                    this.zzfta = l;
                    this.mHandler.removeMessages(12);
                    object2 = this.zzfpy.keySet().iterator();
                    while (object2.hasNext()) {
                        zzh zzh2 = (zzh)object2.next();
                        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(12, (Object)zzh2), this.zzfta);
                    }
                    return true;
                }
                case 2: {
                    object2 = (zzj)((Message)object2).obj;
                    Iterator<zzh<?>> iterator = ((zzj)object2).zzaha().iterator();
                    while (iterator.hasNext()) {
                        zzh<?> zzh3 = iterator.next();
                        zzbo<?> zzbo2 = this.zzfpy.get(zzh3);
                        if (zzbo2 == null) {
                            ((zzj)object2).zza(zzh3, new ConnectionResult(13), null);
                            return true;
                        }
                        if (zzbo2.isConnected()) {
                            ((zzj)object2).zza(zzh3, ConnectionResult.zzfkr, zzbo2.zzahp().zzagi());
                            continue;
                        }
                        if (zzbo2.zzaja() != null) {
                            ((zzj)object2).zza(zzh3, zzbo2.zzaja(), null);
                            continue;
                        }
                        zzbo2.zza((zzj)object2);
                    }
                    return true;
                }
                case 3: {
                    object2 = this.zzfpy.values().iterator();
                    while (object2.hasNext()) {
                        zzbo zzbo3 = (zzbo)object2.next();
                        zzbo3.zzaiz();
                        zzbo3.connect();
                    }
                    return true;
                }
                case 4: 
                case 8: 
                case 13: {
                    zzcp zzcp2 = (zzcp)((Message)object2).obj;
                    zzbo<?> zzbo4 = this.zzfpy.get(zzcp2.zzfur.zzagn());
                    object2 = zzbo4;
                    if (zzbo4 == null) {
                        this.zzb(zzcp2.zzfur);
                        object2 = this.zzfpy.get(zzcp2.zzfur.zzagn());
                    }
                    if (((zzbo)object2).zzaay() && this.zzfte.get() != zzcp2.zzfuq) {
                        zzcp2.zzfup.zzs(zzfsy);
                        ((zzbo)object2).signOut();
                        return true;
                    }
                    ((zzbo)object2).zza(zzcp2.zzfup);
                    return true;
                }
                case 5: {
                    n = ((Message)object2).arg1;
                    connectionResult = (ConnectionResult)((Message)object2).obj;
                    for (Object object2 : this.zzfpy.values()) {
                        if (((zzbo)object2).getInstanceId() != n) continue;
                        break block23;
                    }
                    break;
                }
                case 6: {
                    if (!(this.mContext.getApplicationContext() instanceof Application)) return true;
                    zzk.zza((Application)this.mContext.getApplicationContext());
                    zzk.zzahb().zza(new zzbn(this));
                    if (zzk.zzahb().zzbe(true)) return true;
                    this.zzfta = 300000L;
                    return true;
                }
                case 7: {
                    this.zzb((GoogleApi)((Message)object2).obj);
                    return true;
                }
                case 9: {
                    if (!this.zzfpy.containsKey(((Message)object2).obj)) return true;
                    this.zzfpy.get(((Message)object2).obj).resume();
                    return true;
                }
                case 10: {
                    this.zzait();
                    return true;
                }
                case 11: {
                    if (!this.zzfpy.containsKey(((Message)object2).obj)) return true;
                    this.zzfpy.get(((Message)object2).obj).zzaij();
                    return true;
                }
                case 12: {
                    if (!this.zzfpy.containsKey(((Message)object2).obj)) return true;
                    this.zzfpy.get(((Message)object2).obj).zzajd();
                    return true;
                }
            }
            object2 = null;
        }
        if (object2 != null) {
            String string2 = ((zzf)this.zzfmy).getErrorString(connectionResult.getErrorCode());
            String string3 = connectionResult.getErrorMessage();
            ((zzbo)object2).zzw(new Status(17, new StringBuilder(String.valueOf(string2).length() + 69 + String.valueOf(string3).length()).append("Error resolution was canceled by the user, original error message: ").append(string2).append(": ").append(string3).toString()));
            return true;
        }
        Log.wtf((String)"GoogleApiManager", (String)new StringBuilder(76).append("Could not find API instance ").append(n).append(" while trying to fail enqueued calls.").toString(), (Throwable)new Exception());
        return true;
    }

    final PendingIntent zza(zzh<?> object, int n) {
        if ((object = this.zzfpy.get(object)) == null) {
            return null;
        }
        if ((object = ((zzbo)object).zzaje()) == null) {
            return null;
        }
        return PendingIntent.getActivity((Context)this.mContext, (int)n, (Intent)object.getSignInIntent(), (int)134217728);
    }

    public final Task<Map<zzh<?>, String>> zza(Iterable<? extends GoogleApi<?>> object) {
        zzj zzj2 = new zzj((Iterable<? extends GoogleApi<?>>)object);
        object = object.iterator();
        while (object.hasNext()) {
            GoogleApi googleApi = (GoogleApi)object.next();
            zzbo<?> zzbo2 = this.zzfpy.get(googleApi.zzagn());
            if (zzbo2 == null || !zzbo2.isConnected()) {
                this.mHandler.sendMessage(this.mHandler.obtainMessage(2, (Object)zzj2));
                return zzj2.getTask();
            }
            zzj2.zza(googleApi.zzagn(), ConnectionResult.zzfkr, zzbo2.zzahp().zzagi());
        }
        return zzj2.getTask();
    }

    public final void zza(ConnectionResult connectionResult, int n) {
        if (!this.zzc(connectionResult, n)) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(5, n, 0, (Object)connectionResult));
        }
    }

    public final void zza(GoogleApi<?> googleApi) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, googleApi));
    }

    public final <O extends Api.ApiOptions> void zza(GoogleApi<O> googleApi, int n, zzm<? extends Result, Api.zzb> object) {
        object = new zzc<zzm<? extends Result, Api.zzb>>(n, (zzm<? extends Result, Api.zzb>)object);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, (Object)new zzcp((zza)object, this.zzfte.get(), googleApi)));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zza(zzah zzah2) {
        Object object = sLock;
        synchronized (object) {
            if (this.zzftf != zzah2) {
                this.zzftf = zzah2;
                this.zzftg.clear();
                this.zzftg.addAll(zzah2.zzahx());
            }
            return;
        }
    }

    public final void zzagz() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
    }

    public final int zzais() {
        return this.zzftd.getAndIncrement();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zzb(zzah zzah2) {
        Object object = sLock;
        synchronized (object) {
            if (this.zzftf == zzah2) {
                this.zzftf = null;
                this.zzftg.clear();
            }
            return;
        }
    }

    final boolean zzc(ConnectionResult connectionResult, int n) {
        return this.zzfmy.zza(this.mContext, connectionResult, n);
    }
}


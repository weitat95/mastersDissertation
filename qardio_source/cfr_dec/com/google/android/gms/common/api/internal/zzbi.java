/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzal;
import com.google.android.gms.common.api.internal.zzao;
import com.google.android.gms.common.api.internal.zzaz;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzbh;
import com.google.android.gms.common.api.internal.zzbj;
import com.google.android.gms.common.api.internal.zzbk;
import com.google.android.gms.common.api.internal.zzcc;
import com.google.android.gms.common.api.internal.zzcd;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.api.internal.zzt;
import com.google.android.gms.common.api.internal.zzu;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.internal.zzcxe;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zzbi
implements zzcc,
zzu {
    private final Context mContext;
    private Api.zza<? extends zzcxd, zzcxe> zzfmz;
    final zzba zzfpi;
    private final Lock zzfps;
    private zzr zzfpx;
    private Map<Api<?>, Boolean> zzfqa;
    private final zzf zzfqc;
    final Map<Api.zzc<?>, Api.zze> zzfsb;
    private final Condition zzfso;
    private final zzbk zzfsp;
    final Map<Api.zzc<?>, ConnectionResult> zzfsq = new HashMap();
    private volatile zzbh zzfsr;
    private ConnectionResult zzfss = null;
    int zzfst;
    final zzcd zzfsu;

    public zzbi(Context object, zzba zzba2, Lock lock, Looper looper, zzf zzf2, Map<Api.zzc<?>, Api.zze> map, zzr zzr2, Map<Api<?>, Boolean> map2, Api.zza<? extends zzcxd, zzcxe> zza2, ArrayList<zzt> arrayList, zzcd zzcd2) {
        this.mContext = object;
        this.zzfps = lock;
        this.zzfqc = zzf2;
        this.zzfsb = map;
        this.zzfpx = zzr2;
        this.zzfqa = map2;
        this.zzfmz = zza2;
        this.zzfpi = zzba2;
        this.zzfsu = zzcd2;
        object = arrayList;
        int n = ((ArrayList)object).size();
        for (int i = 0; i < n; ++i) {
            zzba2 = ((ArrayList)object).get(i);
            ((zzt)((Object)zzba2)).zza(this);
        }
        this.zzfsp = new zzbk(this, looper);
        this.zzfso = lock.newCondition();
        this.zzfsr = new zzaz(this);
    }

    static /* synthetic */ Lock zza(zzbi zzbi2) {
        return zzbi2.zzfps;
    }

    static /* synthetic */ zzbh zzb(zzbi zzbi2) {
        return zzbi2.zzfsr;
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
                var1_1 = this.zzfso.awaitNanos(var1_1);
            }
            catch (InterruptedException var3_3) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (this.isConnected()) {
            return ConnectionResult.zzfkr;
        }
        if (this.zzfss == null) return new ConnectionResult(13, null);
        return this.zzfss;
    }

    @Override
    public final void connect() {
        this.zzfsr.connect();
    }

    @Override
    public final void disconnect() {
        if (this.zzfsr.disconnect()) {
            this.zzfsq.clear();
        }
    }

    @Override
    public final void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        String string3 = String.valueOf(string2).concat("  ");
        printWriter.append(string2).append("mState=").println(this.zzfsr);
        for (Api<?> api : this.zzfqa.keySet()) {
            printWriter.append(string2).append(api.getName()).println(":");
            this.zzfsb.get(api.zzagf()).dump(string3, fileDescriptor, printWriter, arrstring);
        }
    }

    @Override
    public final boolean isConnected() {
        return this.zzfsr instanceof zzal;
    }

    @Override
    public final boolean isConnecting() {
        return this.zzfsr instanceof zzao;
    }

    @Override
    public final void onConnected(Bundle bundle) {
        this.zzfps.lock();
        try {
            this.zzfsr.onConnected(bundle);
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    @Override
    public final void onConnectionSuspended(int n) {
        this.zzfps.lock();
        try {
            this.zzfsr.onConnectionSuspended(n);
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    @Override
    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean bl) {
        this.zzfps.lock();
        try {
            this.zzfsr.zza(connectionResult, api, bl);
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    final void zza(zzbj zzbj2) {
        zzbj2 = this.zzfsp.obtainMessage(1, (Object)zzbj2);
        this.zzfsp.sendMessage((Message)zzbj2);
    }

    final void zza(RuntimeException runtimeException) {
        runtimeException = this.zzfsp.obtainMessage(2, (Object)runtimeException);
        this.zzfsp.sendMessage((Message)runtimeException);
    }

    @Override
    public final void zzahk() {
        if (this.isConnected()) {
            ((zzal)this.zzfsr).zzaia();
        }
    }

    final void zzain() {
        this.zzfps.lock();
        try {
            this.zzfsr = new zzao(this, this.zzfpx, this.zzfqa, this.zzfqc, this.zzfmz, this.zzfps, this.mContext);
            this.zzfsr.begin();
            this.zzfso.signalAll();
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    final void zzaio() {
        this.zzfps.lock();
        try {
            this.zzfpi.zzaik();
            this.zzfsr = new zzal(this);
            this.zzfsr.begin();
            this.zzfso.signalAll();
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }

    @Override
    public final <A extends Api.zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        t.zzahi();
        return this.zzfsr.zzd(t);
    }

    @Override
    public final <A extends Api.zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        t.zzahi();
        return this.zzfsr.zze(t);
    }

    final void zzg(ConnectionResult connectionResult) {
        this.zzfps.lock();
        try {
            this.zzfss = connectionResult;
            this.zzfsr = new zzaz(this);
            this.zzfsr.begin();
            this.zzfso.signalAll();
            return;
        }
        finally {
            this.zzfps.unlock();
        }
    }
}


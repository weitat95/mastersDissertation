/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcij;
import com.google.android.gms.internal.zzcik;
import com.google.android.gms.internal.zzcil;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclq;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class zzcih
extends zzcjl {
    private static final AtomicLong zzjeo = new AtomicLong(Long.MIN_VALUE);
    private ExecutorService zzieo;
    private zzcil zzjef;
    private zzcil zzjeg;
    private final PriorityBlockingQueue<zzcik<?>> zzjeh;
    private final BlockingQueue<zzcik<?>> zzjei;
    private final Thread.UncaughtExceptionHandler zzjej;
    private final Thread.UncaughtExceptionHandler zzjek;
    private final Object zzjel = new Object();
    private final Semaphore zzjem = new Semaphore(2);
    private volatile boolean zzjen;

    zzcih(zzcim zzcim2) {
        super(zzcim2);
        this.zzjeh = new PriorityBlockingQueue();
        this.zzjei = new LinkedBlockingQueue();
        this.zzjej = new zzcij(this, "Thread death: Uncaught exception on worker thread");
        this.zzjek = new zzcij(this, "Thread death: Uncaught exception on network thread");
    }

    static /* synthetic */ zzcil zza(zzcih zzcih2, zzcil zzcil2) {
        zzcih2.zzjef = null;
        return null;
    }

    static /* synthetic */ Semaphore zza(zzcih zzcih2) {
        return zzcih2.zzjem;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zza(zzcik<?> zzcik2) {
        Object object = this.zzjel;
        synchronized (object) {
            this.zzjeh.add(zzcik2);
            if (this.zzjef == null) {
                this.zzjef = new zzcil(this, "Measurement Worker", this.zzjeh);
                this.zzjef.setUncaughtExceptionHandler(this.zzjej);
                this.zzjef.start();
            } else {
                this.zzjef.zzrk();
            }
            return;
        }
    }

    public static boolean zzau() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    static /* synthetic */ AtomicLong zzazu() {
        return zzjeo;
    }

    static /* synthetic */ zzcil zzb(zzcih zzcih2, zzcil zzcil2) {
        zzcih2.zzjeg = null;
        return null;
    }

    static /* synthetic */ boolean zzb(zzcih zzcih2) {
        return zzcih2.zzjen;
    }

    static /* synthetic */ Object zzc(zzcih zzcih2) {
        return zzcih2.zzjel;
    }

    static /* synthetic */ zzcil zzd(zzcih zzcih2) {
        return zzcih2.zzjef;
    }

    static /* synthetic */ zzcil zze(zzcih zzcih2) {
        return zzcih2.zzjeg;
    }

    @Override
    public final void zzawj() {
        if (Thread.currentThread() != this.zzjeg) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    public final boolean zzazs() {
        return Thread.currentThread() == this.zzjef;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final ExecutorService zzazt() {
        Object object = this.zzjel;
        synchronized (object) {
            if (this.zzieo != null) return this.zzieo;
            this.zzieo = new ThreadPoolExecutor(0, 1, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
            return this.zzieo;
        }
    }

    public final <V> Future<V> zzc(Callable<V> object) throws IllegalStateException {
        this.zzxf();
        zzbq.checkNotNull(object);
        object = new zzcik(this, object, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzjef) {
            if (!this.zzjeh.isEmpty()) {
                ((zzcjk)this).zzawy().zzazf().log("Callable skipped the worker queue.");
            }
            ((FutureTask)object).run();
            return object;
        }
        this.zza((zzcik<?>)object);
        return object;
    }

    public final <V> Future<V> zzd(Callable<V> object) throws IllegalStateException {
        this.zzxf();
        zzbq.checkNotNull(object);
        object = new zzcik(this, object, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzjef) {
            ((FutureTask)object).run();
            return object;
        }
        this.zza((zzcik<?>)object);
        return object;
    }

    public final void zzg(Runnable runnable) throws IllegalStateException {
        this.zzxf();
        zzbq.checkNotNull(runnable);
        this.zza(new zzcik(this, runnable, false, "Task exception on worker thread"));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzh(Runnable object) throws IllegalStateException {
        this.zzxf();
        zzbq.checkNotNull(object);
        zzcik zzcik2 = new zzcik(this, (Runnable)object, false, "Task exception on network thread");
        object = this.zzjel;
        synchronized (object) {
            this.zzjei.add(zzcik2);
            if (this.zzjeg == null) {
                this.zzjeg = new zzcil(this, "Measurement Network", this.zzjei);
                this.zzjeg.setUncaughtExceptionHandler(this.zzjek);
                this.zzjeg.start();
            } else {
                this.zzjeg.zzrk();
            }
            return;
        }
    }

    @Override
    public final void zzve() {
        if (Thread.currentThread() != this.zzjef) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }
}


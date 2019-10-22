/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 *  android.util.Pair
 */
package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzdg;
import com.google.android.gms.common.api.internal.zzdm;
import com.google.android.gms.common.api.internal.zzs;
import com.google.android.gms.common.internal.zzaq;
import com.google.android.gms.common.internal.zzbq;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@KeepName
public abstract class BasePendingResult<R extends Result>
extends PendingResult<R> {
    static final ThreadLocal<Boolean> zzfot = new zzs();
    @KeepName
    private zzb mResultGuardian;
    private Status mStatus;
    private boolean zzan;
    private final CountDownLatch zzapd;
    private R zzfne;
    private final Object zzfou = new Object();
    private zza<R> zzfov;
    private WeakReference<GoogleApiClient> zzfow;
    private final ArrayList<PendingResult.zza> zzfox;
    private ResultCallback<? super R> zzfoy;
    private final AtomicReference<zzdm> zzfoz;
    private volatile boolean zzfpa;
    private boolean zzfpb;
    private zzaq zzfpc;
    private volatile zzdg<R> zzfpd;
    private boolean zzfpe = false;

    @Deprecated
    BasePendingResult() {
        this.zzapd = new CountDownLatch(1);
        this.zzfox = new ArrayList();
        this.zzfoz = new AtomicReference();
        this.zzfov = new zza(Looper.getMainLooper());
        this.zzfow = new WeakReference<Object>(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected BasePendingResult(GoogleApiClient googleApiClient) {
        this.zzapd = new CountDownLatch(1);
        this.zzfox = new ArrayList();
        this.zzfoz = new AtomicReference();
        Looper looper = googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper();
        this.zzfov = new zza(looper);
        this.zzfow = new WeakReference<GoogleApiClient>(googleApiClient);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final R get() {
        R r;
        boolean bl = true;
        Object object = this.zzfou;
        synchronized (object) {
            if (this.zzfpa) {
                bl = false;
            }
            zzbq.zza(bl, "Result has already been consumed.");
            zzbq.zza(this.isReady(), "Result is not ready.");
            r = this.zzfne;
            this.zzfne = null;
            this.zzfoy = null;
            this.zzfpa = true;
        }
        object = this.zzfoz.getAndSet(null);
        if (object != null) {
            object.zzc(this);
        }
        return r;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzc(R object) {
        this.zzfne = object;
        this.zzfpc = null;
        this.zzapd.countDown();
        this.mStatus = this.zzfne.getStatus();
        if (this.zzan) {
            this.zzfoy = null;
        } else if (this.zzfoy == null) {
            if (this.zzfne instanceof Releasable) {
                this.mResultGuardian = new zzb(this, null);
            }
        } else {
            this.zzfov.removeMessages(2);
            this.zzfov.zza(this.zzfoy, (R)this.get());
        }
        object = this.zzfox;
        int n = ((ArrayList)object).size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.zzfox.clear();
                return;
            }
            Object e = ((ArrayList)object).get(n2);
            ++n2;
            ((PendingResult.zza)e).zzr(this.mStatus);
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void zzd(Result object) {
        if (!(object instanceof Releasable)) return;
        try {
            ((Releasable)object).release();
            return;
        }
        catch (RuntimeException runtimeException) {
            object = String.valueOf(object);
            Log.w((String)"BasePendingResult", (String)new StringBuilder(String.valueOf(object).length() + 18).append("Unable to release ").append((String)object).toString(), (Throwable)runtimeException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final R await() {
        boolean bl = true;
        zzbq.zzgn("await must not be called on the UI thread");
        boolean bl2 = !this.zzfpa;
        zzbq.zza(bl2, "Result has already been consumed");
        bl2 = this.zzfpd == null ? bl : false;
        zzbq.zza(bl2, "Cannot await if then() has been called.");
        try {
            this.zzapd.await();
        }
        catch (InterruptedException interruptedException) {
            this.zzv(Status.zzfnj);
        }
        zzbq.zza(this.isReady(), "Result is not ready.");
        return this.get();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final R await(long l, TimeUnit timeUnit) {
        boolean bl = true;
        if (l > 0L) {
            zzbq.zzgn("await must not be called on the UI thread when time is greater than zero.");
        }
        boolean bl2 = !this.zzfpa;
        zzbq.zza(bl2, "Result has already been consumed.");
        bl2 = this.zzfpd == null ? bl : false;
        zzbq.zza(bl2, "Cannot await if then() has been called.");
        try {
            if (!this.zzapd.await(l, timeUnit)) {
                this.zzv(Status.zzfnl);
            }
        }
        catch (InterruptedException interruptedException) {
            this.zzv(Status.zzfnj);
        }
        zzbq.zza(this.isReady(), "Result is not ready.");
        return this.get();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void cancel() {
        Object object = this.zzfou;
        synchronized (object) {
            if (this.zzan || this.zzfpa) {
                return;
            }
            zzaq zzaq2 = this.zzfpc;
            if (zzaq2 != null) {
                try {
                    this.zzfpc.cancel();
                }
                catch (RemoteException remoteException) {}
            }
            BasePendingResult.zzd(this.zzfne);
            this.zzan = true;
            this.zzc(this.zzb(Status.zzfnm));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isCanceled() {
        Object object = this.zzfou;
        synchronized (object) {
            return this.zzan;
        }
    }

    public final boolean isReady() {
        return this.zzapd.getCount() == 0L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setResult(R r) {
        boolean bl = true;
        Object object = this.zzfou;
        synchronized (object) {
            if (!this.zzfpb && !this.zzan) {
                if (this.isReady()) {
                    // empty if block
                }
                boolean bl2 = !this.isReady();
                zzbq.zza(bl2, "Results have already been set");
                bl2 = !this.zzfpa ? bl : false;
                zzbq.zza(bl2, "Result has already been consumed");
                this.zzc(r);
                return;
            }
            BasePendingResult.zzd(r);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        boolean bl = true;
        Object object = this.zzfou;
        synchronized (object) {
            if (resultCallback == null) {
                this.zzfoy = null;
                return;
            }
            boolean bl2 = !this.zzfpa;
            zzbq.zza(bl2, "Result has already been consumed.");
            bl2 = this.zzfpd == null ? bl : false;
            zzbq.zza(bl2, "Cannot set callbacks if then() has been called.");
            if (((PendingResult)this).isCanceled()) {
                return;
            }
            if (this.isReady()) {
                this.zzfov.zza(resultCallback, (R)this.get());
            } else {
                this.zzfoy = resultCallback;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void zza(PendingResult.zza zza2) {
        boolean bl = zza2 != null;
        zzbq.checkArgument(bl, "Callback cannot be null.");
        Object object = this.zzfou;
        synchronized (object) {
            if (this.isReady()) {
                zza2.zzr(this.mStatus);
            } else {
                this.zzfox.add(zza2);
            }
            return;
        }
    }

    public final void zza(zzdm zzdm2) {
        this.zzfoz.set(zzdm2);
    }

    @Override
    public final Integer zzagv() {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean zzahh() {
        Object object = this.zzfou;
        synchronized (object) {
            if ((GoogleApiClient)this.zzfow.get() != null) {
                if (this.zzfpe) return ((PendingResult)this).isCanceled();
            }
            ((PendingResult)this).cancel();
            return ((PendingResult)this).isCanceled();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzahi() {
        boolean bl = this.zzfpe || zzfot.get() != false;
        this.zzfpe = bl;
    }

    protected abstract R zzb(Status var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzv(Status status) {
        Object object = this.zzfou;
        synchronized (object) {
            if (!this.isReady()) {
                this.setResult(this.zzb(status));
                this.zzfpb = true;
            }
            return;
        }
    }

    public static final class zza<R extends Result>
    extends Handler {
        public zza() {
            this(Looper.getMainLooper());
        }

        public zza(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message object) {
            switch (((Message)object).what) {
                default: {
                    int n = ((Message)object).what;
                    Log.wtf((String)"BasePendingResult", (String)new StringBuilder(45).append("Don't know how to handle message: ").append(n).toString(), (Throwable)new Exception());
                    return;
                }
                case 1: {
                    Object object2 = (Pair)((Message)object).obj;
                    object = (ResultCallback)object2.first;
                    object2 = (Result)object2.second;
                    try {
                        object.onResult(object2);
                        return;
                    }
                    catch (RuntimeException runtimeException) {
                        BasePendingResult.zzd((Result)object2);
                        throw runtimeException;
                    }
                }
                case 2: 
            }
            ((BasePendingResult)((Message)object).obj).zzv(Status.zzfnl);
        }

        public final void zza(ResultCallback<? super R> resultCallback, R r) {
            this.sendMessage(this.obtainMessage(1, (Object)new Pair(resultCallback, r)));
        }
    }

    final class zzb {
        private /* synthetic */ BasePendingResult zzfpf;

        private zzb(BasePendingResult basePendingResult) {
            this.zzfpf = basePendingResult;
        }

        /* synthetic */ zzb(BasePendingResult basePendingResult, zzs zzs2) {
            this(basePendingResult);
        }

        protected final void finalize() throws Throwable {
            BasePendingResult.zzd(this.zzfpf.zzfne);
            super.finalize();
        }
    }

}


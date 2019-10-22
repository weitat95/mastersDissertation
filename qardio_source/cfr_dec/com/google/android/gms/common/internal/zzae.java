/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzae
implements Handler.Callback {
    private final Handler mHandler;
    private final Object mLock;
    private final zzaf zzgab;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zzgac = new ArrayList();
    private ArrayList<GoogleApiClient.ConnectionCallbacks> zzgad = new ArrayList();
    private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zzgae = new ArrayList();
    private volatile boolean zzgaf = false;
    private final AtomicInteger zzgag = new AtomicInteger(0);
    private boolean zzgah = false;

    public zzae(Looper looper, zzaf zzaf2) {
        this.mLock = new Object();
        this.zzgab = zzaf2;
        this.mHandler = new Handler(looper, (Handler.Callback)this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean handleMessage(Message object) {
        if (object.what != 1) {
            int n = object.what;
            Log.wtf((String)"GmsClientEvents", (String)new StringBuilder(45).append("Don't know how to handle message: ").append(n).toString(), (Throwable)new Exception());
            return false;
        }
        GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks)object.obj;
        object = this.mLock;
        synchronized (object) {
            if (this.zzgaf && this.zzgab.isConnected() && this.zzgac.contains(connectionCallbacks)) {
                connectionCallbacks.onConnected(this.zzgab.zzafi());
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        zzbq.checkNotNull(connectionCallbacks);
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.zzgac.contains(connectionCallbacks)) {
            String string2 = String.valueOf(connectionCallbacks);
            Log.w((String)"GmsClientEvents", (String)new StringBuilder(String.valueOf(string2).length() + 62).append("registerConnectionCallbacks(): listener ").append(string2).append(" is already registered").toString());
        } else {
            this.zzgac.add(connectionCallbacks);
        }
        // MONITOREXIT : object
        if (!this.zzgab.isConnected()) return;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, (Object)connectionCallbacks));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener object) {
        zzbq.checkNotNull(object);
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.zzgae.contains(object)) {
                object = String.valueOf(object);
                Log.w((String)"GmsClientEvents", (String)new StringBuilder(String.valueOf(object).length() + 67).append("registerConnectionFailedListener(): listener ").append((String)object).append(" is already registered").toString());
            } else {
                this.zzgae.add((GoogleApiClient.OnConnectionFailedListener)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener object) {
        zzbq.checkNotNull(object);
        Object object2 = this.mLock;
        synchronized (object2) {
            if (!this.zzgae.remove(object)) {
                object = String.valueOf(object);
                Log.w((String)"GmsClientEvents", (String)new StringBuilder(String.valueOf(object).length() + 57).append("unregisterConnectionFailedListener(): listener ").append((String)object).append(" not found").toString());
            }
            return;
        }
    }

    public final void zzali() {
        this.zzgaf = false;
        this.zzgag.incrementAndGet();
    }

    public final void zzalj() {
        this.zzgaf = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzcg(int n) {
        int n2 = 0;
        boolean bl = Looper.myLooper() == this.mHandler.getLooper();
        zzbq.zza(bl, "onUnintentionalDisconnection must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        Object object = this.mLock;
        synchronized (object) {
            this.zzgah = true;
            ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = new ArrayList<GoogleApiClient.ConnectionCallbacks>(this.zzgac);
            int n3 = this.zzgag.get();
            int n4 = arrayList.size();
            while (n2 < n4) {
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = arrayList.get(n2);
                int n5 = n2 + 1;
                if (!this.zzgaf || this.zzgag.get() != n3) break;
                n2 = n5;
                if (!this.zzgac.contains(connectionCallbacks)) continue;
                connectionCallbacks.onConnectionSuspended(n);
                n2 = n5;
            }
            this.zzgad.clear();
            this.zzgah = false;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzk(Bundle bundle) {
        boolean bl = true;
        int n = 0;
        boolean bl2 = Looper.myLooper() == this.mHandler.getLooper();
        zzbq.zza(bl2, "onConnectionSuccess must only be called on the Handler thread");
        Object object = this.mLock;
        synchronized (object) {
            bl2 = !this.zzgah;
            zzbq.checkState(bl2);
            this.mHandler.removeMessages(1);
            this.zzgah = true;
            bl2 = this.zzgad.size() == 0 ? bl : false;
            zzbq.checkState(bl2);
            ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = new ArrayList<GoogleApiClient.ConnectionCallbacks>(this.zzgac);
            int n2 = this.zzgag.get();
            int n3 = arrayList.size();
            while (n < n3) {
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = arrayList.get(n);
                int n4 = n + 1;
                if (!this.zzgaf || !this.zzgab.isConnected() || this.zzgag.get() != n2) break;
                n = n4;
                if (this.zzgad.contains(connectionCallbacks)) continue;
                connectionCallbacks.onConnected(bundle);
                n = n4;
            }
            this.zzgad.clear();
            this.zzgah = false;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzk(ConnectionResult connectionResult) {
        int n = 0;
        boolean bl = Looper.myLooper() == this.mHandler.getLooper();
        zzbq.zza(bl, "onConnectionFailure must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        Object object = this.mLock;
        synchronized (object) {
            ArrayList<GoogleApiClient.OnConnectionFailedListener> arrayList = new ArrayList<GoogleApiClient.OnConnectionFailedListener>(this.zzgae);
            int n2 = this.zzgag.get();
            int n3 = arrayList.size();
            while (n < n3) {
                GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = arrayList.get(n);
                int n4 = n + 1;
                if (!this.zzgaf || this.zzgag.get() != n2) {
                    return;
                }
                n = n4;
                if (!this.zzgae.contains(onConnectionFailedListener)) continue;
                onConnectionFailedListener.onConnectionFailed(connectionResult);
                n = n4;
            }
            return;
        }
    }
}


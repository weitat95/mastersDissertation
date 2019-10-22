/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzae;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzbp;
import com.google.android.gms.common.api.internal.zzbq;
import com.google.android.gms.common.api.internal.zzbr;
import com.google.android.gms.common.api.internal.zzbs;
import com.google.android.gms.common.api.internal.zzbu;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzcq;
import com.google.android.gms.common.api.internal.zzcr;
import com.google.android.gms.common.api.internal.zzcv;
import com.google.android.gms.common.api.internal.zzcy;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.api.internal.zzj;
import com.google.android.gms.common.api.internal.zzu;
import com.google.android.gms.common.internal.zzbz;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class zzbo<O extends Api.ApiOptions>
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,
zzu {
    private final zzh<O> zzfmf;
    private final Api.zze zzfpv;
    private boolean zzfrw;
    final /* synthetic */ zzbm zzfti;
    private final Queue<zza> zzftj = new LinkedList<zza>();
    private final Api.zzb zzftk;
    private final zzae zzftl;
    private final Set<zzj> zzftm = new HashSet<zzj>();
    private final Map<zzck<?>, zzcr> zzftn = new HashMap();
    private final int zzfto;
    private final zzcv zzftp;
    private ConnectionResult zzftq = null;

    /*
     * Enabled aggressive block sorting
     */
    public zzbo(GoogleApi<O> googleApi) {
        this.zzfti = var1_1;
        this.zzfpv = googleApi.zza(zzbm.zza(var1_1).getLooper(), this);
        this.zzftk = this.zzfpv instanceof zzbz ? zzbz.zzals() : this.zzfpv;
        this.zzfmf = googleApi.zzagn();
        this.zzftl = new zzae();
        this.zzfto = googleApi.getInstanceId();
        if (this.zzfpv.zzaay()) {
            this.zzftp = googleApi.zza(zzbm.zzb(var1_1), zzbm.zza(var1_1));
            return;
        }
        this.zzftp = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzaiw() {
        this.zzaiz();
        this.zzi(ConnectionResult.zzfkr);
        this.zzajb();
        for (zzcr zzcr2 : this.zzftn.values()) {
            try {
                zzcr2.zzfnq.zzb(this.zzftk, new TaskCompletionSource<Void>());
            }
            catch (DeadObjectException deadObjectException) {
                this.onConnectionSuspended(1);
                this.zzfpv.disconnect();
                break;
            }
            catch (RemoteException remoteException) {
            }
        }
        while (this.zzfpv.isConnected() && !this.zzftj.isEmpty()) {
            this.zzb(this.zzftj.remove());
        }
        this.zzajc();
    }

    private final void zzaix() {
        this.zzaiz();
        this.zzfrw = true;
        this.zzftl.zzahw();
        zzbm.zza(this.zzfti).sendMessageDelayed(Message.obtain((Handler)zzbm.zza(this.zzfti), (int)9, this.zzfmf), zzbm.zzc(this.zzfti));
        zzbm.zza(this.zzfti).sendMessageDelayed(Message.obtain((Handler)zzbm.zza(this.zzfti), (int)11, this.zzfmf), zzbm.zzd(this.zzfti));
        zzbm.zza(this.zzfti, -1);
    }

    private final void zzajb() {
        if (this.zzfrw) {
            zzbm.zza(this.zzfti).removeMessages(11, this.zzfmf);
            zzbm.zza(this.zzfti).removeMessages(9, this.zzfmf);
            this.zzfrw = false;
        }
    }

    private final void zzajc() {
        zzbm.zza(this.zzfti).removeMessages(12, this.zzfmf);
        zzbm.zza(this.zzfti).sendMessageDelayed(zzbm.zza(this.zzfti).obtainMessage(12, this.zzfmf), zzbm.zzh(this.zzfti));
    }

    private final void zzb(zza zza2) {
        zza2.zza(this.zzftl, this.zzaay());
        try {
            zza2.zza(this);
            return;
        }
        catch (DeadObjectException deadObjectException) {
            this.onConnectionSuspended(1);
            this.zzfpv.disconnect();
            return;
        }
    }

    static /* synthetic */ void zzc(zzbo zzbo2) {
        zzbo2.zzaiw();
    }

    static /* synthetic */ void zzd(zzbo zzbo2) {
        zzbo2.zzaix();
    }

    static /* synthetic */ Api.zze zze(zzbo zzbo2) {
        return zzbo2.zzfpv;
    }

    private final void zzi(ConnectionResult connectionResult) {
        for (zzj zzj2 : this.zzftm) {
            String string2 = null;
            if (connectionResult == ConnectionResult.zzfkr) {
                string2 = this.zzfpv.zzagi();
            }
            zzj2.zza(this.zzfmf, connectionResult, string2);
        }
        this.zzftm.clear();
    }

    public final void connect() {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        if (this.zzfpv.isConnected() || this.zzfpv.isConnecting()) {
            return;
        }
        if (this.zzfpv.zzagg() && zzbm.zzi(this.zzfti) != 0) {
            zzbm.zza(this.zzfti, ((zzf)zzbm.zzg(this.zzfti)).isGooglePlayServicesAvailable(zzbm.zzb(this.zzfti)));
            if (zzbm.zzi(this.zzfti) != 0) {
                this.onConnectionFailed(new ConnectionResult(zzbm.zzi(this.zzfti), null));
                return;
            }
        }
        zzbu zzbu2 = new zzbu(this.zzfti, this.zzfpv, this.zzfmf);
        if (this.zzfpv.zzaay()) {
            this.zzftp.zza(zzbu2);
        }
        this.zzfpv.zza(zzbu2);
    }

    public final int getInstanceId() {
        return this.zzfto;
    }

    final boolean isConnected() {
        return this.zzfpv.isConnected();
    }

    @Override
    public final void onConnected(Bundle bundle) {
        if (Looper.myLooper() == zzbm.zza(this.zzfti).getLooper()) {
            this.zzaiw();
            return;
        }
        zzbm.zza(this.zzfti).post((Runnable)new zzbp(this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void onConnectionFailed(ConnectionResult object) {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        if (this.zzftp != null) {
            this.zzftp.zzajq();
        }
        this.zzaiz();
        zzbm.zza(this.zzfti, -1);
        this.zzi((ConnectionResult)object);
        if (((ConnectionResult)object).getErrorCode() == 4) {
            this.zzw(zzbm.zzaiu());
            return;
        }
        if (this.zzftj.isEmpty()) {
            this.zzftq = object;
            return;
        }
        Object object2 = zzbm.zzaiv();
        synchronized (object2) {
            if (zzbm.zze(this.zzfti) != null && zzbm.zzf(this.zzfti).contains(this.zzfmf)) {
                zzbm.zze(this.zzfti).zzb((ConnectionResult)object, this.zzfto);
                return;
            }
        }
        if (this.zzfti.zzc((ConnectionResult)object, this.zzfto)) return;
        {
            if (((ConnectionResult)object).getErrorCode() == 18) {
                this.zzfrw = true;
            }
            if (this.zzfrw) {
                zzbm.zza(this.zzfti).sendMessageDelayed(Message.obtain((Handler)zzbm.zza(this.zzfti), (int)9, this.zzfmf), zzbm.zzc(this.zzfti));
                return;
            }
        }
        object = this.zzfmf.zzagy();
        this.zzw(new Status(17, new StringBuilder(String.valueOf(object).length() + 38).append("API: ").append((String)object).append(" is not available on this device.").toString()));
    }

    @Override
    public final void onConnectionSuspended(int n) {
        if (Looper.myLooper() == zzbm.zza(this.zzfti).getLooper()) {
            this.zzaix();
            return;
        }
        zzbm.zza(this.zzfti).post((Runnable)new zzbq(this));
    }

    public final void resume() {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        if (this.zzfrw) {
            this.connect();
        }
    }

    public final void signOut() {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        this.zzw(zzbm.zzfsy);
        this.zzftl.zzahv();
        zzck[] arrzzck = this.zzftn.keySet().toArray(new zzck[this.zzftn.size()]);
        int n = arrzzck.length;
        for (int i = 0; i < n; ++i) {
            this.zza(new com.google.android.gms.common.api.internal.zzf(arrzzck[i], new TaskCompletionSource<Boolean>()));
        }
        this.zzi(new ConnectionResult(4));
        if (this.zzfpv.isConnected()) {
            this.zzfpv.zza(new zzbs(this));
        }
    }

    @Override
    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean bl) {
        if (Looper.myLooper() == zzbm.zza(this.zzfti).getLooper()) {
            this.onConnectionFailed(connectionResult);
            return;
        }
        zzbm.zza(this.zzfti).post((Runnable)new zzbr(this, connectionResult));
    }

    public final void zza(zza zza2) {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        if (this.zzfpv.isConnected()) {
            this.zzb(zza2);
            this.zzajc();
            return;
        }
        this.zzftj.add(zza2);
        if (this.zzftq != null && this.zzftq.hasResolution()) {
            this.onConnectionFailed(this.zzftq);
            return;
        }
        this.connect();
    }

    public final void zza(zzj zzj2) {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        this.zzftm.add(zzj2);
    }

    public final boolean zzaay() {
        return this.zzfpv.zzaay();
    }

    public final Api.zze zzahp() {
        return this.zzfpv;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzaij() {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        if (this.zzfrw) {
            this.zzajb();
            Status status = ((zzf)zzbm.zzg(this.zzfti)).isGooglePlayServicesAvailable(zzbm.zzb(this.zzfti)) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error.");
            this.zzw(status);
            this.zzfpv.disconnect();
        }
    }

    public final Map<zzck<?>, zzcr> zzaiy() {
        return this.zzftn;
    }

    public final void zzaiz() {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        this.zzftq = null;
    }

    public final ConnectionResult zzaja() {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        return this.zzftq;
    }

    public final void zzajd() {
        block3: {
            block2: {
                com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
                if (!this.zzfpv.isConnected() || this.zzftn.size() != 0) break block2;
                if (!this.zzftl.zzahu()) break block3;
                this.zzajc();
            }
            return;
        }
        this.zzfpv.disconnect();
    }

    final zzcxd zzaje() {
        if (this.zzftp == null) {
            return null;
        }
        return this.zzftp.zzaje();
    }

    public final void zzh(ConnectionResult connectionResult) {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        this.zzfpv.disconnect();
        this.onConnectionFailed(connectionResult);
    }

    public final void zzw(Status status) {
        com.google.android.gms.common.internal.zzbq.zza(zzbm.zza(this.zzfti));
        Iterator iterator = this.zzftj.iterator();
        while (iterator.hasNext()) {
            ((zza)iterator.next()).zzs(status);
        }
        this.zzftj.clear();
    }
}


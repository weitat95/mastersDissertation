/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzchg;
import com.google.android.gms.internal.zzchl;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzckv;
import com.google.android.gms.internal.zzckw;
import com.google.android.gms.internal.zzckx;
import com.google.android.gms.internal.zzcky;
import com.google.android.gms.internal.zzckz;

public final class zzcku
implements ServiceConnection,
zzf,
zzg {
    final /* synthetic */ zzckg zzjij;
    private volatile boolean zzjiq;
    private volatile zzchl zzjir;

    protected zzcku(zzckg zzckg2) {
        this.zzjij = zzckg2;
    }

    static /* synthetic */ boolean zza(zzcku zzcku2, boolean bl) {
        zzcku2.zzjiq = false;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void onConnected(Bundle object) {
        zzbq.zzge("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            block5: {
                try {
                    object = (zzche)this.zzjir.zzakn();
                    this.zzjir = null;
                    ((zzcjk)this.zzjij).zzawx().zzg(new zzckx(this, (zzche)object));
                    break block5;
                }
                catch (DeadObjectException deadObjectException) {
                }
                catch (IllegalStateException illegalStateException) {}
                this.zzjir = null;
                this.zzjiq = false;
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
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        zzbq.zzge("MeasurementServiceConnection.onConnectionFailed");
        zzchm zzchm2 = this.zzjij.zziwf.zzazx();
        if (zzchm2 != null) {
            zzchm2.zzazf().zzj("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzjiq = false;
            this.zzjir = null;
        }
        ((zzcjk)this.zzjij).zzawx().zzg(new zzckz(this));
    }

    @Override
    public final void onConnectionSuspended(int n) {
        zzbq.zzge("MeasurementServiceConnection.onConnectionSuspended");
        ((zzcjk)this.zzjij).zzawy().zzazi().log("Service connection suspended");
        ((zzcjk)this.zzjij).zzawx().zzg(new zzcky(this));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void onServiceConnected(ComponentName object, IBinder iBinder) {
        block14: {
            block13: {
                zzbq.zzge("MeasurementServiceConnection.onServiceConnected");
                // MONITORENTER : this
                if (iBinder == null) {
                    this.zzjiq = false;
                    ((zzcjk)this.zzjij).zzawy().zzazd().log("Service connected with null binder");
                    // MONITOREXIT : this
                    return;
                }
                object = iBinder.getInterfaceDescriptor();
                boolean bl = "com.google.android.gms.measurement.internal.IMeasurementService".equals(object);
                if (!bl) break block13;
                object = iBinder == null ? null : ((object = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService")) instanceof zzche ? (zzche)object : new zzchg(iBinder));
                ((zzcjk)this.zzjij).zzawy().zzazj().log("Bound to IMeasurementService interface");
                break block14;
                {
                    catch (RemoteException remoteException) {}
                }
            }
            try {
                ((zzcjk)this.zzjij).zzawy().zzazd().zzj("Got binder with a wrong descriptor", object);
                object = null;
            }
            catch (RemoteException remoteException) {
                object = null;
                ((zzcjk)this.zzjij).zzawy().zzazd().log("Service connect failed to get IMeasurementService");
            }
        }
        if (object != null) {
            ((zzcjk)this.zzjij).zzawx().zzg(new zzckv(this, (zzche)object));
            return;
        }
        this.zzjiq = false;
        try {
            zza.zzamc();
            ((zzcjk)this.zzjij).getContext().unbindService((ServiceConnection)zzckg.zza(this.zzjij));
            // MONITOREXIT : this
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        zzbq.zzge("MeasurementServiceConnection.onServiceDisconnected");
        ((zzcjk)this.zzjij).zzawy().zzazi().log("Service disconnected");
        ((zzcjk)this.zzjij).zzawx().zzg(new zzckw(this, componentName));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzbau() {
        ((zzcjk)this.zzjij).zzve();
        Context context = ((zzcjk)this.zzjij).getContext();
        synchronized (this) {
            if (this.zzjiq) {
                ((zzcjk)this.zzjij).zzawy().zzazj().log("Connection attempt already in progress");
                return;
            }
            if (this.zzjir != null) {
                ((zzcjk)this.zzjij).zzawy().zzazj().log("Already awaiting connection attempt");
                return;
            }
            this.zzjir = new zzchl(context, Looper.getMainLooper(), this, this);
            ((zzcjk)this.zzjij).zzawy().zzazj().log("Connecting to remote service");
            this.zzjiq = true;
            this.zzjir.zzakj();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzn(Intent intent) {
        ((zzcjk)this.zzjij).zzve();
        Context context = ((zzcjk)this.zzjij).getContext();
        zza zza2 = zza.zzamc();
        synchronized (this) {
            if (this.zzjiq) {
                ((zzcjk)this.zzjij).zzawy().zzazj().log("Connection attempt already in progress");
                return;
            }
            ((zzcjk)this.zzjij).zzawy().zzazj().log("Using local app measurement service");
            this.zzjiq = true;
            zza2.zza(context, intent, zzckg.zza(this.zzjij), 129);
            return;
        }
    }
}


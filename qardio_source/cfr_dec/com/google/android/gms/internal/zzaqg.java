/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.RemoteException;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqh;
import com.google.android.gms.internal.zzaqi;
import com.google.android.gms.internal.zzaqx;
import com.google.android.gms.internal.zzard;
import com.google.android.gms.internal.zzarf;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzarq;
import com.google.android.gms.internal.zzarr;
import com.google.android.gms.internal.zzash;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class zzaqg
extends zzaqa {
    private final zzaqi zzdtx;
    private zzarr zzdty;
    private final zzarf zzdtz;
    private final zzash zzdua;

    protected zzaqg(zzaqc zzaqc2) {
        super(zzaqc2);
        this.zzdua = new zzash(zzaqc2.zzws());
        this.zzdtx = new zzaqi(this);
        this.zzdtz = new zzaqh(this, zzaqc2);
    }

    private final void onServiceDisconnected(ComponentName componentName) {
        zzj.zzve();
        if (this.zzdty != null) {
            this.zzdty = null;
            this.zza("Disconnected from device AnalyticsService", (Object)componentName);
            this.zzwx().zzwp();
        }
    }

    static /* synthetic */ zzaqi zza(zzaqg zzaqg2) {
        return zzaqg2.zzdtx;
    }

    static /* synthetic */ void zza(zzaqg zzaqg2, ComponentName componentName) {
        zzaqg2.onServiceDisconnected(componentName);
    }

    static /* synthetic */ void zza(zzaqg zzaqg2, zzarr zzarr2) {
        zzaqg2.zza(zzarr2);
    }

    private final void zza(zzarr zzarr2) {
        zzj.zzve();
        this.zzdty = zzarr2;
        this.zzxr();
        this.zzwx().onServiceConnected();
    }

    static /* synthetic */ void zzb(zzaqg zzaqg2) {
        zzaqg2.zzxs();
    }

    private final void zzxr() {
        this.zzdua.start();
        this.zzdtz.zzs(zzarl.zzdxg.get());
    }

    private final void zzxs() {
        zzj.zzve();
        if (!this.isConnected()) {
            return;
        }
        this.zzdu("Inactivity, disconnecting from device AnalyticsService");
        this.disconnect();
    }

    public final boolean connect() {
        zzj.zzve();
        this.zzxf();
        if (this.zzdty != null) {
            return true;
        }
        zzarr zzarr2 = this.zzdtx.zzxt();
        if (zzarr2 != null) {
            this.zzdty = zzarr2;
            this.zzxr();
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void disconnect() {
        zzj.zzve();
        this.zzxf();
        try {
            zza.zzamc();
            this.getContext().unbindService((ServiceConnection)this.zzdtx);
        }
        catch (IllegalArgumentException illegalArgumentException) {
        }
        catch (IllegalStateException illegalStateException) {}
        if (this.zzdty != null) {
            this.zzdty = null;
            this.zzwx().zzwp();
        }
    }

    public final boolean isConnected() {
        zzj.zzve();
        this.zzxf();
        return this.zzdty != null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean zzb(zzarq zzarq2) {
        zzbq.checkNotNull(zzarq2);
        zzj.zzve();
        this.zzxf();
        zzarr zzarr2 = this.zzdty;
        if (zzarr2 == null) {
            return false;
        }
        String string2 = zzarq2.zzzk() ? zzard.zzyw() : zzard.zzyx();
        List<zzaqx> list = Collections.emptyList();
        try {
            zzarr2.zza(zzarq2.zzjh(), zzarq2.zzzi(), string2, list);
            this.zzxr();
            return true;
        }
        catch (RemoteException remoteException) {
            this.zzdu("Failed to send hits to AnalyticsService");
            return false;
        }
    }

    @Override
    protected final void zzvf() {
    }
}


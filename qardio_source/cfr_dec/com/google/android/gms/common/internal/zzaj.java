/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.Message
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.common.internal.zzai;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class zzaj
implements ServiceConnection {
    private ComponentName mComponentName;
    private int mState;
    private IBinder zzfzf;
    private final Set<ServiceConnection> zzgaq;
    private boolean zzgar;
    private final zzah zzgas;
    private /* synthetic */ zzai zzgat;

    public zzaj(zzai zzai2, zzah zzah2) {
        this.zzgat = zzai2;
        this.zzgas = zzah2;
        this.zzgaq = new HashSet<ServiceConnection>();
        this.mState = 2;
    }

    public final IBinder getBinder() {
        return this.zzfzf;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean isBound() {
        return this.zzgar;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        HashMap hashMap = zzai.zza(this.zzgat);
        synchronized (hashMap) {
            zzai.zzb(this.zzgat).removeMessages(1, (Object)this.zzgas);
            this.zzfzf = iBinder;
            this.mComponentName = componentName;
            Iterator<ServiceConnection> iterator = this.zzgaq.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.mState = 1;
                    return;
                }
                iterator.next().onServiceConnected(componentName, iBinder);
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onServiceDisconnected(ComponentName componentName) {
        HashMap hashMap = zzai.zza(this.zzgat);
        synchronized (hashMap) {
            zzai.zzb(this.zzgat).removeMessages(1, (Object)this.zzgas);
            this.zzfzf = null;
            this.mComponentName = componentName;
            Iterator<ServiceConnection> iterator = this.zzgaq.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.mState = 2;
                    return;
                }
                iterator.next().onServiceDisconnected(componentName);
            } while (true);
        }
    }

    public final void zza(ServiceConnection serviceConnection, String string2) {
        zzai.zzd(this.zzgat);
        zzai.zzc(this.zzgat);
        this.zzgas.zzall();
        this.zzgaq.add(serviceConnection);
    }

    public final boolean zza(ServiceConnection serviceConnection) {
        return this.zzgaq.contains((Object)serviceConnection);
    }

    public final boolean zzalm() {
        return this.zzgaq.isEmpty();
    }

    public final void zzb(ServiceConnection serviceConnection, String string2) {
        zzai.zzd(this.zzgat);
        zzai.zzc(this.zzgat);
        this.zzgaq.remove((Object)serviceConnection);
    }

    public final void zzgi(String string2) {
        this.mState = 3;
        this.zzgar = zzai.zzd(this.zzgat).zza(zzai.zzc(this.zzgat), string2, this.zzgas.zzall(), this, this.zzgas.zzalk());
        if (this.zzgar) {
            string2 = zzai.zzb(this.zzgat).obtainMessage(1, (Object)this.zzgas);
            zzai.zzb(this.zzgat).sendMessageDelayed((Message)string2, zzai.zze(this.zzgat));
            return;
        }
        this.mState = 2;
        try {
            zzai.zzd(this.zzgat);
            zzai.zzc(this.zzgat).unbindService((ServiceConnection)this);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public final void zzgj(String string2) {
        zzai.zzb(this.zzgat).removeMessages(1, (Object)this.zzgas);
        zzai.zzd(this.zzgat);
        zzai.zzc(this.zzgat).unbindService((ServiceConnection)this);
        this.zzgar = false;
        this.mState = 2;
    }
}


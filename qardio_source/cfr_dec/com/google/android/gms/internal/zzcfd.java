/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ContentProviderClient
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.internal.zzcdx;
import com.google.android.gms.internal.zzcdz;
import com.google.android.gms.internal.zzceu;
import com.google.android.gms.internal.zzcez;
import com.google.android.gms.internal.zzcfe;
import com.google.android.gms.internal.zzcfh;
import com.google.android.gms.internal.zzcfi;
import com.google.android.gms.internal.zzcfo;
import com.google.android.gms.internal.zzcfq;
import com.google.android.gms.internal.zzcfu;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class zzcfd {
    private final Context mContext;
    private final zzcfu<zzcez> zzikt;
    private ContentProviderClient zzilm = null;
    private boolean zziln = false;
    private final Map<zzck<LocationListener>, zzcfi> zzilo = new HashMap<zzck<LocationListener>, zzcfi>();
    private final Map<zzck<Object>, zzcfh> zzilp = new HashMap<zzck<Object>, zzcfh>();
    private final Map<zzck<LocationCallback>, zzcfe> zzilq = new HashMap<zzck<LocationCallback>, zzcfe>();

    public zzcfd(Context context, zzcfu<zzcez> zzcfu2) {
        this.mContext = context;
        this.zzikt = zzcfu2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final zzcfi zzm(zzci<LocationListener> zzci2) {
        Map<zzck<LocationListener>, zzcfi> map = this.zzilo;
        synchronized (map) {
            zzcfi zzcfi2;
            zzcfi zzcfi3 = zzcfi2 = this.zzilo.get(zzci2.zzajo());
            if (zzcfi2 == null) {
                zzcfi3 = new zzcfi(zzci2);
            }
            this.zzilo.put(zzci2.zzajo(), zzcfi3);
            return zzcfi3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void removeAllListeners() throws RemoteException {
        Map<zzck<Object>, zzev> map = this.zzilo;
        synchronized (map) {
            for (zzev zzev2 : this.zzilo.values()) {
                if (zzev2 == null) continue;
                this.zzikt.zzakn().zza(zzcfq.zza(zzev2, null));
            }
            this.zzilo.clear();
        }
        map = this.zzilq;
        synchronized (map) {
            for (zzev zzev2 : this.zzilq.values()) {
                if (zzev2 == null) continue;
                this.zzikt.zzakn().zza(zzcfq.zza((zzp)((Object)zzev2), null));
            }
            this.zzilq.clear();
        }
        map = this.zzilp;
        synchronized (map) {
            Iterator<zzev> iterator = this.zzilp.values().iterator();
            do {
                zzev zzev2;
                if (!iterator.hasNext()) {
                    this.zzilp.clear();
                    return;
                }
                zzev2 = (zzcfh)iterator.next();
                if (zzev2 == null) continue;
                this.zzikt.zzakn().zza(new zzcdz(2, null, zzev2.asBinder(), null));
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zza(LocationRequest locationRequest, zzci<LocationListener> object, zzceu zzceu2) throws RemoteException {
        this.zzikt.zzakm();
        zzcfi zzcfi2 = this.zzm((zzci<LocationListener>)object);
        object = this.zzikt.zzakn();
        zzcfo zzcfo2 = zzcfo.zza(locationRequest);
        zzcfi2 = zzcfi2.asBinder();
        locationRequest = zzceu2 != null ? zzceu2.asBinder() : null;
        object.zza(new zzcfq(1, zzcfo2, (IBinder)zzcfi2, null, null, (IBinder)locationRequest));
    }

    public final void zzavl() throws RemoteException {
        if (this.zziln) {
            this.zzbj(false);
        }
    }

    public final void zzbj(boolean bl) throws RemoteException {
        this.zzikt.zzakm();
        this.zzikt.zzakn().zzbj(bl);
        this.zziln = bl;
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzcdt;
import com.google.android.gms.internal.zzceu;
import com.google.android.gms.internal.zzcez;
import com.google.android.gms.internal.zzcfb;
import com.google.android.gms.internal.zzcfd;
import com.google.android.gms.internal.zzcfn;
import com.google.android.gms.internal.zzcfu;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

public final class zzcfk
extends zzcdt {
    private final zzcfd zzilu;

    public zzcfk(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String string2, zzr zzr2) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, string2, zzr2);
        this.zzilu = new zzcfd(context, this.zzikt);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void disconnect() {
        zzcfd zzcfd2 = this.zzilu;
        synchronized (zzcfd2) {
            boolean bl = this.isConnected();
            if (bl) {
                try {
                    this.zzilu.removeAllListeners();
                    this.zzilu.zzavl();
                }
                catch (Exception exception) {
                    Log.e((String)"LocationClientImpl", (String)"Client disconnected before listeners could be cleaned up", (Throwable)exception);
                }
            }
            super.disconnect();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zza(LocationRequest locationRequest, zzci<LocationListener> zzci2, zzceu zzceu2) throws RemoteException {
        zzcfd zzcfd2 = this.zzilu;
        synchronized (zzcfd2) {
            this.zzilu.zza(locationRequest, zzci2, zzceu2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zza(LocationSettingsRequest locationSettingsRequest, zzn<LocationSettingsResult> object, String string2) throws RemoteException {
        boolean bl = true;
        this.zzakm();
        boolean bl2 = locationSettingsRequest != null;
        zzbq.checkArgument(bl2, "locationSettingsRequest can't be null nor empty.");
        bl2 = object != null ? bl : false;
        zzbq.checkArgument(bl2, "listener can't be null.");
        object = new zzcfn((zzn<LocationSettingsResult>)object);
        ((zzcez)this.zzakn()).zza(locationSettingsRequest, (zzcfb)object, string2);
    }
}


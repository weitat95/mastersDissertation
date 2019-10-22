/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.common.internal.zzaj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;
import java.util.HashMap;

final class zzai
extends zzag
implements Handler.Callback {
    private final Context mApplicationContext;
    private final Handler mHandler;
    private final HashMap<zzah, zzaj> zzgam = new HashMap();
    private final zza zzgan;
    private final long zzgao;
    private final long zzgap;

    zzai(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), (Handler.Callback)this);
        this.zzgan = zza.zzamc();
        this.zzgao = 5000L;
        this.zzgap = 300000L;
    }

    static /* synthetic */ HashMap zza(zzai zzai2) {
        return zzai2.zzgam;
    }

    static /* synthetic */ Handler zzb(zzai zzai2) {
        return zzai2.mHandler;
    }

    static /* synthetic */ Context zzc(zzai zzai2) {
        return zzai2.mApplicationContext;
    }

    static /* synthetic */ zza zzd(zzai zzai2) {
        return zzai2.zzgan;
    }

    static /* synthetic */ long zze(zzai zzai2) {
        return zzai2.zzgap;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean handleMessage(Message object) {
        switch (object.what) {
            default: {
                return false;
            }
            case 0: {
                HashMap<zzah, zzaj> hashMap = this.zzgam;
                synchronized (hashMap) {
                    object = (zzah)object.obj;
                    zzaj zzaj2 = this.zzgam.get(object);
                    if (zzaj2 != null && zzaj2.zzalm()) {
                        if (zzaj2.isBound()) {
                            zzaj2.zzgj("GmsClientSupervisor");
                        }
                        this.zzgam.remove(object);
                    }
                    return true;
                }
            }
            case 1: 
        }
        HashMap<zzah, zzaj> hashMap = this.zzgam;
        synchronized (hashMap) {
            zzah zzah2 = (zzah)object.obj;
            zzaj zzaj3 = this.zzgam.get(zzah2);
            if (zzaj3 != null && zzaj3.getState() == 3) {
                object = String.valueOf(zzah2);
                Log.wtf((String)"GmsClientSupervisor", (String)new StringBuilder(String.valueOf(object).length() + 47).append("Timeout waiting for ServiceConnection callback ").append((String)object).toString(), (Throwable)new Exception());
                ComponentName componentName = zzaj3.getComponentName();
                object = componentName;
                if (componentName == null) {
                    object = zzah2.getComponentName();
                }
                if (object == null) {
                    object = new ComponentName(zzah2.getPackage(), "unknown");
                }
                zzaj3.onServiceDisconnected((ComponentName)object);
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final boolean zza(zzah object, ServiceConnection serviceConnection, String string2) {
        zzbq.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        HashMap<zzah, zzaj> hashMap = this.zzgam;
        synchronized (hashMap) {
            zzaj zzaj2 = this.zzgam.get(object);
            if (zzaj2 == null) {
                zzaj2 = new zzaj(this, (zzah)object);
                zzaj2.zza(serviceConnection, string2);
                zzaj2.zzgi(string2);
                this.zzgam.put((zzah)object, zzaj2);
                object = zzaj2;
                return ((zzaj)object).isBound();
            } else {
                this.mHandler.removeMessages(0, object);
                if (zzaj2.zza(serviceConnection)) {
                    object = String.valueOf(object);
                    throw new IllegalStateException(new StringBuilder(String.valueOf(object).length() + 81).append("Trying to bind a GmsServiceConnection that was already connected before.  config=").append((String)object).toString());
                }
                zzaj2.zza(serviceConnection, string2);
                switch (zzaj2.getState()) {
                    case 1: {
                        serviceConnection.onServiceConnected(zzaj2.getComponentName(), zzaj2.getBinder());
                        object = zzaj2;
                        return ((zzaj)object).isBound();
                    }
                    case 2: {
                        zzaj2.zzgi(string2);
                        object = zzaj2;
                        return ((zzaj)object).isBound();
                    }
                    default: {
                        object = zzaj2;
                    }
                }
            }
            return ((zzaj)object).isBound();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final void zzb(zzah object, ServiceConnection serviceConnection, String string2) {
        zzbq.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        HashMap<zzah, zzaj> hashMap = this.zzgam;
        synchronized (hashMap) {
            zzaj zzaj2 = this.zzgam.get(object);
            if (zzaj2 == null) {
                object = String.valueOf(object);
                throw new IllegalStateException(new StringBuilder(String.valueOf(object).length() + 50).append("Nonexistent connection status for service config: ").append((String)object).toString());
            }
            if (!zzaj2.zza(serviceConnection)) {
                object = String.valueOf(object);
                throw new IllegalStateException(new StringBuilder(String.valueOf(object).length() + 76).append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=").append((String)object).toString());
            }
            zzaj2.zzb(serviceConnection, string2);
            if (zzaj2.zzalm()) {
                object = this.mHandler.obtainMessage(0, object);
                this.mHandler.sendMessageDelayed((Message)object, this.zzgao);
            }
            return;
        }
    }
}


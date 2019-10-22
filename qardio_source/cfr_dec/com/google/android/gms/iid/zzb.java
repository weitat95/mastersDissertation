/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.IBinder
 *  android.util.Log
 */
package com.google.android.gms.iid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.iid.zzc;
import com.google.android.gms.iid.zzf;
import com.google.android.gms.internal.zzcxs;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class zzb
extends Service {
    private final Object mLock;
    final ExecutorService zzieo = Executors.newSingleThreadExecutor();
    private Binder zziep;
    private int zzieq;
    private int zzier = 0;

    public zzb() {
        this.mLock = new Object();
    }

    static /* synthetic */ void zza(zzb zzb2, Intent intent) {
        zzb2.zzh(intent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzh(Intent object) {
        if (object != null) {
            zzcxs.completeWakefulIntent(object);
        }
        object = this.mLock;
        synchronized (object) {
            --this.zzier;
            if (this.zzier == 0) {
                this.stopSelfResult(this.zzieq);
            }
            return;
        }
    }

    public abstract void handleIntent(Intent var1);

    public final IBinder onBind(Intent intent) {
        synchronized (this) {
            if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
                Log.d((String)"EnhancedIntentService", (String)"Service received bind request");
            }
            if (this.zziep == null) {
                this.zziep = new zzf(this);
            }
            intent = this.zziep;
            return intent;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final int onStartCommand(Intent intent, int n, int n2) {
        Object object = this.mLock;
        // MONITORENTER : object
        this.zzieq = n2;
        ++this.zzier;
        // MONITOREXIT : object
        if (intent == null) {
            this.zzh(intent);
            return 2;
        }
        this.zzieo.execute(new zzc(this, intent, intent));
        return 3;
    }
}


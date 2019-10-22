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
package com.google.firebase.iid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.google.firebase.iid.zzc;
import com.google.firebase.iid.zzf;
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
            WakefulBroadcastReceiver.completeWakefulIntent(object);
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
     */
    public final int onStartCommand(Intent intent, int n, int n2) {
        Object object = this.mLock;
        synchronized (object) {
            this.zzieq = n2;
            ++this.zzier;
        }
        object = this.zzp(intent);
        if (object == null) {
            this.zzh(intent);
            return 2;
        }
        if (this.zzq((Intent)object)) {
            this.zzh(intent);
            return 2;
        }
        this.zzieo.execute(new zzc(this, (Intent)object, intent));
        return 3;
    }

    protected Intent zzp(Intent intent) {
        return intent;
    }

    public boolean zzq(Intent intent) {
        return false;
    }
}


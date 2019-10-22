/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.util.Log
 */
package com.google.android.gms.iid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.iid.zzd;
import com.google.android.gms.iid.zzf;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public final class zzh
implements ServiceConnection {
    private final Context zzair;
    private final Intent zzifb;
    private final ScheduledExecutorService zzifc;
    private final Queue<zzd> zzifd = new LinkedList<zzd>();
    private zzf zzife;
    private boolean zziff = false;

    public zzh(Context context, String string2) {
        this(context, string2, new ScheduledThreadPoolExecutor(0));
    }

    private zzh(Context context, String string2, ScheduledExecutorService scheduledExecutorService) {
        this.zzair = context.getApplicationContext();
        this.zzifb = new Intent(string2).setPackage(this.zzair.getPackageName());
        this.zzifc = scheduledExecutorService;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzavd() {
        synchronized (this) {
            if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
                Log.d((String)"EnhancedIntentService", (String)"flush queue called");
            }
            while (!this.zzifd.isEmpty()) {
                boolean bl;
                if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
                    Log.d((String)"EnhancedIntentService", (String)"found intent to be delivered");
                }
                if (this.zzife != null && this.zzife.isBinderAlive()) {
                    if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
                        Log.d((String)"EnhancedIntentService", (String)"binder is alive, sending the intent.");
                    }
                    zzd zzd2 = this.zzifd.poll();
                    this.zzife.zza(zzd2);
                    continue;
                }
                if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
                    bl = !this.zziff;
                    Log.d((String)"EnhancedIntentService", (String)new StringBuilder(39).append("binder is dead. start connection? ").append(bl).toString());
                }
                if (this.zziff) break;
                this.zziff = true;
                try {
                    bl = zza.zzamc().zza(this.zzair, this.zzifb, this, 65);
                    if (bl) break;
                    Log.e((String)"EnhancedIntentService", (String)"binding to the service failed");
                }
                catch (SecurityException securityException) {
                    Log.e((String)"EnhancedIntentService", (String)"Exception while binding the service", (Throwable)securityException);
                }
                while (!this.zzifd.isEmpty()) {
                    this.zzifd.poll().finish();
                }
                break block7;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onServiceConnected(ComponentName object, IBinder iBinder) {
        synchronized (this) {
            this.zziff = false;
            this.zzife = (zzf)iBinder;
            if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
                object = String.valueOf(object);
                Log.d((String)"EnhancedIntentService", (String)new StringBuilder(String.valueOf(object).length() + 20).append("onServiceConnected: ").append((String)object).toString());
            }
            this.zzavd();
            return;
        }
    }

    public final void onServiceDisconnected(ComponentName object) {
        if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
            object = String.valueOf(object);
            Log.d((String)"EnhancedIntentService", (String)new StringBuilder(String.valueOf(object).length() + 23).append("onServiceDisconnected: ").append((String)object).toString());
        }
        this.zzavd();
    }

    public final void zza(Intent intent, BroadcastReceiver.PendingResult pendingResult) {
        synchronized (this) {
            if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
                Log.d((String)"EnhancedIntentService", (String)"new intent queued in the bind-strategy delivery");
            }
            this.zzifd.add(new zzd(intent, pendingResult, this.zzifc));
            this.zzavd();
            return;
        }
    }
}


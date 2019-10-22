/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzj;
import java.util.concurrent.atomic.AtomicInteger;

final class zzh
extends Handler {
    private /* synthetic */ zzd zzfza;

    public zzh(zzd zzd2, Looper looper) {
        this.zzfza = zzd2;
        super(looper);
    }

    private static void zza(Message message) {
        ((zzi)message.obj).unregister();
    }

    private static boolean zzb(Message message) {
        return message.what == 2 || message.what == 1 || message.what == 7;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public final void handleMessage(Message object) {
        PendingIntent pendingIntent = null;
        if (this.zzfza.zzfyx.get() != object.arg1) {
            if (zzh.zzb(object)) {
                zzh.zza(object);
            }
            return;
        }
        if (!(object.what != 1 && object.what != 7 && object.what != 4 && object.what != 5 || this.zzfza.isConnecting())) {
            zzh.zza(object);
            return;
        }
        if (object.what == 4) {
            void var1_3;
            zzd.zza(this.zzfza, new ConnectionResult(object.arg2));
            if (zzd.zzb(this.zzfza) && !zzd.zzc(this.zzfza)) {
                zzd.zza(this.zzfza, 3, null);
                return;
            }
            if (zzd.zzd(this.zzfza) != null) {
                ConnectionResult connectionResult = zzd.zzd(this.zzfza);
            } else {
                ConnectionResult connectionResult = new ConnectionResult(8);
            }
            this.zzfza.zzfym.zzf((ConnectionResult)var1_3);
            this.zzfza.onConnectionFailed((ConnectionResult)var1_3);
            return;
        }
        if (object.what == 5) {
            void var1_6;
            if (zzd.zzd(this.zzfza) != null) {
                ConnectionResult connectionResult = zzd.zzd(this.zzfza);
            } else {
                ConnectionResult connectionResult = new ConnectionResult(8);
            }
            this.zzfza.zzfym.zzf((ConnectionResult)var1_6);
            this.zzfza.onConnectionFailed((ConnectionResult)var1_6);
            return;
        }
        if (object.what == 3) {
            if (object.obj instanceof PendingIntent) {
                pendingIntent = (PendingIntent)object.obj;
            }
            ConnectionResult connectionResult = new ConnectionResult(object.arg2, pendingIntent);
            this.zzfza.zzfym.zzf(connectionResult);
            this.zzfza.onConnectionFailed(connectionResult);
            return;
        }
        if (object.what == 6) {
            zzd.zza(this.zzfza, 5, null);
            if (zzd.zze(this.zzfza) != null) {
                zzd.zze(this.zzfza).onConnectionSuspended(object.arg2);
            }
            this.zzfza.onConnectionSuspended(object.arg2);
            zzd.zza(this.zzfza, 5, 1, null);
            return;
        }
        if (object.what == 2 && !this.zzfza.isConnected()) {
            zzh.zza(object);
            return;
        }
        if (zzh.zzb(object)) {
            ((zzi)object.obj).zzaks();
            return;
        }
        int n = object.what;
        Log.wtf((String)"GmsClient", (String)new StringBuilder(45).append("Don't know how to handle message: ").append(n).toString(), (Throwable)new Exception());
    }
}


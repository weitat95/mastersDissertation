/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.iid.zzj;
import com.google.firebase.iid.zzk;
import com.google.firebase.iid.zzq;
import com.google.firebase.iid.zzr;
import com.google.firebase.iid.zzt;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class zzi {
    private static zzi zznyx;
    private final Context zzair;
    private final ScheduledExecutorService zznyy;
    private zzk zznyz = new zzk(this, null);
    private int zznza = 1;

    private zzi(Context context, ScheduledExecutorService scheduledExecutorService) {
        this.zznyy = scheduledExecutorService;
        this.zzair = context.getApplicationContext();
    }

    static /* synthetic */ Context zza(zzi zzi2) {
        return zzi2.zzair;
    }

    private final <T> Task<T> zza(zzr<T> object) {
        synchronized (this) {
            if (Log.isLoggable((String)"MessengerIpcClient", (int)3)) {
                String string2 = String.valueOf(object);
                Log.d((String)"MessengerIpcClient", (String)new StringBuilder(String.valueOf(string2).length() + 9).append("Queueing ").append(string2).toString());
            }
            if (!this.zznyz.zzb((zzr)object)) {
                this.zznyz = new zzk(this, null);
                this.zznyz.zzb((zzr)object);
            }
            object = ((zzr)object).zzgrq.getTask();
            return object;
        }
    }

    static /* synthetic */ ScheduledExecutorService zzb(zzi zzi2) {
        return zzi2.zznyy;
    }

    private final int zzcja() {
        synchronized (this) {
            int n = this.zznza;
            this.zznza = n + 1;
            return n;
        }
    }

    public static zzi zzev(Context object) {
        synchronized (zzi.class) {
            if (zznyx == null) {
                zznyx = new zzi((Context)object, Executors.newSingleThreadScheduledExecutor());
            }
            object = zznyx;
            return object;
        }
    }

    public final Task<Void> zzh(int n, Bundle bundle) {
        return this.zza(new zzq(this.zzcja(), 2, bundle));
    }

    public final Task<Bundle> zzi(int n, Bundle bundle) {
        return this.zza(new zzt(this.zzcja(), 1, bundle));
    }
}


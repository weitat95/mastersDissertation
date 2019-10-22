/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Process
 *  android.util.Log
 */
package com.google.android.gms.iid;

import android.os.Binder;
import android.os.Process;
import android.util.Log;
import com.google.android.gms.iid.zzb;
import com.google.android.gms.iid.zzd;
import com.google.android.gms.iid.zzg;
import java.util.concurrent.ExecutorService;

public final class zzf
extends Binder {
    private final zzb zziey;

    zzf(zzb zzb2) {
        this.zziey = zzb2;
    }

    static /* synthetic */ zzb zza(zzf zzf2) {
        return zzf2.zziey;
    }

    public final void zza(zzd zzd2) {
        if (Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Binding only allowed within app");
        }
        if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
            Log.d((String)"EnhancedIntentService", (String)"service received new intent via bind strategy");
        }
        if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
            Log.d((String)"EnhancedIntentService", (String)"intent being queued for bg execution");
        }
        this.zziey.zzieo.execute(new zzg(this, zzd2));
    }
}


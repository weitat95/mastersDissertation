/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.Process
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.content.Intent;
import android.os.Binder;
import android.os.Process;
import android.util.Log;
import com.google.firebase.iid.zzb;
import com.google.firebase.iid.zzd;
import com.google.firebase.iid.zzg;
import java.util.concurrent.ExecutorService;

public final class zzf
extends Binder {
    private final zzb zznyl;

    zzf(zzb zzb2) {
        this.zznyl = zzb2;
    }

    static /* synthetic */ zzb zza(zzf zzf2) {
        return zzf2.zznyl;
    }

    public final void zza(zzd zzd2) {
        if (Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Binding only allowed within app");
        }
        if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
            Log.d((String)"EnhancedIntentService", (String)"service received new intent via bind strategy");
        }
        if (this.zznyl.zzq(zzd2.intent)) {
            zzd2.finish();
            return;
        }
        if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
            Log.d((String)"EnhancedIntentService", (String)"intent being queued for bg execution");
        }
        this.zznyl.zzieo.execute(new zzg(this, zzd2));
    }
}


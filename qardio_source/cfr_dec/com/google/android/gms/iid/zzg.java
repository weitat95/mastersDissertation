/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.util.Log
 */
package com.google.android.gms.iid;

import android.content.Intent;
import android.util.Log;
import com.google.android.gms.iid.zzd;
import com.google.android.gms.iid.zzf;

final class zzg
implements Runnable {
    private /* synthetic */ zzd zziez;
    private /* synthetic */ zzf zzifa;

    zzg(zzf zzf2, zzd zzd2) {
        this.zzifa = zzf2;
        this.zziez = zzd2;
    }

    @Override
    public final void run() {
        if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
            Log.d((String)"EnhancedIntentService", (String)"bg processing of the intent starting now");
        }
        zzf.zza(this.zzifa).handleIntent(this.zziez.intent);
        this.zziez.finish();
    }
}


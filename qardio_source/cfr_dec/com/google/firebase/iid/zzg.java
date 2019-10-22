/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;
import com.google.firebase.iid.zzd;
import com.google.firebase.iid.zzf;

final class zzg
implements Runnable {
    private /* synthetic */ zzd zznym;
    private /* synthetic */ zzf zznyn;

    zzg(zzf zzf2, zzd zzd2) {
        this.zznyn = zzf2;
        this.zznym = zzd2;
    }

    @Override
    public final void run() {
        if (Log.isLoggable((String)"EnhancedIntentService", (int)3)) {
            Log.d((String)"EnhancedIntentService", (String)"bg processing of the intent starting now");
        }
        zzf.zza(this.zznyn).handleIntent(this.zznym.intent);
        this.zznym.finish();
    }
}


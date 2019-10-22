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

final class zze
implements Runnable {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ zzd zziex;

    zze(zzd zzd2, Intent intent) {
        this.zziex = zzd2;
        this.val$intent = intent;
    }

    @Override
    public final void run() {
        String string2 = this.val$intent.getAction();
        Log.w((String)"EnhancedIntentService", (String)new StringBuilder(String.valueOf(string2).length() + 61).append("Service took too long to process intent: ").append(string2).append(" App may get closed.").toString());
        this.zziex.finish();
    }
}


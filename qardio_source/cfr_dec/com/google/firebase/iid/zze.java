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

final class zze
implements Runnable {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ zzd zznyk;

    zze(zzd zzd2, Intent intent) {
        this.zznyk = zzd2;
        this.val$intent = intent;
    }

    @Override
    public final void run() {
        String string2 = this.val$intent.getAction();
        Log.w((String)"EnhancedIntentService", (String)new StringBuilder(String.valueOf(string2).length() + 61).append("Service took too long to process intent: ").append(string2).append(" App may get closed.").toString());
        this.zznyk.finish();
    }
}


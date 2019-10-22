/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.google.android.gms.dynamic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

final class zzf
implements View.OnClickListener {
    private /* synthetic */ Context val$context;
    private /* synthetic */ Intent zzgwl;

    zzf(Context context, Intent intent) {
        this.val$context = context;
        this.zzgwl = intent;
    }

    public final void onClick(View view) {
        try {
            this.val$context.startActivity(this.zzgwl);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Log.e((String)"DeferredLifecycleHelper", (String)"Failed to start resolution intent", (Throwable)activityNotFoundException);
            return;
        }
    }
}


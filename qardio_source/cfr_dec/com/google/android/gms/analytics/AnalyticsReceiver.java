/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.internal.zzasc;

public final class AnalyticsReceiver
extends BroadcastReceiver {
    private zzasc zzdok;

    public final void onReceive(Context context, Intent intent) {
        if (this.zzdok == null) {
            this.zzdok = new zzasc();
        }
        zzasc.onReceive(context, intent);
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.internal.zzcid;
import com.google.android.gms.internal.zzcif;

public final class AppMeasurementInstallReferrerReceiver
extends BroadcastReceiver
implements zzcif {
    private zzcid zziwp;

    @Override
    public final BroadcastReceiver.PendingResult doGoAsync() {
        return this.goAsync();
    }

    @Override
    public final void doStartService(Context context, Intent intent) {
    }

    public final void onReceive(Context context, Intent intent) {
        if (this.zziwp == null) {
            this.zziwp = new zzcid(this);
        }
        this.zziwp.onReceive(context, intent);
    }
}


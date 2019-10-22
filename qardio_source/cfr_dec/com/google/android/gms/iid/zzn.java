/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.util.Log
 */
package com.google.android.gms.iid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.iid.zzl;

final class zzn
extends BroadcastReceiver {
    private /* synthetic */ zzl zzigd;

    zzn(zzl zzl2) {
        this.zzigd = zzl2;
    }

    public final void onReceive(Context context, Intent intent) {
        if (Log.isLoggable((String)"InstanceID/Rpc", (int)3)) {
            Log.d((String)"InstanceID/Rpc", (String)"Received GSF callback via dynamic receiver");
        }
        this.zzigd.zzk(intent);
    }
}


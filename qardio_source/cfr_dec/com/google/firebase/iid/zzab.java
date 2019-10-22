/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.zzaa;

final class zzab
extends BroadcastReceiver {
    private zzaa zznzy;

    public zzab(zzaa zzaa2) {
        this.zznzy = zzaa2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void onReceive(Context context, Intent intent) {
        if (this.zznzy == null || !this.zznzy.zzcjp()) {
            return;
        }
        if (FirebaseInstanceId.zzcix()) {
            Log.d((String)"FirebaseInstanceId", (String)"Connectivity changed. Starting background sync.");
        }
        FirebaseInstanceId.zzb(this.zznzy, 0L);
        this.zznzy.getContext().unregisterReceiver((BroadcastReceiver)this);
        this.zznzy = null;
    }

    public final void zzcjq() {
        if (FirebaseInstanceId.zzcix()) {
            Log.d((String)"FirebaseInstanceId", (String)"Connectivity change received registered");
        }
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        this.zznzy.getContext().registerReceiver((BroadcastReceiver)this, intentFilter);
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 */
package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.api.internal.zzby;

public final class zzbx
extends BroadcastReceiver {
    private Context mContext;
    private final zzby zzftx;

    public zzbx(zzby zzby2) {
        this.zzftx = zzby2;
    }

    public final void onReceive(Context object, Intent intent) {
        intent = intent.getData();
        object = null;
        if (intent != null) {
            object = intent.getSchemeSpecificPart();
        }
        if ("com.google.android.gms".equals(object)) {
            this.zzftx.zzahg();
            this.unregister();
        }
    }

    public final void setContext(Context context) {
        this.mContext = context;
    }

    public final void unregister() {
        synchronized (this) {
            if (this.mContext != null) {
                this.mContext.unregisterReceiver((BroadcastReceiver)this);
            }
            this.mContext = null;
            return;
        }
    }
}


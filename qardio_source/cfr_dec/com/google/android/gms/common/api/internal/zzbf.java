/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.internal.zzba;

final class zzbf
extends Handler {
    private /* synthetic */ zzba zzfsj;

    zzbf(zzba zzba2, Looper looper) {
        this.zzfsj = zzba2;
        super(looper);
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            default: {
                int n = message.what;
                Log.w((String)"GoogleApiClientImpl", (String)new StringBuilder(31).append("Unknown message id: ").append(n).toString());
                return;
            }
            case 1: {
                zzba.zzb(this.zzfsj);
                return;
            }
            case 2: 
        }
        zzba.zza(this.zzfsj);
    }
}


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
import com.google.android.gms.common.api.internal.zzbi;
import com.google.android.gms.common.api.internal.zzbj;

final class zzbk
extends Handler {
    private /* synthetic */ zzbi zzfsw;

    zzbk(zzbi zzbi2, Looper looper) {
        this.zzfsw = zzbi2;
        super(looper);
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            default: {
                int n = message.what;
                Log.w((String)"GACStateManager", (String)new StringBuilder(31).append("Unknown message id: ").append(n).toString());
                return;
            }
            case 1: {
                ((zzbj)message.obj).zzc(this.zzfsw);
                return;
            }
            case 2: 
        }
        throw (RuntimeException)message.obj;
    }
}


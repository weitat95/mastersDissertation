/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.iid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.iid.zzl;

final class zzm
extends Handler {
    private /* synthetic */ zzl zzigd;

    zzm(zzl zzl2, Looper looper) {
        this.zzigd = zzl2;
        super(looper);
    }

    public final void handleMessage(Message message) {
        this.zzigd.zzc(message);
    }
}


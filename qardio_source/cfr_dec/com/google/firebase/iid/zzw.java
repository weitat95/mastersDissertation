/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.firebase.iid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.firebase.iid.zzv;

final class zzw
extends Handler {
    private /* synthetic */ zzv zznzo;

    zzw(zzv zzv2, Looper looper) {
        this.zznzo = zzv2;
        super(looper);
    }

    public final void handleMessage(Message message) {
        zzv.zza(this.zznzo, message);
    }
}


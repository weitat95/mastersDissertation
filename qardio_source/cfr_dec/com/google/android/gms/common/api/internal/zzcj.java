/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.common.internal.zzbq;

final class zzcj
extends Handler {
    private /* synthetic */ zzci zzfum;

    public zzcj(zzci zzci2, Looper looper) {
        this.zzfum = zzci2;
        super(looper);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void handleMessage(Message message) {
        boolean bl = true;
        if (message.what != 1) {
            bl = false;
        }
        zzbq.checkArgument(bl);
        this.zzfum.zzb((zzcl)message.obj);
    }
}


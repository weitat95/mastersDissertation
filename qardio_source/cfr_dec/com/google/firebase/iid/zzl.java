/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 */
package com.google.firebase.iid;

import android.os.Handler;
import android.os.Message;
import com.google.firebase.iid.zzk;

final class zzl
implements Handler.Callback {
    private final zzk zznzg;

    zzl(zzk zzk2) {
        this.zznzg = zzk2;
    }

    public final boolean handleMessage(Message message) {
        return this.zznzg.zzd(message);
    }
}


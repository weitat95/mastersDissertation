/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 */
package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Message;
import com.google.android.gms.tagmanager.zzfn;
import com.google.android.gms.tagmanager.zzfo;
import com.google.android.gms.tagmanager.zzfs;

final class zzft
implements Handler.Callback {
    private /* synthetic */ zzfs zzkjx;

    zzft(zzfs zzfs2) {
        this.zzkjx = zzfs2;
    }

    public final boolean handleMessage(Message message) {
        if (1 == message.what && zzfo.zzbgi().equals(message.obj)) {
            ((zzfn)this.zzkjx.zzkjw).dispatch();
            if (!zzfo.zzb(this.zzkjx.zzkjw)) {
                this.zzkjx.zzs(zzfo.zzc(this.zzkjx.zzkjw));
            }
        }
        return true;
    }
}


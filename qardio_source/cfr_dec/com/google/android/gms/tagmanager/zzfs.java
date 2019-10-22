/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.tagmanager.zzfo;
import com.google.android.gms.tagmanager.zzfp;
import com.google.android.gms.tagmanager.zzfr;
import com.google.android.gms.tagmanager.zzft;

final class zzfs
implements zzfr {
    private Handler handler;
    final /* synthetic */ zzfo zzkjw;

    private zzfs(zzfo zzfo2) {
        this.zzkjw = zzfo2;
        this.handler = new Handler(zzfo.zza(this.zzkjw).getMainLooper(), (Handler.Callback)new zzft(this));
    }

    /* synthetic */ zzfs(zzfo zzfo2, zzfp zzfp2) {
        this(zzfo2);
    }

    private final Message obtainMessage() {
        return this.handler.obtainMessage(1, zzfo.zzbgi());
    }

    @Override
    public final void cancel() {
        this.handler.removeMessages(1, zzfo.zzbgi());
    }

    @Override
    public final void zzbgj() {
        this.handler.removeMessages(1, zzfo.zzbgi());
        this.handler.sendMessage(this.obtainMessage());
    }

    @Override
    public final void zzs(long l) {
        this.handler.removeMessages(1, zzfo.zzbgi());
        this.handler.sendMessageDelayed(this.obtainMessage(), l);
    }
}


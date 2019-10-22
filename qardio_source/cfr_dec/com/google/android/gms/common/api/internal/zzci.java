/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.api.internal.zzcj;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.common.internal.zzbq;

public final class zzci<L> {
    private final zzcj zzfuj;
    private volatile L zzfuk;
    private final zzck<L> zzful;

    zzci(Looper looper, L l, String string2) {
        this.zzfuj = new zzcj(this, looper);
        this.zzfuk = zzbq.checkNotNull(l, "Listener must not be null");
        this.zzful = new zzck<L>(l, zzbq.zzgm(string2));
    }

    public final void clear() {
        this.zzfuk = null;
    }

    public final void zza(zzcl<? super L> message) {
        zzbq.checkNotNull(message, "Notifier must not be null");
        message = this.zzfuj.obtainMessage(1, message);
        this.zzfuj.sendMessage(message);
    }

    public final zzck<L> zzajo() {
        return this.zzful;
    }

    final void zzb(zzcl<? super L> zzcl2) {
        L l = this.zzfuk;
        if (l == null) {
            zzcl2.zzahz();
            return;
        }
        try {
            zzcl2.zzu(l);
            return;
        }
        catch (RuntimeException runtimeException) {
            zzcl2.zzahz();
            throw runtimeException;
        }
    }
}


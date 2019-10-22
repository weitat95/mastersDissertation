/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzao;
import com.google.android.gms.common.api.internal.zzap;

abstract class zzay
implements Runnable {
    private /* synthetic */ zzao zzfrl;

    private zzay(zzao zzao2) {
        this.zzfrl = zzao2;
    }

    /* synthetic */ zzay(zzao zzao2, zzap zzap2) {
        this(zzao2);
    }

    @Override
    public void run() {
        block6: {
            zzao.zzc(this.zzfrl).lock();
            boolean bl = Thread.interrupted();
            if (!bl) break block6;
            zzao.zzc(this.zzfrl).unlock();
            return;
        }
        try {
            this.zzaib();
        }
        catch (RuntimeException runtimeException) {
            zzao.zzd(this.zzfrl).zza(runtimeException);
            return;
        }
        finally {
            zzao.zzc(this.zzfrl).unlock();
        }
        zzao.zzc(this.zzfrl).unlock();
        return;
    }

    protected abstract void zzaib();
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzbh;
import com.google.android.gms.common.api.internal.zzbi;

abstract class zzbj {
    private final zzbh zzfsv;

    protected zzbj(zzbh zzbh2) {
        this.zzfsv = zzbh2;
    }

    protected abstract void zzaib();

    public final void zzc(zzbi zzbi2) {
        block4: {
            zzbi.zza(zzbi2).lock();
            zzbh zzbh2 = zzbi.zzb(zzbi2);
            zzbh zzbh3 = this.zzfsv;
            if (zzbh2 == zzbh3) break block4;
            zzbi.zza(zzbi2).unlock();
            return;
        }
        try {
            this.zzaib();
            return;
        }
        finally {
            zzbi.zza(zzbi2).unlock();
        }
    }
}


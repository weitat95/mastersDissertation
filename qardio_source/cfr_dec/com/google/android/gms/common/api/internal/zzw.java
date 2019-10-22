/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzv;

final class zzw
implements Runnable {
    private /* synthetic */ zzv zzfpu;

    @Override
    public final void run() {
        zzv.zza(this.zzfpu).lock();
        try {
            zzv.zzb(this.zzfpu);
            return;
        }
        finally {
            zzv.zza(this.zzfpu).unlock();
        }
    }
}


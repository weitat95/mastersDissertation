/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.dynamic;

import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zza;
import com.google.android.gms.dynamic.zzi;

final class zzg
implements zzi {
    private /* synthetic */ zza zzgwh;

    zzg(zza zza2) {
        this.zzgwh = zza2;
    }

    @Override
    public final int getState() {
        return 4;
    }

    @Override
    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        zza.zzb(this.zzgwh).onStart();
    }
}


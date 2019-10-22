/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.dynamic;

import android.os.Bundle;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zza;
import com.google.android.gms.dynamic.zzi;

final class zzd
implements zzi {
    private /* synthetic */ Bundle zzail;
    private /* synthetic */ zza zzgwh;

    zzd(zza zza2, Bundle bundle) {
        this.zzgwh = zza2;
        this.zzail = bundle;
    }

    @Override
    public final int getState() {
        return 1;
    }

    @Override
    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        zza.zzb(this.zzgwh).onCreate(this.zzail);
    }
}


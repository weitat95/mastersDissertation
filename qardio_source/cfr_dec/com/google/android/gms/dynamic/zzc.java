/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 */
package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zza;
import com.google.android.gms.dynamic.zzi;

final class zzc
implements zzi {
    private /* synthetic */ Activity val$activity;
    private /* synthetic */ Bundle zzail;
    private /* synthetic */ zza zzgwh;
    private /* synthetic */ Bundle zzgwi;

    zzc(zza zza2, Activity activity, Bundle bundle, Bundle bundle2) {
        this.zzgwh = zza2;
        this.val$activity = activity;
        this.zzgwi = bundle;
        this.zzail = bundle2;
    }

    @Override
    public final int getState() {
        return 0;
    }

    @Override
    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        zza.zzb(this.zzgwh).onInflate(this.val$activity, this.zzgwi, this.zzail);
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzdb;

final class zzdc
implements Runnable {
    private /* synthetic */ String zzat;
    private /* synthetic */ LifecycleCallback zzfuh;
    private /* synthetic */ zzdb zzfuw;

    zzdc(zzdb zzdb2, LifecycleCallback lifecycleCallback, String string2) {
        this.zzfuw = zzdb2;
        this.zzfuh = lifecycleCallback;
        this.zzat = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void run() {
        if (zzdb.zza(this.zzfuw) > 0) {
            LifecycleCallback lifecycleCallback = this.zzfuh;
            Bundle bundle = zzdb.zzb(this.zzfuw) != null ? zzdb.zzb(this.zzfuw).getBundle(this.zzat) : null;
            lifecycleCallback.onCreate(bundle);
        }
        if (zzdb.zza(this.zzfuw) >= 2) {
            this.zzfuh.onStart();
        }
        if (zzdb.zza(this.zzfuw) >= 3) {
            this.zzfuh.onResume();
        }
        if (zzdb.zza(this.zzfuw) >= 4) {
            this.zzfuh.onStop();
        }
        if (zzdb.zza(this.zzfuw) >= 5) {
            this.zzfuh.onDestroy();
        }
    }
}


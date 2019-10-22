/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzcg;

final class zzch
implements Runnable {
    private /* synthetic */ String zzat;
    private /* synthetic */ LifecycleCallback zzfuh;
    private /* synthetic */ zzcg zzfui;

    zzch(zzcg zzcg2, LifecycleCallback lifecycleCallback, String string2) {
        this.zzfui = zzcg2;
        this.zzfuh = lifecycleCallback;
        this.zzat = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void run() {
        if (zzcg.zza(this.zzfui) > 0) {
            LifecycleCallback lifecycleCallback = this.zzfuh;
            Bundle bundle = zzcg.zzb(this.zzfui) != null ? zzcg.zzb(this.zzfui).getBundle(this.zzat) : null;
            lifecycleCallback.onCreate(bundle);
        }
        if (zzcg.zza(this.zzfui) >= 2) {
            this.zzfuh.onStart();
        }
        if (zzcg.zza(this.zzfui) >= 3) {
            this.zzfuh.onResume();
        }
        if (zzcg.zza(this.zzfui) >= 4) {
            this.zzfuh.onStop();
        }
        if (zzcg.zza(this.zzfui) >= 5) {
            this.zzfuh.onDestroy();
        }
    }
}


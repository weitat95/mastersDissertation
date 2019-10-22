/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Looper;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.internal.zzarf;

final class zzarg
implements Runnable {
    private /* synthetic */ zzarf zzdvr;

    zzarg(zzarf zzarf2) {
        this.zzdvr = zzarf2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void run() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            zzarf.zza(this.zzdvr).zzwv().zzc(this);
            return;
        } else {
            boolean bl = this.zzdvr.zzdx();
            zzarf.zza(this.zzdvr, 0L);
            if (!bl) return;
            {
                this.zzdvr.run();
                return;
            }
        }
    }
}


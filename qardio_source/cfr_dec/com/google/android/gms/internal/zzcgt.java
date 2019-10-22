/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Looper;
import com.google.android.gms.internal.zzcgs;
import com.google.android.gms.internal.zzcih;

final class zzcgt
implements Runnable {
    private /* synthetic */ zzcgs zzize;

    zzcgt(zzcgs zzcgs2) {
        this.zzize = zzcgs2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void run() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            zzcgs.zza(this.zzize).zzawx().zzg(this);
            return;
        } else {
            boolean bl = this.zzize.zzdx();
            zzcgs.zza(this.zzize, 0L);
            if (!bl || !zzcgs.zzb(this.zzize)) return;
            {
                this.zzize.run();
                return;
            }
        }
    }
}


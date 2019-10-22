/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzbx;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzec;
import com.google.android.gms.tagmanager.zzfx;

final class zzed
implements zzfx {
    private /* synthetic */ zzec zzkhh;

    zzed(zzec zzec2) {
        this.zzkhh = zzec2;
    }

    @Override
    public final void zza(zzbx zzbx2) {
        zzec.zza(this.zzkhh, zzbx2.zzbey());
    }

    @Override
    public final void zzb(zzbx zzbx2) {
        zzec.zza(this.zzkhh, zzbx2.zzbey());
        long l = zzbx2.zzbey();
        zzdj.v(new StringBuilder(57).append("Permanent failure dispatching hitId: ").append(l).toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void zzc(zzbx zzbx2) {
        long l = zzbx2.zzbez();
        if (l == 0L) {
            zzec.zza(this.zzkhh, zzbx2.zzbey(), zzec.zza(this.zzkhh).currentTimeMillis());
            return;
        } else {
            if (l + 14400000L >= zzec.zza(this.zzkhh).currentTimeMillis()) return;
            {
                zzec.zza(this.zzkhh, zzbx2.zzbey());
                l = zzbx2.zzbey();
                zzdj.v(new StringBuilder(47).append("Giving up on failed hitId: ").append(l).toString());
                return;
            }
        }
    }
}


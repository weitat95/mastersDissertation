/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.tagmanager.zzbz;
import com.google.android.gms.tagmanager.zzca;
import com.google.android.gms.tagmanager.zzcc;
import com.google.android.gms.tagmanager.zzfo;

final class zzcb
implements Runnable {
    private /* synthetic */ String zzcml;
    private /* synthetic */ zzbz zzkgb;
    private /* synthetic */ long zzkgc;
    private /* synthetic */ zzca zzkgd;

    zzcb(zzca zzca2, zzbz zzbz2, long l, String string2) {
        this.zzkgd = zzca2;
        this.zzkgb = zzbz2;
        this.zzkgc = l;
        this.zzcml = string2;
    }

    @Override
    public final void run() {
        if (zzca.zza(this.zzkgd) == null) {
            zzfo zzfo2 = zzfo.zzbgg();
            zzfo2.zza(zzca.zzb(this.zzkgd), this.zzkgb);
            zzca.zza(this.zzkgd, zzfo2.zzbgh());
        }
        zzca.zza(this.zzkgd).zzb(this.zzkgc, this.zzcml);
    }
}


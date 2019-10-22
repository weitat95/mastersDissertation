/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgd;

final class zzcge
implements Runnable {
    private /* synthetic */ String zzbfa;
    private /* synthetic */ long zziwu;
    private /* synthetic */ zzcgd zziwv;

    zzcge(zzcgd zzcgd2, String string2, long l) {
        this.zziwv = zzcgd2;
        this.zzbfa = string2;
        this.zziwu = l;
    }

    @Override
    public final void run() {
        zzcgd.zza(this.zziwv, this.zzbfa, this.zziwu);
    }
}


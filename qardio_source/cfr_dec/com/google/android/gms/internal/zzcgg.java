/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgd;

final class zzcgg
implements Runnable {
    private /* synthetic */ long zziwu;
    private /* synthetic */ zzcgd zziwv;

    zzcgg(zzcgd zzcgd2, long l) {
        this.zziwv = zzcgd2;
        this.zziwu = l;
    }

    @Override
    public final void run() {
        zzcgd.zza(this.zziwv, this.zziwu);
    }
}


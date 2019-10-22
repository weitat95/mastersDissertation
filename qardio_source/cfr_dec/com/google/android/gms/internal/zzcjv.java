/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.internal.zzcjn;

final class zzcjv
implements Runnable {
    private /* synthetic */ String val$name;
    private /* synthetic */ String zzimf;
    private /* synthetic */ String zzjgq;
    private /* synthetic */ zzcjn zzjhc;
    private /* synthetic */ long zzjhh;
    private /* synthetic */ Bundle zzjhi;
    private /* synthetic */ boolean zzjhj;
    private /* synthetic */ boolean zzjhk;
    private /* synthetic */ boolean zzjhl;

    zzcjv(zzcjn zzcjn2, String string2, String string3, long l, Bundle bundle, boolean bl, boolean bl2, boolean bl3, String string4) {
        this.zzjhc = zzcjn2;
        this.zzjgq = string2;
        this.val$name = string3;
        this.zzjhh = l;
        this.zzjhi = bundle;
        this.zzjhj = bl;
        this.zzjhk = bl2;
        this.zzjhl = bl3;
        this.zzimf = string4;
    }

    @Override
    public final void run() {
        zzcjn.zza(this.zzjhc, this.zzjgq, this.val$name, this.zzjhh, this.zzjhi, this.zzjhj, this.zzjhk, this.zzjhl, this.zzimf);
    }
}


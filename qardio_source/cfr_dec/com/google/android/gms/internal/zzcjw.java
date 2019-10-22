/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcjn;

final class zzcjw
implements Runnable {
    private /* synthetic */ String val$name;
    private /* synthetic */ String zzjgq;
    private /* synthetic */ zzcjn zzjhc;
    private /* synthetic */ long zzjhh;
    private /* synthetic */ Object zzjhm;

    zzcjw(zzcjn zzcjn2, String string2, String string3, Object object, long l) {
        this.zzjhc = zzcjn2;
        this.zzjgq = string2;
        this.val$name = string3;
        this.zzjhm = object;
        this.zzjhh = l;
    }

    @Override
    public final void run() {
        zzcjn.zza(this.zzjhc, this.zzjgq, this.val$name, this.zzjhm, this.zzjhh);
    }
}


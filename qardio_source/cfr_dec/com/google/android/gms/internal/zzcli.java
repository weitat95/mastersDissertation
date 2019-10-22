/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzclf;

final class zzcli
implements Runnable {
    private /* synthetic */ long zziwu;
    private /* synthetic */ zzclf zzjjf;

    zzcli(zzclf zzclf2, long l) {
        this.zzjjf = zzclf2;
        this.zziwu = l;
    }

    @Override
    public final void run() {
        zzclf.zza(this.zzjjf, this.zziwu);
    }
}


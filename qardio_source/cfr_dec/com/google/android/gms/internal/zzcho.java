/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzchm;

public final class zzcho {
    private final int mPriority;
    private /* synthetic */ zzchm zzjce;
    private final boolean zzjcf;
    private final boolean zzjcg;

    zzcho(zzchm zzchm2, int n, boolean bl, boolean bl2) {
        this.zzjce = zzchm2;
        this.mPriority = n;
        this.zzjcf = bl;
        this.zzjcg = bl2;
    }

    public final void log(String string2) {
        this.zzjce.zza(this.mPriority, this.zzjcf, this.zzjcg, string2, null, null, null);
    }

    public final void zzd(String string2, Object object, Object object2, Object object3) {
        this.zzjce.zza(this.mPriority, this.zzjcf, this.zzjcg, string2, object, object2, object3);
    }

    public final void zze(String string2, Object object, Object object2) {
        this.zzjce.zza(this.mPriority, this.zzjcf, this.zzjcg, string2, object, object2, null);
    }

    public final void zzj(String string2, Object object) {
        this.zzjce.zza(this.mPriority, this.zzjcf, this.zzjcg, string2, object, null, null);
    }
}


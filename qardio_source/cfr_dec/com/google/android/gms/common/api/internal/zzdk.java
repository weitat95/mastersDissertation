/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzdj;
import com.google.android.gms.common.api.internal.zzdm;
import java.util.Set;

final class zzdk
implements zzdm {
    private /* synthetic */ zzdj zzfvk;

    zzdk(zzdj zzdj2) {
        this.zzfvk = zzdj2;
    }

    @Override
    public final void zzc(BasePendingResult<?> basePendingResult) {
        this.zzfvk.zzfvi.remove(basePendingResult);
    }
}


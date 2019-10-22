/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.internal.zzbxx;
import com.google.android.gms.internal.zzbzj;

final class zzbzo
extends zzbxx {
    private final zzn<ListSubscriptionsResult> zzgbw;

    private zzbzo(zzn<ListSubscriptionsResult> zzn2) {
        this.zzgbw = zzn2;
    }

    /* synthetic */ zzbzo(zzn zzn2, zzbzj zzbzj2) {
        this(zzn2);
    }

    @Override
    public final void zza(ListSubscriptionsResult listSubscriptionsResult) {
        this.zzgbw.setResult(listSubscriptionsResult);
    }
}


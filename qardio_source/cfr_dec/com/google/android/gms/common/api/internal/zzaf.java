/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzae;

final class zzaf
implements PendingResult.zza {
    private /* synthetic */ BasePendingResult zzfqq;
    private /* synthetic */ zzae zzfqr;

    zzaf(zzae zzae2, BasePendingResult basePendingResult) {
        this.zzfqr = zzae2;
        this.zzfqq = basePendingResult;
    }

    @Override
    public final void zzr(Status status) {
        zzae.zza(this.zzfqr).remove(this.zzfqq);
    }
}


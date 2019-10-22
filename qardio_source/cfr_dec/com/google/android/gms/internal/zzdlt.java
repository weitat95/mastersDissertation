/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzdlq;

final class zzdlt
extends zzdlq {
    private final zzn<BooleanResult> zzgbw;

    public zzdlt(zzn<BooleanResult> zzn2) {
        this.zzgbw = zzn2;
    }

    @Override
    public final void zza(Status status, boolean bl, Bundle bundle) {
        this.zzgbw.setResult(new BooleanResult(status, bl));
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.internal.zza;

class zzgm<T>
extends zza {
    private zzn<T> zzeay;

    public zzgm(zzn<T> zzn2) {
        this.zzeay = zzn2;
    }

    public final void zzav(T t) {
        zzn<T> zzn2 = this.zzeay;
        if (zzn2 != null) {
            zzn2.setResult(t);
            this.zzeay = null;
        }
    }
}


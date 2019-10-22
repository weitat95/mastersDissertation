/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzao;
import com.google.android.gms.common.api.internal.zzaw;
import com.google.android.gms.common.api.internal.zzbh;
import com.google.android.gms.common.api.internal.zzbj;
import com.google.android.gms.internal.zzcxi;
import com.google.android.gms.internal.zzcxq;
import java.lang.ref.WeakReference;

final class zzav
extends zzcxi {
    private final WeakReference<zzao> zzfrm;

    zzav(zzao zzao2) {
        this.zzfrm = new WeakReference<zzao>(zzao2);
    }

    @Override
    public final void zzb(zzcxq zzcxq2) {
        zzao zzao2 = (zzao)this.zzfrm.get();
        if (zzao2 == null) {
            return;
        }
        zzao.zzd(zzao2).zza(new zzaw(this, zzao2, zzao2, zzcxq2));
    }
}


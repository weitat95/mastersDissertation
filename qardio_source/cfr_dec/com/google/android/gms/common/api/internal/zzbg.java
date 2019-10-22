/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzby;
import java.lang.ref.WeakReference;

final class zzbg
extends zzby {
    private WeakReference<zzba> zzfsn;

    zzbg(zzba zzba2) {
        this.zzfsn = new WeakReference<zzba>(zzba2);
    }

    @Override
    public final void zzahg() {
        zzba zzba2 = (zzba)this.zzfsn.get();
        if (zzba2 == null) {
            return;
        }
        zzba.zza(zzba2);
    }
}


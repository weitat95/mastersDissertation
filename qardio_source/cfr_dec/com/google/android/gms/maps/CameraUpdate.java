/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.maps;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class CameraUpdate {
    private final IObjectWrapper zziqw;

    CameraUpdate(IObjectWrapper iObjectWrapper) {
        this.zziqw = zzbq.checkNotNull(iObjectWrapper);
    }

    public final IObjectWrapper zzavz() {
        return this.zziqw;
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.internal.zzah;

final class zzho
implements zzcl<CapabilityApi.CapabilityListener> {
    private /* synthetic */ zzah zzlmc;

    zzho(zzah zzah2) {
        this.zzlmc = zzah2;
    }

    @Override
    public final void zzahz() {
    }

    @Override
    public final /* synthetic */ void zzu(Object object) {
        ((CapabilityApi.CapabilityListener)object).onCapabilityChanged(this.zzlmc);
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.internal.zzaw;

final class zzhn
implements zzcl<ChannelApi.ChannelListener> {
    private /* synthetic */ zzaw zzlhv;

    zzhn(zzaw zzaw2) {
        this.zzlhv = zzaw2;
    }

    @Override
    public final void zzahz() {
    }

    @Override
    public final /* synthetic */ void zzu(Object object) {
        object = (ChannelApi.ChannelListener)object;
        this.zzlhv.zza((ChannelApi.ChannelListener)object);
    }
}


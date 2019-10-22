/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.ChannelClient;
import com.google.android.gms.wearable.internal.zzao;

public final class zzas
implements ChannelApi.ChannelListener {
    private final ChannelClient.ChannelCallback zzliz;

    public zzas(ChannelClient.ChannelCallback channelCallback) {
        this.zzliz = channelCallback;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        object = (zzas)object;
        return this.zzliz.equals(((zzas)object).zzliz);
    }

    public final int hashCode() {
        return this.zzliz.hashCode();
    }

    @Override
    public final void onChannelClosed(Channel channel, int n, int n2) {
        this.zzliz.onChannelClosed(zzao.zzb(channel), n, n2);
    }

    @Override
    public final void onChannelOpened(Channel channel) {
        this.zzliz.onChannelOpened(zzao.zzb(channel));
    }

    @Override
    public final void onInputClosed(Channel channel, int n, int n2) {
        this.zzliz.onInputClosed(zzao.zzb(channel), n, n2);
    }

    @Override
    public final void onOutputClosed(Channel channel, int n, int n2) {
        this.zzliz.onOutputClosed(zzao.zzb(channel), n, n2);
    }
}


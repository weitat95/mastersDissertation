/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelClient;
import com.google.android.gms.wearable.internal.zzay;

public final class zzao
extends ChannelClient {
    private static zzay zza(Channel channel) {
        zzbq.checkNotNull(channel, "channel must not be null");
        return (zzay)channel;
    }

    static /* synthetic */ zzay zzb(Channel channel) {
        return zzao.zza(channel);
    }
}


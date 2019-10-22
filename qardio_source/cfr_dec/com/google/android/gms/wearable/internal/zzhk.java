/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.internal.zzah;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.internal.zzen;
import com.google.android.gms.wearable.internal.zzfe;
import com.google.android.gms.wearable.internal.zzfo;
import com.google.android.gms.wearable.internal.zzhl;
import com.google.android.gms.wearable.internal.zzhm;
import com.google.android.gms.wearable.internal.zzhn;
import com.google.android.gms.wearable.internal.zzho;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.internal.zzl;
import java.util.List;

public final class zzhk<T>
extends zzen {
    private final IntentFilter[] zzlkt;
    private zzci<DataApi.DataListener> zzllv;
    private zzci<MessageApi.MessageListener> zzllw;
    private zzci<ChannelApi.ChannelListener> zzllz;
    private zzci<CapabilityApi.CapabilityListener> zzlma;
    private final String zzlmb;

    @Override
    public final void onConnectedNodes(List<zzfo> list) {
    }

    @Override
    public final void zza(zzah zzah2) {
        if (this.zzlma != null) {
            this.zzlma.zza(new zzho(zzah2));
        }
    }

    @Override
    public final void zza(zzaw zzaw2) {
        if (this.zzllz != null) {
            this.zzllz.zza(new zzhn(zzaw2));
        }
    }

    @Override
    public final void zza(zzfe zzfe2) {
        if (this.zzllw != null) {
            this.zzllw.zza(new zzhm(zzfe2));
        }
    }

    @Override
    public final void zza(zzfo zzfo2) {
    }

    @Override
    public final void zza(zzi zzi2) {
    }

    @Override
    public final void zza(zzl zzl2) {
    }

    @Override
    public final void zzas(DataHolder dataHolder) {
        if (this.zzllv != null) {
            this.zzllv.zza(new zzhl(dataHolder));
            return;
        }
        dataHolder.close();
    }

    @Override
    public final void zzb(zzfo zzfo2) {
    }

    public final IntentFilter[] zzbkg() {
        return this.zzlkt;
    }

    public final String zzbkh() {
        return this.zzlmb;
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.internal.zzey;
import com.google.android.gms.wearable.internal.zzga;
import com.google.android.gms.wearable.internal.zzgd;
import com.google.android.gms.wearable.internal.zzgm;

final class zzhe
extends zzgm<MessageApi.SendMessageResult> {
    public zzhe(zzn<MessageApi.SendMessageResult> zzn2) {
        super(zzn2);
    }

    @Override
    public final void zza(zzga zzga2) {
        this.zzav(new zzey(zzgd.zzdg(zzga2.statusCode), zzga2.zzift));
    }
}


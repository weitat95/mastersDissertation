/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.internal.zzch;
import com.google.android.gms.wearable.internal.zzdg;
import com.google.android.gms.wearable.internal.zzgd;
import com.google.android.gms.wearable.internal.zzgm;

final class zzgp
extends zzgm<DataApi.DeleteDataItemsResult> {
    public zzgp(zzn<DataApi.DeleteDataItemsResult> zzn2) {
        super(zzn2);
    }

    @Override
    public final void zza(zzdg zzdg2) {
        this.zzav(new zzch(zzgd.zzdg(zzdg2.statusCode), zzdg2.zzlkd));
    }
}


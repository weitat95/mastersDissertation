/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.internal.zzcg;
import com.google.android.gms.wearable.internal.zzdd;
import com.google.android.gms.wearable.internal.zzfu;
import com.google.android.gms.wearable.internal.zzgd;
import com.google.android.gms.wearable.internal.zzgm;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.FutureTask;

final class zzhb
extends zzgm<DataApi.DataItemResult> {
    private final List<FutureTask<Boolean>> zzllg;

    zzhb(zzn<DataApi.DataItemResult> zzn2, List<FutureTask<Boolean>> list) {
        super(zzn2);
        this.zzllg = list;
    }

    @Override
    public final void zza(zzfu object) {
        this.zzav(new zzcg(zzgd.zzdg(((zzfu)object).statusCode), ((zzfu)object).zzlkn));
        if (((zzfu)object).statusCode != 0) {
            object = this.zzllg.iterator();
            while (object.hasNext()) {
                ((FutureTask)object.next()).cancel(true);
            }
        }
    }
}


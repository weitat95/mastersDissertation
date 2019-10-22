/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.fitness.data;

import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.data.zzg;
import com.google.android.gms.internal.zzbuv;
import com.google.android.gms.internal.zzbuw;
import java.util.concurrent.TimeUnit;

final class zzf
implements zzbuv<DataPoint, DataType> {
    public static final zzf zzhae = new zzf();

    private zzf() {
    }

    @Override
    public final /* synthetic */ long zza(Object object, TimeUnit timeUnit) {
        object = (DataPoint)object;
        return ((DataPoint)object).getEndTime(timeUnit) - ((DataPoint)object).getStartTime(timeUnit);
    }

    @Override
    public final /* synthetic */ Object zzaa(Object object) {
        return ((DataPoint)object).getDataType();
    }

    @Override
    public final /* synthetic */ String zzab(Object object) {
        return ((DataPoint)object).getDataType().getName();
    }

    @Override
    public final zzbuw<DataType> zzaqe() {
        return zzg.zzhaf;
    }

    @Override
    public final /* synthetic */ double zzb(Object object, int n) {
        return ((DataPoint)object).zzdb(n).asFloat();
    }

    @Override
    public final /* synthetic */ int zzc(Object object, int n) {
        return ((DataPoint)object).zzdb(n).asInt();
    }

    @Override
    public final /* synthetic */ boolean zzd(Object object, int n) {
        return ((DataPoint)object).zzdb(n).isSet();
    }
}


/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.fitness.data;

import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.zzm;
import com.google.android.gms.internal.zzbuw;
import java.util.List;

final class zzg
implements zzbuw<DataType> {
    public static final zzg zzhaf = new zzg();

    private zzg() {
    }

    private static Field zza(DataType dataType, int n) {
        return dataType.getFields().get(n);
    }

    @Override
    public final /* synthetic */ int zzac(Object object) {
        return ((DataType)object).getFields().size();
    }

    @Override
    public final /* synthetic */ String zzad(Object object) {
        return ((DataType)object).getName();
    }

    @Override
    public final /* synthetic */ int zze(Object object, int n) {
        return zzg.zza((DataType)object, n).getFormat();
    }

    @Override
    public final /* synthetic */ boolean zzf(Object object, int n) {
        object = (DataType)object;
        return Boolean.TRUE.equals(zzg.zza((DataType)object, n).isOptional());
    }

    @Override
    public final /* synthetic */ String zzg(Object object, int n) {
        return zzg.zza((DataType)object, n).getName();
    }

    @Override
    public final boolean zzhe(String string2) {
        return zzm.zzhf(string2) != null;
    }
}


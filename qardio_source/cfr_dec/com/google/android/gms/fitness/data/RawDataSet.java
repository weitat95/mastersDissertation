/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.RawDataPoint;
import com.google.android.gms.fitness.data.zzaa;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbuu;
import java.util.Arrays;
import java.util.List;

@KeepName
public final class RawDataSet
extends zzbfm {
    public static final Parcelable.Creator<RawDataSet> CREATOR = new zzaa();
    private int zzeck;
    public final boolean zzhad;
    public final int zzhdo;
    private int zzhds;
    public final List<RawDataPoint> zzhdt;

    public RawDataSet(int n, int n2, int n3, List<RawDataPoint> list, boolean bl) {
        this.zzeck = n;
        this.zzhdo = n2;
        this.zzhds = n3;
        this.zzhdt = list;
        this.zzhad = bl;
    }

    public RawDataSet(DataSet dataSet, List<DataSource> list, List<DataType> list2) {
        this.zzeck = 3;
        this.zzhdt = dataSet.zzab(list);
        this.zzhad = dataSet.zzaqd();
        this.zzhdo = zzbuu.zza(dataSet.getDataSource(), list);
        this.zzhds = zzbuu.zza(dataSet.getDataType(), list2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof RawDataSet)) return bl2;
        object = (RawDataSet)object;
        boolean bl3 = this.zzhdo == ((RawDataSet)object).zzhdo && this.zzhad == ((RawDataSet)object).zzhad && zzbg.equal(this.zzhdt, ((RawDataSet)object).zzhdt);
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhdo});
    }

    public final String toString() {
        return String.format("RawDataSet{%s@[%s]}", this.zzhdo, this.zzhdt);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzhdo);
        zzbfp.zzc(parcel, 2, this.zzhds);
        zzbfp.zzc(parcel, 3, this.zzhdt, false);
        zzbfp.zza(parcel, 4, this.zzhad);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n);
    }
}


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
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.data.zzz;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbuu;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@KeepName
public final class RawDataPoint
extends zzbfm {
    public static final Parcelable.Creator<RawDataPoint> CREATOR = new zzz();
    private int versionCode;
    public final long zzhdl;
    public final long zzhdm;
    public final Value[] zzhdn;
    public final int zzhdo;
    public final int zzhdp;
    public final long zzhdq;
    public final long zzhdr;

    public RawDataPoint(int n, long l, long l2, Value[] arrvalue, int n2, int n3, long l3, long l4) {
        this.versionCode = n;
        this.zzhdl = l;
        this.zzhdm = l2;
        this.zzhdo = n2;
        this.zzhdp = n3;
        this.zzhdq = l3;
        this.zzhdr = l4;
        this.zzhdn = arrvalue;
    }

    RawDataPoint(DataPoint dataPoint, List<DataSource> list) {
        this.versionCode = 4;
        this.zzhdl = dataPoint.getTimestamp(TimeUnit.NANOSECONDS);
        this.zzhdm = dataPoint.getStartTime(TimeUnit.NANOSECONDS);
        this.zzhdn = dataPoint.zzaqf();
        this.zzhdo = zzbuu.zza(dataPoint.getDataSource(), list);
        this.zzhdp = zzbuu.zza(dataPoint.zzaqg(), list);
        this.zzhdq = dataPoint.zzaqh();
        this.zzhdr = dataPoint.zzaqi();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof RawDataPoint)) return bl2;
        object = (RawDataPoint)object;
        boolean bl3 = this.zzhdl == ((RawDataPoint)object).zzhdl && this.zzhdm == ((RawDataPoint)object).zzhdm && Arrays.equals(this.zzhdn, ((RawDataPoint)object).zzhdn) && this.zzhdo == ((RawDataPoint)object).zzhdo && this.zzhdp == ((RawDataPoint)object).zzhdp && this.zzhdq == ((RawDataPoint)object).zzhdq;
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhdl, this.zzhdm});
    }

    public final String toString() {
        return String.format("RawDataPoint{%s@[%s, %s](%d,%d)}", Arrays.toString(this.zzhdn), this.zzhdm, this.zzhdl, this.zzhdo, this.zzhdp);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhdl);
        zzbfp.zza(parcel, 2, this.zzhdm);
        zzbfp.zza((Parcel)parcel, (int)3, (Parcelable[])this.zzhdn, (int)n, (boolean)false);
        zzbfp.zzc(parcel, 4, this.zzhdo);
        zzbfp.zzc(parcel, 5, this.zzhdp);
        zzbfp.zza(parcel, 6, this.zzhdq);
        zzbfp.zza(parcel, 7, this.zzhdr);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, n2);
    }
}


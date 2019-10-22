/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.RawDataPoint;
import com.google.android.gms.fitness.data.RawDataSet;
import com.google.android.gms.fitness.data.zzf;
import com.google.android.gms.fitness.data.zzi;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbuy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class DataSet
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<DataSet> CREATOR = new zzi();
    private final int versionCode;
    private final DataType zzgzg;
    private final DataSource zzgzh;
    private final List<DataPoint> zzham;
    private final List<DataSource> zzhan;
    private boolean zzhao = false;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    DataSet(int n, DataSource iterator, DataType zzbfm2, List<RawDataPoint> list, List<DataSource> list2, boolean bl) {
        List list3;
        void var4_6;
        void var6_8;
        this.versionCode = n;
        this.zzgzh = iterator;
        this.zzgzg = ((DataSource)((Object)iterator)).getDataType();
        this.zzhao = var6_8;
        this.zzham = new ArrayList<DataPoint>(var4_6.size());
        if (n < 2) {
            list3 = Collections.singletonList(iterator);
        }
        this.zzhan = list3;
        iterator = var4_6.iterator();
        while (iterator.hasNext()) {
            RawDataPoint rawDataPoint = (RawDataPoint)iterator.next();
            this.zzham.add(new DataPoint(this.zzhan, rawDataPoint));
        }
        return;
    }

    private DataSet(DataSource dataSource) {
        this.versionCode = 3;
        this.zzgzh = zzbq.checkNotNull(dataSource);
        this.zzgzg = dataSource.getDataType();
        this.zzham = new ArrayList<DataPoint>();
        this.zzhan = new ArrayList<DataSource>();
        this.zzhan.add(this.zzgzh);
    }

    /*
     * Enabled aggressive block sorting
     */
    public DataSet(RawDataSet iterator, List<DataSource> object) {
        this.versionCode = 3;
        int n = ((RawDataSet)iterator).zzhdo;
        Object var4_4 = n >= 0 && n < object.size() ? object.get(n) : null;
        this.zzgzh = var4_4;
        this.zzgzg = this.zzgzh.getDataType();
        this.zzhan = object;
        this.zzhao = ((RawDataSet)iterator).zzhad;
        iterator = ((RawDataSet)iterator).zzhdt;
        this.zzham = new ArrayList<DataPoint>(iterator.size());
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            object = (RawDataPoint)iterator.next();
            this.zzham.add(new DataPoint(this.zzhan, (RawDataPoint)object));
        }
        return;
    }

    public static DataSet create(DataSource dataSource) {
        zzbq.checkNotNull(dataSource, "DataSource should be specified");
        return new DataSet(dataSource);
    }

    private final void zza(DataPoint zzbfm2) {
        this.zzham.add((DataPoint)zzbfm2);
        zzbfm2 = zzbfm2.getOriginalDataSource();
        if (zzbfm2 != null && !this.zzhan.contains(zzbfm2)) {
            this.zzhan.add((DataSource)zzbfm2);
        }
    }

    private List<RawDataPoint> zzaqk() {
        return this.zzab(this.zzhan);
    }

    public static void zzb(DataPoint object) throws IllegalArgumentException {
        String string2 = zzbuy.zza(object, zzf.zzhae);
        if (string2 != null) {
            object = String.valueOf(object);
            Log.w((String)"Fitness", (String)new StringBuilder(String.valueOf(object).length() + 20).append("Invalid data point: ").append((String)object).toString());
            throw new IllegalArgumentException(string2);
        }
    }

    public final void add(DataPoint dataPoint) {
        DataSource dataSource = dataPoint.getDataSource();
        zzbq.zzb(dataSource.getStreamIdentifier().equals(this.zzgzh.getStreamIdentifier()), "Conflicting data sources found %s vs %s", dataSource, this.zzgzh);
        dataPoint.zzaqj();
        DataSet.zzb(dataPoint);
        this.zza(dataPoint);
    }

    public final DataPoint createDataPoint() {
        return DataPoint.create(this.zzgzh);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        boolean bl = false;
        if (object == this) return true;
        boolean bl2 = bl;
        if (!(object instanceof DataSet)) return bl2;
        object = (DataSet)object;
        boolean bl3 = zzbg.equal(this.getDataType(), ((DataSet)object).getDataType()) && zzbg.equal(this.zzgzh, ((DataSet)object).zzgzh) && zzbg.equal(this.zzham, ((DataSet)object).zzham) && this.zzhao == ((DataSet)object).zzhao;
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public final List<DataPoint> getDataPoints() {
        return Collections.unmodifiableList(this.zzham);
    }

    public final DataSource getDataSource() {
        return this.zzgzh;
    }

    public final DataType getDataType() {
        return this.zzgzh.getDataType();
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzgzh});
    }

    public final boolean isEmpty() {
        return this.zzham.isEmpty();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final String toString() {
        Object object = this.zzaqk();
        String string2 = this.zzgzh.toDebugString();
        if (this.zzham.size() < 10) {
            do {
                return String.format("DataSet{%s %s}", string2, object);
                break;
            } while (true);
        }
        object = String.format("%d data points, first 5: %s", this.zzham.size(), object.subList(0, 5));
        return String.format("DataSet{%s %s}", string2, object);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.getDataSource(), n, false);
        zzbfp.zza(parcel, 2, this.getDataType(), n, false);
        zzbfp.zzd(parcel, 3, this.zzaqk(), false);
        zzbfp.zzc(parcel, 4, this.zzhan, false);
        zzbfp.zza(parcel, 5, this.zzhao);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, n2);
    }

    final List<RawDataPoint> zzab(List<DataSource> list) {
        ArrayList<RawDataPoint> arrayList = new ArrayList<RawDataPoint>(this.zzham.size());
        Iterator<DataPoint> iterator = this.zzham.iterator();
        while (iterator.hasNext()) {
            arrayList.add(new RawDataPoint(iterator.next(), list));
        }
        return arrayList;
    }

    public final boolean zzaqd() {
        return this.zzhao;
    }

    public final void zzb(Iterable<DataPoint> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.zza((DataPoint)object.next());
        }
    }
}


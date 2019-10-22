/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.RawBucket;
import com.google.android.gms.fitness.data.RawDataSet;
import com.google.android.gms.fitness.result.zzc;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DataReadResult
extends zzbfm
implements Result {
    public static final Parcelable.Creator<DataReadResult> CREATOR = new zzc();
    private final Status mStatus;
    private final int zzeck;
    private final List<DataSet> zzhab;
    private final List<Bucket> zzhii;
    private int zzhij;
    private final List<DataSource> zzhik;
    private final List<DataType> zzhil;

    /*
     * WARNING - void declaration
     */
    DataReadResult(int n, List<RawDataSet> iterator, Status zzbfm22, List<RawBucket> list, int n2, List<DataSource> list2, List<DataType> list3) {
        void var4_8;
        void var7_11;
        void var5_9;
        void var6_10;
        this.zzeck = n;
        this.mStatus = zzbfm22;
        this.zzhij = var5_9;
        this.zzhik = var6_10;
        this.zzhil = var7_11;
        this.zzhab = new ArrayList<DataSet>(iterator.size());
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            RawDataSet rawDataSet = (RawDataSet)((Object)iterator.next());
            this.zzhab.add(new DataSet(rawDataSet, (List<DataSource>)var6_10));
        }
        this.zzhii = new ArrayList<Bucket>(var4_8.size());
        for (RawBucket rawBucket : var4_8) {
            this.zzhii.add(new Bucket(rawBucket, (List<DataSource>)var6_10));
        }
    }

    private DataReadResult(List<DataSet> list, List<Bucket> list2, Status status) {
        this.zzeck = 5;
        this.zzhab = list;
        this.mStatus = status;
        this.zzhii = list2;
        this.zzhij = 1;
        this.zzhik = new ArrayList<DataSource>();
        this.zzhil = new ArrayList<DataType>();
    }

    public static DataReadResult zza(Status status, List<DataType> object, List<DataSource> object2) {
        ArrayList<DataSet> arrayList = new ArrayList<DataSet>();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            arrayList.add(DataSet.create((DataSource)object2.next()));
        }
        object = object.iterator();
        while (object.hasNext()) {
            object2 = (DataType)object.next();
            arrayList.add(DataSet.create(new DataSource.Builder().setDataType((DataType)object2).setType(1).setName("Default").build()));
        }
        return new DataReadResult(arrayList, Collections.<Bucket>emptyList(), status);
    }

    private static void zza(DataSet dataSet, List<DataSet> list) {
        for (DataSet dataSet2 : list) {
            if (!dataSet2.getDataSource().equals(dataSet.getDataSource())) continue;
            dataSet2.zzb(dataSet.getDataPoints());
            return;
        }
        list.add(dataSet);
    }

    private List<RawBucket> zzaqy() {
        ArrayList<RawBucket> arrayList = new ArrayList<RawBucket>(this.zzhii.size());
        Iterator<Bucket> iterator = this.zzhii.iterator();
        while (iterator.hasNext()) {
            arrayList.add(new RawBucket(iterator.next(), this.zzhik, this.zzhil));
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof DataReadResult)) return bl2;
        object = (DataReadResult)object;
        boolean bl3 = this.mStatus.equals(((DataReadResult)object).mStatus) && zzbg.equal(this.zzhab, ((DataReadResult)object).zzhab) && zzbg.equal(this.zzhii, ((DataReadResult)object).zzhii);
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public List<Bucket> getBuckets() {
        return this.zzhii;
    }

    public DataSet getDataSet(DataType dataType) {
        for (DataSet dataSet : this.zzhab) {
            if (!dataType.equals(dataSet.getDataType())) continue;
            return dataSet;
        }
        return DataSet.create(new DataSource.Builder().setDataType(dataType).setType(1).build());
    }

    public List<DataSet> getDataSets() {
        return this.zzhab;
    }

    @Override
    public Status getStatus() {
        return this.mStatus;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzhab, this.zzhii});
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        Object object;
        int n;
        zzbi zzbi2 = zzbg.zzx(this).zzg("status", this.mStatus);
        if (this.zzhab.size() > 5) {
            n = this.zzhab.size();
            object = new StringBuilder(21).append(n).append(" data sets").toString();
        } else {
            object = this.zzhab;
        }
        zzbi2 = zzbi2.zzg("dataSets", object);
        if (this.zzhii.size() > 5) {
            n = this.zzhii.size();
            object = new StringBuilder(19).append(n).append(" buckets").toString();
            return zzbi2.zzg("buckets", object).toString();
        }
        object = this.zzhii;
        return zzbi2.zzg("buckets", object).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        ArrayList<RawDataSet> arrayList = new ArrayList<RawDataSet>(this.zzhab.size());
        Iterator<DataSet> iterator = this.zzhab.iterator();
        while (iterator.hasNext()) {
            arrayList.add(new RawDataSet(iterator.next(), this.zzhik, this.zzhil));
        }
        zzbfp.zzd(parcel, 1, arrayList, false);
        zzbfp.zza(parcel, 2, this.getStatus(), n, false);
        zzbfp.zzd(parcel, 3, this.zzaqy(), false);
        zzbfp.zzc(parcel, 5, this.zzhij);
        zzbfp.zzc(parcel, 6, this.zzhik, false);
        zzbfp.zzc(parcel, 7, this.zzhil, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
    }

    public final int zzaqx() {
        return this.zzhij;
    }

    public final void zzb(DataReadResult object) {
        Iterator<DataSet> object22 = ((DataReadResult)((Object)object)).getDataSets().iterator();
        while (object22.hasNext()) {
            DataReadResult.zza(object22.next(), this.zzhab);
        }
        block1: for (Bucket bucket : ((DataReadResult)((Object)object)).getBuckets()) {
            for (Bucket bucket2 : this.zzhii) {
                if (!bucket2.zza(bucket)) continue;
                Iterator<DataSet> iterator = bucket.getDataSets().iterator();
                while (iterator.hasNext()) {
                    DataReadResult.zza(iterator.next(), bucket2.getDataSets());
                }
                continue block1;
            }
            this.zzhii.add(bucket);
        }
    }
}


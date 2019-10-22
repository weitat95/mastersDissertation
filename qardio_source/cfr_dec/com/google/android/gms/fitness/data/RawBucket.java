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
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.RawDataSet;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.zzy;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@KeepName
public final class RawBucket
extends zzbfm {
    public static final Parcelable.Creator<RawBucket> CREATOR = new zzy();
    public final long zzdvq;
    private int zzeck;
    public final Session zzgzp;
    public final long zzgzz;
    public final List<RawDataSet> zzhab;
    public final int zzhac;
    public final boolean zzhad;
    public final int zzhdk;

    public RawBucket(int n, long l, long l2, Session session, int n2, List<RawDataSet> list, int n3, boolean bl) {
        this.zzeck = n;
        this.zzdvq = l;
        this.zzgzz = l2;
        this.zzgzp = session;
        this.zzhdk = n2;
        this.zzhab = list;
        this.zzhac = n3;
        this.zzhad = bl;
    }

    public RawBucket(Bucket iterator, List<DataSource> list, List<DataType> list2) {
        this.zzeck = 2;
        this.zzdvq = ((Bucket)((Object)iterator)).getStartTime(TimeUnit.MILLISECONDS);
        this.zzgzz = ((Bucket)((Object)iterator)).getEndTime(TimeUnit.MILLISECONDS);
        this.zzgzp = ((Bucket)((Object)iterator)).getSession();
        this.zzhdk = ((Bucket)((Object)iterator)).zzaqc();
        this.zzhac = ((Bucket)((Object)iterator)).getBucketType();
        this.zzhad = ((Bucket)((Object)iterator)).zzaqd();
        iterator = ((Bucket)((Object)iterator)).getDataSets();
        this.zzhab = new ArrayList<RawDataSet>(iterator.size());
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            DataSet dataSet = (DataSet)iterator.next();
            this.zzhab.add(new RawDataSet(dataSet, list, list2));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof RawBucket)) return bl2;
        object = (RawBucket)object;
        boolean bl3 = this.zzdvq == ((RawBucket)object).zzdvq && this.zzgzz == ((RawBucket)object).zzgzz && this.zzhdk == ((RawBucket)object).zzhdk && zzbg.equal(this.zzhab, ((RawBucket)object).zzhab) && this.zzhac == ((RawBucket)object).zzhac && this.zzhad == ((RawBucket)object).zzhad;
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzdvq, this.zzgzz, this.zzhac});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("startTime", this.zzdvq).zzg("endTime", this.zzgzz).zzg("activity", this.zzhdk).zzg("dataSets", this.zzhab).zzg("bucketType", this.zzhac).zzg("serverHasMoreData", this.zzhad).toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzdvq);
        zzbfp.zza(parcel, 2, this.zzgzz);
        zzbfp.zza(parcel, 3, this.zzgzp, n, false);
        zzbfp.zzc(parcel, 4, this.zzhdk);
        zzbfp.zzc(parcel, 5, this.zzhab, false);
        zzbfp.zzc(parcel, 6, this.zzhac);
        zzbfp.zza(parcel, 7, this.zzhad);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
    }
}


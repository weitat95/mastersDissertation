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
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.RawBucket;
import com.google.android.gms.fitness.data.RawDataSet;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.zze;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Bucket
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<Bucket> CREATOR = new zze();
    private final long zzdvq;
    private final int zzeck;
    private final Session zzgzp;
    private final long zzgzz;
    private final int zzhaa;
    private final List<DataSet> zzhab;
    private final int zzhac;
    private boolean zzhad = false;

    Bucket(int n, long l, long l2, Session session, int n2, List<DataSet> list, int n3, boolean bl) {
        this.zzeck = n;
        this.zzdvq = l;
        this.zzgzz = l2;
        this.zzgzp = session;
        this.zzhaa = n2;
        this.zzhab = list;
        this.zzhac = n3;
        this.zzhad = bl;
    }

    public Bucket(RawBucket rawBucket, List<DataSource> list) {
        this(2, rawBucket.zzdvq, rawBucket.zzgzz, rawBucket.zzgzp, rawBucket.zzhdk, Bucket.zza(rawBucket.zzhab, list), rawBucket.zzhac, rawBucket.zzhad);
    }

    private static List<DataSet> zza(Collection<RawDataSet> object, List<DataSource> list) {
        ArrayList<DataSet> arrayList = new ArrayList<DataSet>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(new DataSet((RawDataSet)object.next(), list));
        }
        return arrayList;
    }

    public static String zzda(int n) {
        switch (n) {
            default: {
                return "bug";
            }
            case 1: {
                return "time";
            }
            case 3: {
                return "type";
            }
            case 4: {
                return "segment";
            }
            case 2: {
                return "session";
            }
            case 0: 
        }
        return "unknown";
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (object == this) return true;
        boolean bl2 = bl;
        if (!(object instanceof Bucket)) return bl2;
        object = (Bucket)object;
        boolean bl3 = this.zzdvq == ((Bucket)object).zzdvq && this.zzgzz == ((Bucket)object).zzgzz && this.zzhaa == ((Bucket)object).zzhaa && zzbg.equal(this.zzhab, ((Bucket)object).zzhab) && this.zzhac == ((Bucket)object).zzhac && this.zzhad == ((Bucket)object).zzhad;
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public int getBucketType() {
        return this.zzhac;
    }

    public List<DataSet> getDataSets() {
        return this.zzhab;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzgzz, TimeUnit.MILLISECONDS);
    }

    public Session getSession() {
        return this.zzgzp;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzdvq, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzdvq, this.zzgzz, this.zzhaa, this.zzhac});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("startTime", this.zzdvq).zzg("endTime", this.zzgzz).zzg("activity", this.zzhaa).zzg("dataSets", this.zzhab).zzg("bucketType", Bucket.zzda(this.zzhac)).zzg("serverHasMoreData", this.zzhad).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzdvq);
        zzbfp.zza(parcel, 2, this.zzgzz);
        zzbfp.zza(parcel, 3, this.getSession(), n, false);
        zzbfp.zzc(parcel, 4, this.zzhaa);
        zzbfp.zzc(parcel, 5, this.getDataSets(), false);
        zzbfp.zzc(parcel, 6, this.getBucketType());
        zzbfp.zza(parcel, 7, this.zzaqd());
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
    }

    public final boolean zza(Bucket bucket) {
        return this.zzdvq == bucket.zzdvq && this.zzgzz == bucket.zzgzz && this.zzhaa == bucket.zzhaa && this.zzhac == bucket.zzhac;
    }

    public final int zzaqc() {
        return this.zzhaa;
    }

    public final boolean zzaqd() {
        if (this.zzhad) {
            return true;
        }
        Iterator<DataSet> iterator = this.zzhab.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().zzaqd()) continue;
            return true;
        }
        return false;
    }
}


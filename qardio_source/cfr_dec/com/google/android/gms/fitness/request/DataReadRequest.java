/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.fitness.request.zzm;
import com.google.android.gms.fitness.request.zzn;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbww;
import com.google.android.gms.internal.zzbwx;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataReadRequest
extends zzbfm {
    public static final Parcelable.Creator<DataReadRequest> CREATOR = new zzn();
    private final long zzdvq;
    private final int zzeck;
    private final List<DataType> zzgzy;
    private final long zzgzz;
    private final int zzhac;
    private final List<DataSource> zzhgf;
    private final List<DataType> zzhgk;
    private final List<DataSource> zzhgl;
    private final long zzhgm;
    private final DataSource zzhgn;
    private final int zzhgo;
    private final boolean zzhgp;
    private final boolean zzhgq;
    private final zzbww zzhgr;
    private final List<Device> zzhgs;
    private final List<Integer> zzhgt;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    DataReadRequest(int n, List<DataType> list, List<DataSource> list2, long l, long l2, List<DataType> list3, List<DataSource> list4, int n2, long l3, DataSource dataSource, int n3, boolean bl, boolean bl2, IBinder iBinder, List<Device> list5, List<Integer> list6) {
        void var11_18;
        void var6_14;
        void var2_10;
        void var16_22;
        void var10_17;
        void var15_21;
        void var4_13;
        void var18_24;
        void var14_20;
        void var8_15;
        void var13_19;
        void var19_25;
        void var2_7;
        void var17_23;
        void var3_12;
        void var9_16;
        void var2_4;
        this.zzeck = n;
        this.zzgzy = list;
        this.zzhgf = var3_12;
        this.zzdvq = var4_13;
        this.zzgzz = var6_14;
        this.zzhgk = var8_15;
        this.zzhgl = var9_16;
        this.zzhac = var10_17;
        this.zzhgm = var11_18;
        this.zzhgn = var13_19;
        this.zzhgo = var14_20;
        this.zzhgp = var15_21;
        this.zzhgq = var16_22;
        if (var17_23 == null) {
            Object var2_3 = null;
        } else {
            zzbww zzbww2 = zzbwx.zzat((IBinder)var17_23);
        }
        this.zzhgr = var2_4;
        void var2_5 = var18_24;
        if (var18_24 == null) {
            List list7 = Collections.emptyList();
        }
        this.zzhgs = var2_7;
        void var2_8 = var19_25;
        if (var19_25 == null) {
            List list8 = Collections.emptyList();
        }
        this.zzhgt = var2_10;
    }

    private DataReadRequest(Builder builder) {
        this(builder.zzgzy, builder.zzhgf, builder.zzdvq, builder.zzgzz, builder.zzhgk, builder.zzhgl, builder.zzhac, builder.zzhgm, builder.zzhgn, builder.zzhgo, false, builder.zzhgq, null, builder.zzhgs, builder.zzhgt);
    }

    /* synthetic */ DataReadRequest(Builder builder, zzm zzm2) {
        this(builder);
    }

    public DataReadRequest(DataReadRequest dataReadRequest, zzbww zzbww2) {
        this(dataReadRequest.zzgzy, dataReadRequest.zzhgf, dataReadRequest.zzdvq, dataReadRequest.zzgzz, dataReadRequest.zzhgk, dataReadRequest.zzhgl, dataReadRequest.zzhac, dataReadRequest.zzhgm, dataReadRequest.zzhgn, dataReadRequest.zzhgo, dataReadRequest.zzhgp, dataReadRequest.zzhgq, zzbww2, dataReadRequest.zzhgs, dataReadRequest.zzhgt);
    }

    /*
     * Enabled aggressive block sorting
     */
    private DataReadRequest(List<DataType> list, List<DataSource> list2, long l, long l2, List<DataType> list3, List<DataSource> list4, int n, long l3, DataSource dataSource, int n2, boolean bl, boolean bl2, zzbww zzbww2, List<Device> list5, List<Integer> list6) {
        zzbww2 = zzbww2 == null ? null : zzbww2.asBinder();
        this(6, list, list2, l, l2, list3, list4, n, l3, dataSource, n2, bl, bl2, (IBinder)zzbww2, list5, list6);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof DataReadRequest)) return bl2;
        object = (DataReadRequest)object;
        boolean bl3 = this.zzgzy.equals(((DataReadRequest)object).zzgzy) && this.zzhgf.equals(((DataReadRequest)object).zzhgf) && this.zzdvq == ((DataReadRequest)object).zzdvq && this.zzgzz == ((DataReadRequest)object).zzgzz && this.zzhac == ((DataReadRequest)object).zzhac && this.zzhgl.equals(((DataReadRequest)object).zzhgl) && this.zzhgk.equals(((DataReadRequest)object).zzhgk) && zzbg.equal(this.zzhgn, ((DataReadRequest)object).zzhgn) && this.zzhgm == ((DataReadRequest)object).zzhgm && this.zzhgq == ((DataReadRequest)object).zzhgq && this.zzhgo == ((DataReadRequest)object).zzhgo && this.zzhgp == ((DataReadRequest)object).zzhgp && zzbg.equal(this.zzhgr, ((DataReadRequest)object).zzhgr) && zzbg.equal(this.zzhgs, ((DataReadRequest)object).zzhgs) && zzbg.equal(this.zzhgt, ((DataReadRequest)object).zzhgt);
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public DataSource getActivityDataSource() {
        return this.zzhgn;
    }

    public List<DataSource> getAggregatedDataSources() {
        return this.zzhgl;
    }

    public List<DataType> getAggregatedDataTypes() {
        return this.zzhgk;
    }

    public int getBucketType() {
        return this.zzhac;
    }

    public List<DataSource> getDataSources() {
        return this.zzhgf;
    }

    public List<DataType> getDataTypes() {
        return this.zzgzy;
    }

    public List<Integer> getFilteredDataQualityStandards() {
        return this.zzhgt;
    }

    public int getLimit() {
        return this.zzhgo;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhac, this.zzdvq, this.zzgzz});
    }

    public String toString() {
        Iterator<Object> iterator;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataReadRequest{");
        if (!this.zzgzy.isEmpty()) {
            iterator = this.zzgzy.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(((DataType)iterator.next()).zzaqp()).append(" ");
            }
        }
        if (!this.zzhgf.isEmpty()) {
            iterator = this.zzhgf.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(((DataSource)iterator.next()).toDebugString()).append(" ");
            }
        }
        if (this.zzhac != 0) {
            stringBuilder.append("bucket by ").append(Bucket.zzda(this.zzhac));
            if (this.zzhgm > 0L) {
                stringBuilder.append(" >").append(this.zzhgm).append("ms");
            }
            stringBuilder.append(": ");
        }
        if (!this.zzhgk.isEmpty()) {
            iterator = this.zzhgk.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(((DataType)iterator.next()).zzaqp()).append(" ");
            }
        }
        if (!this.zzhgl.isEmpty()) {
            iterator = this.zzhgl.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(((DataSource)iterator.next()).toDebugString()).append(" ");
            }
        }
        stringBuilder.append(String.format("(%tF %tT - %tF %tT)", this.zzdvq, this.zzdvq, this.zzgzz, this.zzgzz));
        if (this.zzhgn != null) {
            stringBuilder.append("activities: ").append(this.zzhgn.toDebugString());
        }
        if (!this.zzhgt.isEmpty()) {
            stringBuilder.append("quality: ");
            iterator = this.zzhgt.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(DataSource.zzdd((Integer)iterator.next())).append(" ");
            }
        }
        if (this.zzhgq) {
            stringBuilder.append(" +server");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.getDataTypes(), false);
        zzbfp.zzc(parcel, 2, this.getDataSources(), false);
        zzbfp.zza(parcel, 3, this.zzdvq);
        zzbfp.zza(parcel, 4, this.zzgzz);
        zzbfp.zzc(parcel, 5, this.getAggregatedDataTypes(), false);
        zzbfp.zzc(parcel, 6, this.getAggregatedDataSources(), false);
        zzbfp.zzc(parcel, 7, this.getBucketType());
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zza(parcel, 8, this.zzhgm);
        zzbfp.zza(parcel, 9, this.getActivityDataSource(), n, false);
        zzbfp.zzc(parcel, 10, this.getLimit());
        zzbfp.zza(parcel, 12, this.zzhgp);
        zzbfp.zza(parcel, 13, this.zzhgq);
        IBinder iBinder = this.zzhgr == null ? null : this.zzhgr.asBinder();
        zzbfp.zza(parcel, 14, iBinder, false);
        zzbfp.zzc(parcel, 16, this.zzhgs, false);
        zzbfp.zza(parcel, 17, this.getFilteredDataQualityStandards(), false);
        zzbfp.zzai(parcel, n2);
    }

    public static class Builder {
        private long zzdvq;
        private List<DataType> zzgzy = new ArrayList<DataType>();
        private long zzgzz;
        private int zzhac = 0;
        private List<DataSource> zzhgf = new ArrayList<DataSource>();
        private List<DataType> zzhgk = new ArrayList<DataType>();
        private List<DataSource> zzhgl = new ArrayList<DataSource>();
        private long zzhgm = 0L;
        private DataSource zzhgn;
        private int zzhgo = 0;
        private boolean zzhgp = false;
        private boolean zzhgq = false;
        private final List<Device> zzhgs = new ArrayList<Device>();
        private final List<Integer> zzhgt = new ArrayList<Integer>();

        /*
         * Enabled aggressive block sorting
         */
        public Builder aggregate(DataSource dataSource, DataType dataType) {
            zzbq.checkNotNull(dataSource, "Attempting to add a null data source");
            boolean bl = !this.zzhgf.contains(dataSource);
            zzbq.zza(bl, "Cannot add the same data source for aggregated and detailed");
            DataType dataType2 = dataSource.getDataType();
            List<DataType> list = DataType.getAggregatesForInput(dataType2);
            bl = !list.isEmpty();
            zzbq.zzb(bl, "Unsupported input data type specified for aggregation: %s", dataType2);
            zzbq.zzb(list.contains(dataType), "Invalid output aggregate data type specified: %s -> %s", dataType2, dataType);
            if (!this.zzhgl.contains(dataSource)) {
                this.zzhgl.add(dataSource);
            }
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder aggregate(DataType dataType, DataType dataType2) {
            zzbq.checkNotNull(dataType, "Attempting to use a null data type");
            boolean bl = !this.zzgzy.contains(dataType);
            zzbq.zza(bl, "Cannot add the same data type as aggregated and detailed");
            List<DataType> list = DataType.getAggregatesForInput(dataType);
            bl = !list.isEmpty();
            zzbq.zzb(bl, "Unsupported input data type specified for aggregation: %s", dataType);
            zzbq.zzb(list.contains(dataType2), "Invalid output aggregate data type specified: %s -> %s", dataType, dataType2);
            if (!this.zzhgk.contains(dataType)) {
                this.zzhgk.add(dataType);
            }
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder bucketByActivitySegment(int n, TimeUnit timeUnit) {
            boolean bl = this.zzhac == 0;
            zzbq.zzb(bl, "Bucketing strategy already set to %s", this.zzhac);
            bl = n > 0;
            zzbq.zzb(bl, "Must specify a valid minimum duration for an activity segment: %d", n);
            this.zzhac = 4;
            this.zzhgm = timeUnit.toMillis(n);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder bucketByTime(int n, TimeUnit timeUnit) {
            boolean bl = this.zzhac == 0;
            zzbq.zzb(bl, "Bucketing strategy already set to %s", this.zzhac);
            bl = n > 0;
            zzbq.zzb(bl, "Must specify a valid minimum duration for an activity segment: %d", n);
            this.zzhac = 1;
            this.zzhgm = timeUnit.toMillis(n);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public DataReadRequest build() {
            boolean bl;
            block3: {
                boolean bl2;
                boolean bl3;
                block2: {
                    bl2 = true;
                    bl = !this.zzhgf.isEmpty() || !this.zzgzy.isEmpty() || !this.zzhgl.isEmpty() || !this.zzhgk.isEmpty();
                    zzbq.zza(bl, "Must add at least one data source (aggregated or detailed)");
                    bl = this.zzdvq > 0L;
                    zzbq.zza(bl, "Invalid start time: %s", this.zzdvq);
                    bl = this.zzgzz > 0L && this.zzgzz > this.zzdvq;
                    zzbq.zza(bl, "Invalid end time: %s", this.zzgzz);
                    bl3 = this.zzhgl.isEmpty() && this.zzhgk.isEmpty();
                    if (!bl3) break block2;
                    bl = bl2;
                    if (this.zzhac == 0) break block3;
                }
                bl = !bl3 && this.zzhac != 0 ? bl2 : false;
            }
            zzbq.zza(bl, "Must specify a valid bucketing strategy while requesting aggregation");
            return new DataReadRequest(this, null);
        }

        public Builder enableServerQueries() {
            this.zzhgq = true;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder read(DataSource dataSource) {
            zzbq.checkNotNull(dataSource, "Attempting to add a null data source");
            boolean bl = !this.zzhgl.contains(dataSource);
            zzbq.checkArgument(bl, "Cannot add the same data source as aggregated and detailed");
            if (!this.zzhgf.contains(dataSource)) {
                this.zzhgf.add(dataSource);
            }
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder read(DataType dataType) {
            zzbq.checkNotNull(dataType, "Attempting to use a null data type");
            boolean bl = !this.zzhgk.contains(dataType);
            zzbq.zza(bl, "Cannot add the same data type as aggregated and detailed");
            if (!this.zzgzy.contains(dataType)) {
                this.zzgzy.add(dataType);
            }
            return this;
        }

        public Builder setTimeRange(long l, long l2, TimeUnit timeUnit) {
            this.zzdvq = timeUnit.toMillis(l);
            this.zzgzz = timeUnit.toMillis(l2);
            return this;
        }
    }

}


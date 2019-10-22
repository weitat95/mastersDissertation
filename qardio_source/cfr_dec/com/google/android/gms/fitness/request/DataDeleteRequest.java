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
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.zzi;
import com.google.android.gms.fitness.request.zzj;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataDeleteRequest
extends zzbfm {
    public static final Parcelable.Creator<DataDeleteRequest> CREATOR = new zzj();
    private final long zzdvq;
    private final int zzeck;
    private final List<DataType> zzgzy;
    private final long zzgzz;
    private final zzbyf zzhgc;
    private final List<DataSource> zzhgf;
    private final List<Session> zzhgg;
    private final boolean zzhgh;
    private final boolean zzhgi;

    DataDeleteRequest(int n, long l, long l2, List<DataSource> list, List<DataType> list2, List<Session> list3, boolean bl, boolean bl2, IBinder iBinder) {
        this.zzeck = n;
        this.zzdvq = l;
        this.zzgzz = l2;
        this.zzhgf = Collections.unmodifiableList(list);
        this.zzgzy = Collections.unmodifiableList(list2);
        this.zzhgg = list3;
        this.zzhgh = bl;
        this.zzhgi = bl2;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    private DataDeleteRequest(long l, long l2, List<DataSource> list, List<DataType> list2, List<Session> list3, boolean bl, boolean bl2, zzbyf zzbyf2) {
        this.zzeck = 3;
        this.zzdvq = l;
        this.zzgzz = l2;
        this.zzhgf = Collections.unmodifiableList(list);
        this.zzgzy = Collections.unmodifiableList(list2);
        this.zzhgg = list3;
        this.zzhgh = bl;
        this.zzhgi = bl2;
        this.zzhgc = zzbyf2;
    }

    private DataDeleteRequest(Builder builder) {
        this(builder.zzdvq, builder.zzgzz, builder.zzhgf, builder.zzgzy, builder.zzhgg, builder.zzhgh, builder.zzhgi, null);
    }

    /* synthetic */ DataDeleteRequest(Builder builder, zzi zzi2) {
        this(builder);
    }

    public DataDeleteRequest(DataDeleteRequest dataDeleteRequest, zzbyf zzbyf2) {
        this(dataDeleteRequest.zzdvq, dataDeleteRequest.zzgzz, dataDeleteRequest.zzhgf, dataDeleteRequest.zzgzy, dataDeleteRequest.zzhgg, dataDeleteRequest.zzhgh, dataDeleteRequest.zzhgi, zzbyf2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (object == this) return true;
        boolean bl2 = bl;
        if (!(object instanceof DataDeleteRequest)) return bl2;
        object = (DataDeleteRequest)object;
        boolean bl3 = this.zzdvq == ((DataDeleteRequest)object).zzdvq && this.zzgzz == ((DataDeleteRequest)object).zzgzz && zzbg.equal(this.zzhgf, ((DataDeleteRequest)object).zzhgf) && zzbg.equal(this.zzgzy, ((DataDeleteRequest)object).zzgzy) && zzbg.equal(this.zzhgg, ((DataDeleteRequest)object).zzhgg) && this.zzhgh == ((DataDeleteRequest)object).zzhgh && this.zzhgi == ((DataDeleteRequest)object).zzhgi;
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public List<DataSource> getDataSources() {
        return this.zzhgf;
    }

    public List<DataType> getDataTypes() {
        return this.zzgzy;
    }

    public List<Session> getSessions() {
        return this.zzhgg;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzdvq, this.zzgzz});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("startTimeMillis", this.zzdvq).zzg("endTimeMillis", this.zzgzz).zzg("dataSources", this.zzhgf).zzg("dateTypes", this.zzgzy).zzg("sessions", this.zzhgg).zzg("deleteAllData", this.zzhgh).zzg("deleteAllSessions", this.zzhgi).toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzdvq);
        zzbfp.zza(parcel, 2, this.zzgzz);
        zzbfp.zzc(parcel, 3, this.getDataSources(), false);
        zzbfp.zzc(parcel, 4, this.getDataTypes(), false);
        zzbfp.zzc(parcel, 5, this.getSessions(), false);
        zzbfp.zza(parcel, 6, this.zzhgh);
        zzbfp.zza(parcel, 7, this.zzhgi);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        IBinder iBinder = this.zzhgc == null ? null : this.zzhgc.asBinder();
        zzbfp.zza(parcel, 8, iBinder, false);
        zzbfp.zzai(parcel, n);
    }

    public static class Builder {
        private long zzdvq;
        private List<DataType> zzgzy;
        private long zzgzz;
        private List<DataSource> zzhgf = new ArrayList<DataSource>();
        private List<Session> zzhgg;
        private boolean zzhgh = false;
        private boolean zzhgi = false;

        public Builder() {
            this.zzgzy = new ArrayList<DataType>();
            this.zzhgg = new ArrayList<Session>();
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder addDataType(DataType dataType) {
            boolean bl = true;
            boolean bl2 = !this.zzhgh;
            zzbq.checkArgument(bl2, "All data is already marked for deletion.  addDataType() cannot be combined with deleteAllData()");
            bl2 = dataType != null ? bl : false;
            zzbq.checkArgument(bl2, "Must specify a valid data type");
            if (!this.zzgzy.contains(dataType)) {
                this.zzgzy.add(dataType);
            }
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public DataDeleteRequest build() {
            boolean bl = this.zzdvq > 0L && this.zzgzz > this.zzdvq;
            zzbq.zza(bl, "Must specify a valid time interval");
            boolean bl2 = this.zzhgh || !this.zzhgf.isEmpty() || !this.zzgzy.isEmpty();
            boolean bl3 = this.zzhgi || !this.zzhgg.isEmpty();
            bl = bl2 || bl3;
            zzbq.zza(bl, "No data or session marked for deletion");
            if (!this.zzhgg.isEmpty()) {
                for (Session session : this.zzhgg) {
                    bl = session.getStartTime(TimeUnit.MILLISECONDS) >= this.zzdvq && session.getEndTime(TimeUnit.MILLISECONDS) <= this.zzgzz;
                    zzbq.zza(bl, "Session %s is outside the time interval [%d, %d]", session, this.zzdvq, this.zzgzz);
                }
            }
            return new DataDeleteRequest(this, null);
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder setTimeInterval(long l, long l2, TimeUnit timeUnit) {
            boolean bl = l > 0L;
            zzbq.zzb(bl, "Invalid start time :%d", l);
            bl = l2 > l;
            zzbq.zzb(bl, "Invalid end time :%d", l2);
            this.zzdvq = timeUnit.toMillis(l);
            this.zzgzz = timeUnit.toMillis(l2);
            return this;
        }
    }

}


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
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.RawDataPoint;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.data.zzh;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbut;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class DataPoint
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<DataPoint> CREATOR = new zzh();
    private final int versionCode;
    private final DataSource zzgzh;
    private long zzhag;
    private long zzhah;
    private final Value[] zzhai;
    private DataSource zzhaj;
    private long zzhak;
    private long zzhal;

    DataPoint(int n, DataSource dataSource, long l, long l2, Value[] arrvalue, DataSource dataSource2, long l3, long l4) {
        this.versionCode = n;
        this.zzgzh = dataSource;
        this.zzhaj = dataSource2;
        this.zzhag = l;
        this.zzhah = l2;
        this.zzhai = arrvalue;
        this.zzhak = l3;
        this.zzhal = l4;
    }

    private DataPoint(DataSource iterator) {
        this.versionCode = 4;
        this.zzgzh = zzbq.checkNotNull(iterator, "Data source cannot be null");
        iterator = ((DataSource)((Object)iterator)).getDataType().getFields();
        this.zzhai = new Value[iterator.size()];
        iterator = iterator.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            Field field = (Field)iterator.next();
            this.zzhai[n] = new Value(field.getFormat());
            ++n;
        }
    }

    private DataPoint(DataSource dataSource, DataSource dataSource2, RawDataPoint rawDataPoint) {
        this(4, dataSource, DataPoint.zza(rawDataPoint.zzhdl, 0L), DataPoint.zza(rawDataPoint.zzhdm, 0L), rawDataPoint.zzhdn, dataSource2, DataPoint.zza(rawDataPoint.zzhdq, 0L), DataPoint.zza(rawDataPoint.zzhdr, 0L));
    }

    DataPoint(List<DataSource> list, RawDataPoint rawDataPoint) {
        this(DataPoint.zzc(list, rawDataPoint.zzhdo), DataPoint.zzc(list, rawDataPoint.zzhdp), rawDataPoint);
    }

    public static DataPoint create(DataSource dataSource) {
        return new DataPoint(dataSource);
    }

    private static long zza(Long l, long l2) {
        if (l != null) {
            return l;
        }
        return 0L;
    }

    private static DataSource zzc(List<DataSource> list, int n) {
        if (n >= 0 && n < list.size()) {
            return list.get(n);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof DataPoint)) return bl2;
        object = (DataPoint)object;
        boolean bl3 = zzbg.equal(this.zzgzh, ((DataPoint)object).zzgzh) && this.zzhag == ((DataPoint)object).zzhag && this.zzhah == ((DataPoint)object).zzhah && Arrays.equals(this.zzhai, ((DataPoint)object).zzhai) && zzbg.equal(this.getOriginalDataSource(), ((DataPoint)object).getOriginalDataSource());
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public final DataSource getDataSource() {
        return this.zzgzh;
    }

    public final DataType getDataType() {
        return this.zzgzh.getDataType();
    }

    public final long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhag, TimeUnit.NANOSECONDS);
    }

    public final DataSource getOriginalDataSource() {
        if (this.zzhaj != null) {
            return this.zzhaj;
        }
        return this.zzgzh;
    }

    public final long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhah, TimeUnit.NANOSECONDS);
    }

    public final long getTimestamp(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhag, TimeUnit.NANOSECONDS);
    }

    public final Value getValue(Field field) {
        int n = this.getDataType().indexOf(field);
        return this.zzhai[n];
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzgzh, this.zzhag, this.zzhah});
    }

    public final DataPoint setTimeInterval(long l, long l2, TimeUnit timeUnit) {
        this.zzhah = timeUnit.toNanos(l);
        this.zzhag = timeUnit.toNanos(l2);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final DataPoint setTimestamp(long l, TimeUnit timeUnit) {
        boolean bl = true;
        this.zzhag = timeUnit.toNanos(l);
        boolean bl2 = this.getDataType() == DataType.TYPE_LOCATION_SAMPLE;
        if (bl2 && (bl2 = timeUnit.convert(1L, TimeUnit.MILLISECONDS) > 1L ? bl : false)) {
            Log.w((String)"Fitness", (String)"Storing location at more than millisecond granularity is not supported. Extra precision is lost as the value is converted to milliseconds.");
            this.zzhag = zzbut.zza(this.zzhag, TimeUnit.NANOSECONDS, TimeUnit.MILLISECONDS);
        }
        return this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final String toString() {
        String string2;
        String string3 = Arrays.toString(this.zzhai);
        long l = this.zzhah;
        long l2 = this.zzhag;
        long l3 = this.zzhak;
        long l4 = this.zzhal;
        String string4 = this.zzgzh.toDebugString();
        if (this.zzhaj != null) {
            string2 = this.zzhaj.toDebugString();
            do {
                return String.format("DataPoint{%s@[%s, %s,raw=%s,insert=%s](%s %s)}", string3, l, l2, l3, l4, string4, string2);
                break;
            } while (true);
        }
        string2 = "N/A";
        return String.format("DataPoint{%s@[%s, %s,raw=%s,insert=%s](%s %s)}", string3, l, l2, l3, l4, string4, string2);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.getDataSource(), n, false);
        zzbfp.zza(parcel, 3, this.zzhag);
        zzbfp.zza(parcel, 4, this.zzhah);
        zzbfp.zza((Parcel)parcel, (int)5, (Parcelable[])this.zzhai, (int)n, (boolean)false);
        zzbfp.zza(parcel, 6, this.zzhaj, n, false);
        zzbfp.zza(parcel, 7, this.zzhak);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zza(parcel, 8, this.zzhal);
        zzbfp.zzai(parcel, n2);
    }

    public final Value[] zzaqf() {
        return this.zzhai;
    }

    public final DataSource zzaqg() {
        return this.zzhaj;
    }

    public final long zzaqh() {
        return this.zzhak;
    }

    public final long zzaqi() {
        return this.zzhal;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzaqj() {
        DataSource dataSource = this.getDataSource();
        zzbq.zzb(this.getDataType().getName().equals(dataSource.getDataType().getName()), "Conflicting data types found %s vs %s", this.getDataType(), this.getDataType());
        boolean bl = this.zzhag > 0L;
        zzbq.zzb(bl, "Data point does not have the timestamp set: %s", this);
        bl = this.zzhah <= this.zzhag;
        zzbq.zzb(bl, "Data point with start time greater than end time found: %s", this);
    }

    public final Value zzdb(int n) {
        DataType dataType = this.getDataType();
        if (n < 0 || n >= dataType.getFields().size()) {
            throw new IllegalArgumentException(String.format("fieldIndex %s is out of range for %s", n, dataType));
        }
        return this.zzhai[n];
    }
}


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
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.result.zzb;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public class DailyTotalResult
extends zzbfm
implements Result {
    public static final Parcelable.Creator<DailyTotalResult> CREATOR = new zzb();
    private final Status mStatus;
    private final int zzeck;
    private final DataSet zzhdx;

    DailyTotalResult(int n, Status status, DataSet dataSet) {
        this.zzeck = n;
        this.mStatus = status;
        this.zzhdx = dataSet;
    }

    private DailyTotalResult(DataSet dataSet, Status status) {
        this.zzeck = 1;
        this.mStatus = status;
        this.zzhdx = dataSet;
    }

    public static DailyTotalResult zza(Status status, DataType dataType) {
        return new DailyTotalResult(DataSet.create(new DataSource.Builder().setDataType(dataType).setType(1).build()), status);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof DailyTotalResult)) return bl2;
        object = (DailyTotalResult)object;
        boolean bl3 = this.mStatus.equals(((DailyTotalResult)object).mStatus) && zzbg.equal(this.zzhdx, ((DailyTotalResult)object).zzhdx);
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    @Override
    public Status getStatus() {
        return this.mStatus;
    }

    public DataSet getTotal() {
        return this.zzhdx;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzhdx});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("status", this.mStatus).zzg("dataPoint", this.zzhdx).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.getStatus(), n, false);
        zzbfp.zza(parcel, 2, this.getTotal(), n, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
    }
}


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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.RawBucket;
import com.google.android.gms.fitness.data.RawDataSet;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzc
implements Parcelable.Creator<DataReadResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = 0;
        ArrayList<DataType> arrayList = null;
        int n2 = zzbfn.zzd(parcel);
        ArrayList<RawDataSet> arrayList2 = new ArrayList<RawDataSet>();
        ArrayList<RawBucket> arrayList3 = new ArrayList<RawBucket>();
        ArrayList<DataSource> arrayList4 = null;
        Status status = null;
        int n3 = 0;
        block9 : while (parcel.dataPosition() < n2) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block9;
                }
                case 1: {
                    zzbfn.zza(parcel, n4, arrayList2, this.getClass().getClassLoader());
                    continue block9;
                }
                case 2: {
                    status = zzbfn.zza(parcel, n4, Status.CREATOR);
                    continue block9;
                }
                case 3: {
                    zzbfn.zza(parcel, n4, arrayList3, this.getClass().getClassLoader());
                    continue block9;
                }
                case 5: {
                    n = zzbfn.zzg(parcel, n4);
                    continue block9;
                }
                case 6: {
                    arrayList4 = zzbfn.zzc(parcel, n4, DataSource.CREATOR);
                    continue block9;
                }
                case 7: {
                    arrayList = zzbfn.zzc(parcel, n4, DataType.CREATOR);
                    continue block9;
                }
                case 1000: 
            }
            n3 = zzbfn.zzg(parcel, n4);
        }
        zzbfn.zzaf(parcel, n2);
        return new DataReadResult(n3, arrayList2, status, arrayList3, n, arrayList4, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new DataReadResult[n];
    }
}


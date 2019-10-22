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
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.internal.zzbfn;

public final class zzb
implements Parcelable.Creator<DailyTotalResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        Status status = null;
        int n2 = 0;
        DataSet dataSet = null;
        block5 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block5;
                }
                case 1: {
                    status = zzbfn.zza(parcel, n3, Status.CREATOR);
                    continue block5;
                }
                case 2: {
                    dataSet = zzbfn.zza(parcel, n3, DataSet.CREATOR);
                    continue block5;
                }
                case 1000: 
            }
            n2 = zzbfn.zzg(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new DailyTotalResult(n2, status, dataSet);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new DailyTotalResult[n];
    }
}


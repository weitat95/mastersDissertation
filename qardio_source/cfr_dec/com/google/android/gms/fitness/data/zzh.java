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
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.internal.zzbfn;

public final class zzh
implements Parcelable.Creator<DataPoint> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        DataSource dataSource = null;
        long l = 0L;
        long l2 = 0L;
        Value[] arrvalue = null;
        DataSource dataSource2 = null;
        long l3 = 0L;
        long l4 = 0L;
        block10 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block10;
                }
                case 1: {
                    dataSource = zzbfn.zza(parcel, n3, DataSource.CREATOR);
                    continue block10;
                }
                case 3: {
                    l = zzbfn.zzi(parcel, n3);
                    continue block10;
                }
                case 4: {
                    l2 = zzbfn.zzi(parcel, n3);
                    continue block10;
                }
                case 5: {
                    arrvalue = zzbfn.zzb(parcel, n3, Value.CREATOR);
                    continue block10;
                }
                case 6: {
                    dataSource2 = zzbfn.zza(parcel, n3, DataSource.CREATOR);
                    continue block10;
                }
                case 7: {
                    l3 = zzbfn.zzi(parcel, n3);
                    continue block10;
                }
                case 1000: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block10;
                }
                case 8: 
            }
            l4 = zzbfn.zzi(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new DataPoint(n2, dataSource, l, l2, arrvalue, dataSource2, l3, l4);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new DataPoint[n];
    }
}


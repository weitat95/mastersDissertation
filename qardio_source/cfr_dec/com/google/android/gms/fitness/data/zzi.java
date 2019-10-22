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
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.RawDataPoint;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzi
implements Parcelable.Creator<DataSet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean bl = false;
        ArrayList<DataSource> arrayList = null;
        int n = zzbfn.zzd(parcel);
        ArrayList<RawDataPoint> arrayList2 = new ArrayList<RawDataPoint>();
        DataType dataType = null;
        DataSource dataSource = null;
        int n2 = 0;
        block8 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block8;
                }
                case 1: {
                    dataSource = zzbfn.zza(parcel, n3, DataSource.CREATOR);
                    continue block8;
                }
                case 2: {
                    dataType = zzbfn.zza(parcel, n3, DataType.CREATOR);
                    continue block8;
                }
                case 3: {
                    zzbfn.zza(parcel, n3, arrayList2, this.getClass().getClassLoader());
                    continue block8;
                }
                case 4: {
                    arrayList = zzbfn.zzc(parcel, n3, DataSource.CREATOR);
                    continue block8;
                }
                case 5: {
                    bl = zzbfn.zzc(parcel, n3);
                    continue block8;
                }
                case 1000: 
            }
            n2 = zzbfn.zzg(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new DataSet(n2, dataSource, dataType, arrayList2, arrayList, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new DataSet[n];
    }
}


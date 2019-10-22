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
import com.google.android.gms.fitness.data.RawDataPoint;
import com.google.android.gms.fitness.data.RawDataSet;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzaa
implements Parcelable.Creator<RawDataSet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean bl = false;
        int n = zzbfn.zzd(parcel);
        ArrayList<RawDataPoint> arrayList = null;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        block7 : while (parcel.dataPosition() < n) {
            int n5 = parcel.readInt();
            switch (0xFFFF & n5) {
                default: {
                    zzbfn.zzb(parcel, n5);
                    continue block7;
                }
                case 1: {
                    n3 = zzbfn.zzg(parcel, n5);
                    continue block7;
                }
                case 2: {
                    n2 = zzbfn.zzg(parcel, n5);
                    continue block7;
                }
                case 3: {
                    arrayList = zzbfn.zzc(parcel, n5, RawDataPoint.CREATOR);
                    continue block7;
                }
                case 4: {
                    bl = zzbfn.zzc(parcel, n5);
                    continue block7;
                }
                case 1000: 
            }
            n4 = zzbfn.zzg(parcel, n5);
        }
        zzbfn.zzaf(parcel, n);
        return new RawDataSet(n4, n3, n2, arrayList, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new RawDataSet[n];
    }
}


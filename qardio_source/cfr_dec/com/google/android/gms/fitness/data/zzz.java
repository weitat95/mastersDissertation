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
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.internal.zzbfn;

public final class zzz
implements Parcelable.Creator<RawDataPoint> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        long l = 0L;
        long l2 = 0L;
        Value[] arrvalue = null;
        int n3 = 0;
        int n4 = 0;
        long l3 = 0L;
        long l4 = 0L;
        block10 : while (parcel.dataPosition() < n) {
            int n5 = parcel.readInt();
            switch (0xFFFF & n5) {
                default: {
                    zzbfn.zzb(parcel, n5);
                    continue block10;
                }
                case 1: {
                    l = zzbfn.zzi(parcel, n5);
                    continue block10;
                }
                case 2: {
                    l2 = zzbfn.zzi(parcel, n5);
                    continue block10;
                }
                case 3: {
                    arrvalue = zzbfn.zzb(parcel, n5, Value.CREATOR);
                    continue block10;
                }
                case 4: {
                    n3 = zzbfn.zzg(parcel, n5);
                    continue block10;
                }
                case 5: {
                    n4 = zzbfn.zzg(parcel, n5);
                    continue block10;
                }
                case 6: {
                    l3 = zzbfn.zzi(parcel, n5);
                    continue block10;
                }
                case 7: {
                    l4 = zzbfn.zzi(parcel, n5);
                    continue block10;
                }
                case 1000: 
            }
            n2 = zzbfn.zzg(parcel, n5);
        }
        zzbfn.zzaf(parcel, n);
        return new RawDataPoint(n2, l, l2, arrvalue, n3, n4, l3, l4);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new RawDataPoint[n];
    }
}


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
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.internal.zzbfn;

public final class zzah
implements Parcelable.Creator<Subscription> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        DataType dataType = null;
        int n = 0;
        int n2 = zzbfn.zzd(parcel);
        long l = 0L;
        DataSource dataSource = null;
        int n3 = 0;
        block7 : while (parcel.dataPosition() < n2) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block7;
                }
                case 1: {
                    dataSource = zzbfn.zza(parcel, n4, DataSource.CREATOR);
                    continue block7;
                }
                case 2: {
                    dataType = zzbfn.zza(parcel, n4, DataType.CREATOR);
                    continue block7;
                }
                case 3: {
                    l = zzbfn.zzi(parcel, n4);
                    continue block7;
                }
                case 4: {
                    n = zzbfn.zzg(parcel, n4);
                    continue block7;
                }
                case 1000: 
            }
            n3 = zzbfn.zzg(parcel, n4);
        }
        zzbfn.zzaf(parcel, n2);
        return new Subscription(n3, dataSource, dataType, l, n);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Subscription[n];
    }
}


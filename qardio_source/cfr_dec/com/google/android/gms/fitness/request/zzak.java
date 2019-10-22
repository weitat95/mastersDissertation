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
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzaj;
import com.google.android.gms.internal.zzbfn;

public final class zzak
implements Parcelable.Creator<zzaj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        DataType dataType = null;
        int n2 = 0;
        IBinder iBinder = null;
        block5 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block5;
                }
                case 1: {
                    dataType = zzbfn.zza(parcel, n3, DataType.CREATOR);
                    continue block5;
                }
                case 2: {
                    iBinder = zzbfn.zzr(parcel, n3);
                    continue block5;
                }
                case 1000: 
            }
            n2 = zzbfn.zzg(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzaj(n2, dataType, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzaj[n];
    }
}


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
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.request.zzk;
import com.google.android.gms.internal.zzbfn;

public final class zzl
implements Parcelable.Creator<zzk> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        IBinder iBinder = null;
        DataSet dataSet = null;
        int n2 = 0;
        boolean bl = false;
        block6 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block6;
                }
                case 1: {
                    dataSet = zzbfn.zza(parcel, n3, DataSet.CREATOR);
                    continue block6;
                }
                case 2: {
                    iBinder = zzbfn.zzr(parcel, n3);
                    continue block6;
                }
                case 4: {
                    bl = zzbfn.zzc(parcel, n3);
                    continue block6;
                }
                case 1000: 
            }
            n2 = zzbfn.zzg(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzk(n2, dataSet, iBinder, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzk[n];
    }
}


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
import com.google.android.gms.fitness.request.zzg;
import com.google.android.gms.internal.zzbfn;

public final class zzh
implements Parcelable.Creator<zzg> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        DataType dataType = null;
        IBinder iBinder = null;
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
                    iBinder = zzbfn.zzr(parcel, n3);
                    continue block6;
                }
                case 2: {
                    dataType = zzbfn.zza(parcel, n3, DataType.CREATOR);
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
        return new zzg(n2, iBinder, dataType, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzg[n];
    }
}


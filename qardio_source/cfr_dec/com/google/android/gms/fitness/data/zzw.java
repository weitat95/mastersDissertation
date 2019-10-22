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
import com.google.android.gms.fitness.data.MapValue;
import com.google.android.gms.internal.zzbfn;

public final class zzw
implements Parcelable.Creator<MapValue> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = 0;
        int n2 = zzbfn.zzd(parcel);
        float f = 0.0f;
        int n3 = 0;
        block5 : while (parcel.dataPosition() < n2) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block5;
                }
                case 1: {
                    n = zzbfn.zzg(parcel, n4);
                    continue block5;
                }
                case 2: {
                    f = zzbfn.zzl(parcel, n4);
                    continue block5;
                }
                case 1000: 
            }
            n3 = zzbfn.zzg(parcel, n4);
        }
        zzbfn.zzaf(parcel, n2);
        return new MapValue(n3, n, f);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new MapValue[n];
    }
}


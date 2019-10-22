/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzdkq;

public final class zzdkr
implements Parcelable.Creator<zzdkq> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        byte[] arrby = null;
        block3 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block3;
                }
                case 2: 
            }
            arrby = zzbfn.zzt(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zzdkq(arrby);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzdkq[n];
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.maps.model.LatLng;

public final class zzf
implements Parcelable.Creator<LatLng> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        double d = 0.0;
        int n = zzbfn.zzd(parcel);
        double d2 = 0.0;
        block4 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block4;
                }
                case 2: {
                    d2 = zzbfn.zzn(parcel, n2);
                    continue block4;
                }
                case 3: 
            }
            d = zzbfn.zzn(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new LatLng(d2, d);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LatLng[n];
    }
}


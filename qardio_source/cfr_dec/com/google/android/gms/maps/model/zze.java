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
import com.google.android.gms.maps.model.LatLngBounds;

public final class zze
implements Parcelable.Creator<LatLngBounds> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        LatLng latLng = null;
        LatLng latLng2 = null;
        block4 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block4;
                }
                case 2: {
                    latLng2 = zzbfn.zza(parcel, n2, LatLng.CREATOR);
                    continue block4;
                }
                case 3: 
            }
            latLng = zzbfn.zza(parcel, n2, LatLng.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new LatLngBounds(latLng2, latLng);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LatLngBounds[n];
    }
}


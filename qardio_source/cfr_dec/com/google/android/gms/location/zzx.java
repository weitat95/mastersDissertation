/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.location.LocationResult;
import java.util.List;

public final class zzx
implements Parcelable.Creator<LocationResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        List<Location> list = LocationResult.zziju;
        block3 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block3;
                }
                case 1: 
            }
            list = zzbfn.zzc(parcel, n2, Location.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new LocationResult(list);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LocationResult[n];
    }
}


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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public final class zza
implements Parcelable.Creator<CameraPosition> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        float f = 0.0f;
        float f2 = 0.0f;
        LatLng latLng = null;
        float f3 = 0.0f;
        block6 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block6;
                }
                case 2: {
                    latLng = zzbfn.zza(parcel, n2, LatLng.CREATOR);
                    continue block6;
                }
                case 3: {
                    f2 = zzbfn.zzl(parcel, n2);
                    continue block6;
                }
                case 4: {
                    f = zzbfn.zzl(parcel, n2);
                    continue block6;
                }
                case 5: 
            }
            f3 = zzbfn.zzl(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new CameraPosition(latLng, f2, f, f3);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new CameraPosition[n];
    }
}


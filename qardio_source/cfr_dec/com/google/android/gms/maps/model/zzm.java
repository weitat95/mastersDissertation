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
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public final class zzm
implements Parcelable.Creator<StreetViewPanoramaCamera> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        float f = 0.0f;
        int n = zzbfn.zzd(parcel);
        float f2 = 0.0f;
        float f3 = 0.0f;
        block5 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block5;
                }
                case 2: {
                    f3 = zzbfn.zzl(parcel, n2);
                    continue block5;
                }
                case 3: {
                    f2 = zzbfn.zzl(parcel, n2);
                    continue block5;
                }
                case 4: 
            }
            f = zzbfn.zzl(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new StreetViewPanoramaCamera(f3, f2, f);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new StreetViewPanoramaCamera[n];
    }
}


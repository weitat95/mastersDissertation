/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public final class zzai
implements Parcelable.Creator<StreetViewPanoramaOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Integer n = null;
        byte by = 0;
        int n2 = zzbfn.zzd(parcel);
        byte by2 = 0;
        byte by3 = 0;
        byte by4 = 0;
        byte by5 = 0;
        LatLng latLng = null;
        String string2 = null;
        StreetViewPanoramaCamera streetViewPanoramaCamera = null;
        block11 : while (parcel.dataPosition() < n2) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block11;
                }
                case 2: {
                    streetViewPanoramaCamera = zzbfn.zza(parcel, n3, StreetViewPanoramaCamera.CREATOR);
                    continue block11;
                }
                case 3: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block11;
                }
                case 4: {
                    latLng = zzbfn.zza(parcel, n3, LatLng.CREATOR);
                    continue block11;
                }
                case 5: {
                    n = zzbfn.zzh(parcel, n3);
                    continue block11;
                }
                case 6: {
                    by5 = zzbfn.zze(parcel, n3);
                    continue block11;
                }
                case 7: {
                    by4 = zzbfn.zze(parcel, n3);
                    continue block11;
                }
                case 8: {
                    by3 = zzbfn.zze(parcel, n3);
                    continue block11;
                }
                case 9: {
                    by2 = zzbfn.zze(parcel, n3);
                    continue block11;
                }
                case 10: 
            }
            by = zzbfn.zze(parcel, n3);
        }
        zzbfn.zzaf(parcel, n2);
        return new StreetViewPanoramaOptions(streetViewPanoramaCamera, string2, latLng, n, by5, by4, by3, by2, by);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new StreetViewPanoramaOptions[n];
    }
}


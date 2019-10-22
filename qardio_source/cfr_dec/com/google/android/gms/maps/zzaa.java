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
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;

public final class zzaa
implements Parcelable.Creator<GoogleMapOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        byte by = -1;
        byte by2 = -1;
        int n2 = 0;
        CameraPosition cameraPosition = null;
        byte by3 = -1;
        byte by4 = -1;
        byte by5 = -1;
        byte by6 = -1;
        byte by7 = -1;
        byte by8 = -1;
        byte by9 = -1;
        byte by10 = -1;
        byte by11 = -1;
        Float f = null;
        Float f2 = null;
        LatLngBounds latLngBounds = null;
        block18 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block18;
                }
                case 2: {
                    by = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 3: {
                    by2 = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 4: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block18;
                }
                case 5: {
                    cameraPosition = zzbfn.zza(parcel, n3, CameraPosition.CREATOR);
                    continue block18;
                }
                case 6: {
                    by3 = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 7: {
                    by4 = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 8: {
                    by5 = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 9: {
                    by6 = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 10: {
                    by7 = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 11: {
                    by8 = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 12: {
                    by9 = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 14: {
                    by10 = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 15: {
                    by11 = zzbfn.zze(parcel, n3);
                    continue block18;
                }
                case 16: {
                    f = zzbfn.zzm(parcel, n3);
                    continue block18;
                }
                case 17: {
                    f2 = zzbfn.zzm(parcel, n3);
                    continue block18;
                }
                case 18: 
            }
            latLngBounds = zzbfn.zza(parcel, n3, LatLngBounds.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new GoogleMapOptions(by, by2, n2, cameraPosition, by3, by4, by5, by6, by7, by8, by9, by10, by11, f, f2, latLngBounds);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new GoogleMapOptions[n];
    }
}


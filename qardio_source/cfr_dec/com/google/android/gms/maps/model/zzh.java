/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public final class zzh
implements Parcelable.Creator<MarkerOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        LatLng latLng = null;
        String string2 = null;
        String string3 = null;
        IBinder iBinder = null;
        float f = 0.0f;
        float f2 = 0.0f;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        float f3 = 0.0f;
        float f4 = 0.5f;
        float f5 = 0.0f;
        float f6 = 1.0f;
        float f7 = 0.0f;
        block16 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block16;
                }
                case 2: {
                    latLng = zzbfn.zza(parcel, n2, LatLng.CREATOR);
                    continue block16;
                }
                case 3: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block16;
                }
                case 4: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block16;
                }
                case 5: {
                    iBinder = zzbfn.zzr(parcel, n2);
                    continue block16;
                }
                case 6: {
                    f = zzbfn.zzl(parcel, n2);
                    continue block16;
                }
                case 7: {
                    f2 = zzbfn.zzl(parcel, n2);
                    continue block16;
                }
                case 8: {
                    bl = zzbfn.zzc(parcel, n2);
                    continue block16;
                }
                case 9: {
                    bl2 = zzbfn.zzc(parcel, n2);
                    continue block16;
                }
                case 10: {
                    bl3 = zzbfn.zzc(parcel, n2);
                    continue block16;
                }
                case 11: {
                    f3 = zzbfn.zzl(parcel, n2);
                    continue block16;
                }
                case 12: {
                    f4 = zzbfn.zzl(parcel, n2);
                    continue block16;
                }
                case 13: {
                    f5 = zzbfn.zzl(parcel, n2);
                    continue block16;
                }
                case 14: {
                    f6 = zzbfn.zzl(parcel, n2);
                    continue block16;
                }
                case 15: 
            }
            f7 = zzbfn.zzl(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new MarkerOptions(latLng, string2, string3, iBinder, f, f2, bl, bl2, bl3, f3, f4, f5, f6, f7);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new MarkerOptions[n];
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.location.LocationSettingsStates;

public final class zzad
implements Parcelable.Creator<LocationSettingsStates> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean bl = false;
        int n = zzbfn.zzd(parcel);
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        block8 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block8;
                }
                case 1: {
                    bl6 = zzbfn.zzc(parcel, n2);
                    continue block8;
                }
                case 2: {
                    bl5 = zzbfn.zzc(parcel, n2);
                    continue block8;
                }
                case 3: {
                    bl4 = zzbfn.zzc(parcel, n2);
                    continue block8;
                }
                case 4: {
                    bl3 = zzbfn.zzc(parcel, n2);
                    continue block8;
                }
                case 5: {
                    bl2 = zzbfn.zzc(parcel, n2);
                    continue block8;
                }
                case 6: 
            }
            bl = zzbfn.zzc(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new LocationSettingsStates(bl6, bl5, bl4, bl3, bl2, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LocationSettingsStates[n];
    }
}


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
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzz;
import java.util.ArrayList;
import java.util.List;

public final class zzab
implements Parcelable.Creator<LocationSettingsRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        zzz zzz2 = null;
        boolean bl = false;
        int n = zzbfn.zzd(parcel);
        boolean bl2 = false;
        ArrayList<LocationRequest> arrayList = null;
        block6 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block6;
                }
                case 1: {
                    arrayList = zzbfn.zzc(parcel, n2, LocationRequest.CREATOR);
                    continue block6;
                }
                case 2: {
                    bl2 = zzbfn.zzc(parcel, n2);
                    continue block6;
                }
                case 3: {
                    bl = zzbfn.zzc(parcel, n2);
                    continue block6;
                }
                case 5: 
            }
            zzz2 = zzbfn.zza(parcel, n2, zzz.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new LocationSettingsRequest(arrayList, bl2, bl, zzz2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LocationSettingsRequest[n];
    }
}


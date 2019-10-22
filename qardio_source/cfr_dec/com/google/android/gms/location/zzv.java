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
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.zzae;

public final class zzv
implements Parcelable.Creator<LocationAvailability> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = 1;
        int n2 = zzbfn.zzd(parcel);
        int n3 = 1000;
        long l = 0L;
        zzae[] arrzzae = null;
        int n4 = 1;
        block7 : while (parcel.dataPosition() < n2) {
            int n5 = parcel.readInt();
            switch (0xFFFF & n5) {
                default: {
                    zzbfn.zzb(parcel, n5);
                    continue block7;
                }
                case 1: {
                    n4 = zzbfn.zzg(parcel, n5);
                    continue block7;
                }
                case 2: {
                    n = zzbfn.zzg(parcel, n5);
                    continue block7;
                }
                case 3: {
                    l = zzbfn.zzi(parcel, n5);
                    continue block7;
                }
                case 4: {
                    n3 = zzbfn.zzg(parcel, n5);
                    continue block7;
                }
                case 5: 
            }
            arrzzae = zzbfn.zzb(parcel, n5, zzae.CREATOR);
        }
        zzbfn.zzaf(parcel, n2);
        return new LocationAvailability(n3, n4, n, l, arrzzae);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LocationAvailability[n];
    }
}


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

public final class zzw
implements Parcelable.Creator<LocationRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 102;
        long l = 3600000L;
        long l2 = 600000L;
        boolean bl = false;
        long l3 = Long.MAX_VALUE;
        int n3 = Integer.MAX_VALUE;
        float f = 0.0f;
        long l4 = 0L;
        block10 : while (parcel.dataPosition() < n) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block10;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n4);
                    continue block10;
                }
                case 2: {
                    l = zzbfn.zzi(parcel, n4);
                    continue block10;
                }
                case 3: {
                    l2 = zzbfn.zzi(parcel, n4);
                    continue block10;
                }
                case 4: {
                    bl = zzbfn.zzc(parcel, n4);
                    continue block10;
                }
                case 5: {
                    l3 = zzbfn.zzi(parcel, n4);
                    continue block10;
                }
                case 6: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block10;
                }
                case 7: {
                    f = zzbfn.zzl(parcel, n4);
                    continue block10;
                }
                case 8: 
            }
            l4 = zzbfn.zzi(parcel, n4);
        }
        zzbfn.zzaf(parcel, n);
        return new LocationRequest(n2, l, l2, bl, l3, n3, f, l4);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new LocationRequest[n];
    }
}


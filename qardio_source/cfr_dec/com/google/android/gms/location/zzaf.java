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
import com.google.android.gms.location.zzae;

public final class zzaf
implements Parcelable.Creator<zzae> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long l = -1L;
        int n = 1;
        int n2 = zzbfn.zzd(parcel);
        long l2 = -1L;
        int n3 = 1;
        block6 : while (parcel.dataPosition() < n2) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block6;
                }
                case 1: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block6;
                }
                case 2: {
                    n = zzbfn.zzg(parcel, n4);
                    continue block6;
                }
                case 3: {
                    l2 = zzbfn.zzi(parcel, n4);
                    continue block6;
                }
                case 4: 
            }
            l = zzbfn.zzi(parcel, n4);
        }
        zzbfn.zzaf(parcel, n2);
        return new zzae(n3, n, l2, l);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzae[n];
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wearable.internal.zzdg;

public final class zzdh
implements Parcelable.Creator<zzdg> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = 0;
        int n2 = zzbfn.zzd(parcel);
        int n3 = 0;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block4;
                }
                case 2: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block4;
                }
                case 3: 
            }
            n = zzbfn.zzg(parcel, n4);
        }
        zzbfn.zzaf(parcel, n2);
        return new zzdg(n3, n);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzdg[n];
    }
}


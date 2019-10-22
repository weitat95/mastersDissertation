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
import com.google.android.gms.wearable.internal.zzeg;
import com.google.android.gms.wearable.internal.zzfo;

public final class zzeh
implements Parcelable.Creator<zzeg> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        zzfo zzfo2 = null;
        block4 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block4;
                }
                case 2: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block4;
                }
                case 3: 
            }
            zzfo2 = zzbfn.zza(parcel, n3, zzfo.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new zzeg(n2, zzfo2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzeg[n];
    }
}


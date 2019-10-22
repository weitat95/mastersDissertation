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
import com.google.android.gms.wearable.internal.zzi;

public final class zzj
implements Parcelable.Creator<zzi> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        byte by = 0;
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        byte by2 = 0;
        block5 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block5;
                }
                case 2: {
                    by2 = zzbfn.zze(parcel, n2);
                    continue block5;
                }
                case 3: {
                    by = zzbfn.zze(parcel, n2);
                    continue block5;
                }
                case 4: 
            }
            string2 = zzbfn.zzq(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zzi(by2, by, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzi[n];
    }
}


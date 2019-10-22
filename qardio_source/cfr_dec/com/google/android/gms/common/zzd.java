/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzbfn;

public final class zzd
implements Parcelable.Creator<zzc> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        int n2 = 0;
        block4 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block4;
                }
                case 1: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block4;
                }
                case 2: 
            }
            n2 = zzbfn.zzg(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzc(string2, n2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzc[n];
    }
}


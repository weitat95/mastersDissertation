/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzcxo;

public final class zzcxp
implements Parcelable.Creator<zzcxo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        zzbr zzbr2 = null;
        block4 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block4;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block4;
                }
                case 2: 
            }
            zzbr2 = zzbfn.zza(parcel, n3, zzbr.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new zzcxo(n2, zzbr2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcxo[n];
    }
}


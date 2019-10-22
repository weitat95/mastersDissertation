/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzcxg;

public final class zzcxh
implements Parcelable.Creator<zzcxg> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = 0;
        int n2 = zzbfn.zzd(parcel);
        Intent intent = null;
        int n3 = 0;
        block5 : while (parcel.dataPosition() < n2) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block5;
                }
                case 1: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block5;
                }
                case 2: {
                    n = zzbfn.zzg(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            intent = (Intent)zzbfn.zza(parcel, n4, Intent.CREATOR);
        }
        zzbfn.zzaf(parcel, n2);
        return new zzcxg(n3, n, intent);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcxg[n];
    }
}


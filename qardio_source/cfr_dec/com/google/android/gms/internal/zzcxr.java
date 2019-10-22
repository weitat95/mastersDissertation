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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbt;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzcxq;

public final class zzcxr
implements Parcelable.Creator<zzcxq> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        ConnectionResult connectionResult = null;
        int n2 = 0;
        zzbt zzbt2 = null;
        block5 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block5;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block5;
                }
                case 2: {
                    connectionResult = zzbfn.zza(parcel, n3, ConnectionResult.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            zzbt2 = zzbfn.zza(parcel, n3, zzbt.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new zzcxq(n2, connectionResult, zzbt2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcxq[n];
    }
}


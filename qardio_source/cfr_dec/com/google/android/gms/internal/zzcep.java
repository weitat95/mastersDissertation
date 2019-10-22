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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzceo;

public final class zzcep
implements Parcelable.Creator<zzceo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        Status status = null;
        block3 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block3;
                }
                case 1: 
            }
            status = zzbfn.zza(parcel, n2, Status.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new zzceo(status);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzceo[n];
    }
}


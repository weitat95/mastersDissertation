/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wearable.internal.zzdm;

public final class zzdn
implements Parcelable.Creator<zzdm> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
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
            parcelFileDescriptor = (ParcelFileDescriptor)zzbfn.zza(parcel, n3, ParcelFileDescriptor.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new zzdm(n2, parcelFileDescriptor);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzdm[n];
    }
}


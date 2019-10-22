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
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.internal.zzay;

public final class zzax
implements Parcelable.Creator<zzaw> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        int n3 = 0;
        zzay zzay2 = null;
        int n4 = 0;
        block6 : while (parcel.dataPosition() < n) {
            int n5 = parcel.readInt();
            switch (0xFFFF & n5) {
                default: {
                    zzbfn.zzb(parcel, n5);
                    continue block6;
                }
                case 2: {
                    zzay2 = zzbfn.zza(parcel, n5, zzay.CREATOR);
                    continue block6;
                }
                case 3: {
                    n3 = zzbfn.zzg(parcel, n5);
                    continue block6;
                }
                case 4: {
                    n2 = zzbfn.zzg(parcel, n5);
                    continue block6;
                }
                case 5: 
            }
            n4 = zzbfn.zzg(parcel, n5);
        }
        zzbfn.zzaf(parcel, n);
        return new zzaw(zzay2, n3, n2, n4);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzaw[n];
    }
}


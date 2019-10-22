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
import com.google.android.gms.wearable.internal.zzdt;

public final class zzds
implements Parcelable.Creator<zzdt> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean bl = false;
        int n = zzbfn.zzd(parcel);
        boolean bl2 = false;
        int n2 = 0;
        block5 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block5;
                }
                case 2: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block5;
                }
                case 3: {
                    bl2 = zzbfn.zzc(parcel, n3);
                    continue block5;
                }
                case 4: 
            }
            bl = zzbfn.zzc(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzdt(n2, bl2, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzdt[n];
    }
}


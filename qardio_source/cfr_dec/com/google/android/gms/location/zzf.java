/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.location.zze;

public final class zzf
implements Parcelable.Creator<zze> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        boolean bl = true;
        long l = 50L;
        float f = 0.0f;
        long l2 = Long.MAX_VALUE;
        int n2 = Integer.MAX_VALUE;
        block7 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block7;
                }
                case 1: {
                    bl = zzbfn.zzc(parcel, n3);
                    continue block7;
                }
                case 2: {
                    l = zzbfn.zzi(parcel, n3);
                    continue block7;
                }
                case 3: {
                    f = zzbfn.zzl(parcel, n3);
                    continue block7;
                }
                case 4: {
                    l2 = zzbfn.zzi(parcel, n3);
                    continue block7;
                }
                case 5: 
            }
            n2 = zzbfn.zzg(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zze(bl, l, f, l2, n2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zze[n];
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzcdx;
import com.google.android.gms.internal.zzcdz;

public final class zzcea
implements Parcelable.Creator<zzcdz> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        IBinder iBinder = null;
        zzcdx zzcdx2 = null;
        int n2 = 1;
        IBinder iBinder2 = null;
        block6 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block6;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block6;
                }
                case 2: {
                    zzcdx2 = zzbfn.zza(parcel, n3, zzcdx.CREATOR);
                    continue block6;
                }
                case 3: {
                    iBinder = zzbfn.zzr(parcel, n3);
                    continue block6;
                }
                case 4: 
            }
            iBinder2 = zzbfn.zzr(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzcdz(n2, zzcdx2, iBinder, iBinder2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcdz[n];
    }
}


/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.wobs.TimeInterval;

public final class zzk
implements Parcelable.Creator<TimeInterval> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long l = 0L;
        int n = zzbfn.zzd(parcel);
        long l2 = 0L;
        block4 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block4;
                }
                case 2: {
                    l2 = zzbfn.zzi(parcel, n2);
                    continue block4;
                }
                case 3: 
            }
            l = zzbfn.zzi(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new TimeInterval(l2, l);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new TimeInterval[n];
    }
}


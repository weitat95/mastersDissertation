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
import com.google.android.gms.wearable.internal.zzay;

public final class zzbi
implements Parcelable.Creator<zzay> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        int n = zzbfn.zzd(parcel);
        String string3 = null;
        String string4 = null;
        block5 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block5;
                }
                case 2: {
                    string4 = zzbfn.zzq(parcel, n2);
                    continue block5;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block5;
                }
                case 4: 
            }
            string2 = zzbfn.zzq(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new zzay(string4, string3, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzay[n];
    }
}


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
import com.google.android.gms.wearable.internal.zzah;
import com.google.android.gms.wearable.internal.zzfo;
import java.util.ArrayList;
import java.util.List;

public final class zzai
implements Parcelable.Creator<zzah> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        ArrayList<zzfo> arrayList = null;
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        block4 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block4;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block4;
                }
                case 3: 
            }
            arrayList = zzbfn.zzc(parcel, n2, zzfo.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new zzah(string2, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzah[n];
    }
}


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
import com.google.android.gms.wearable.internal.zzea;
import com.google.android.gms.wearable.internal.zzfo;
import java.util.ArrayList;
import java.util.List;

public final class zzeb
implements Parcelable.Creator<zzea> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        ArrayList<zzfo> arrayList = null;
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
            arrayList = zzbfn.zzc(parcel, n3, zzfo.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new zzea(n2, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzea[n];
    }
}


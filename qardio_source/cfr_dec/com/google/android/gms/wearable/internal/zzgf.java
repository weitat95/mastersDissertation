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
import com.google.android.gms.wearable.internal.zzfs;
import com.google.android.gms.wearable.internal.zzge;
import java.util.ArrayList;
import java.util.List;

public final class zzgf
implements Parcelable.Creator<zzge> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        long l = 0L;
        ArrayList<zzfs> arrayList = null;
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
                    l = zzbfn.zzi(parcel, n3);
                    continue block5;
                }
                case 4: 
            }
            arrayList = zzbfn.zzc(parcel, n3, zzfs.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new zzge(n2, l, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzge[n];
    }
}


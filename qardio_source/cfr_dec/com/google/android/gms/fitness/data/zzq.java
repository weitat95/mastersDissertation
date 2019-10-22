/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.internal.zzbfn;

public final class zzq
implements Parcelable.Creator<Field> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Boolean bl = null;
        int n = 0;
        int n2 = zzbfn.zzd(parcel);
        String string2 = null;
        int n3 = 0;
        block6 : while (parcel.dataPosition() < n2) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block6;
                }
                case 1: {
                    string2 = zzbfn.zzq(parcel, n4);
                    continue block6;
                }
                case 2: {
                    n = zzbfn.zzg(parcel, n4);
                    continue block6;
                }
                case 3: {
                    bl = zzbfn.zzd(parcel, n4);
                    continue block6;
                }
                case 1000: 
            }
            n3 = zzbfn.zzg(parcel, n4);
        }
        zzbfn.zzaf(parcel, n2);
        return new Field(n3, string2, n, bl);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Field[n];
    }
}


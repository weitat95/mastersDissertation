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
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzl
implements Parcelable.Creator<DataType> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        String string3 = null;
        ArrayList<Field> arrayList = null;
        String string4 = null;
        block7 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block7;
                }
                case 1: {
                    string4 = zzbfn.zzq(parcel, n3);
                    continue block7;
                }
                case 2: {
                    arrayList = zzbfn.zzc(parcel, n3, Field.CREATOR);
                    continue block7;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block7;
                }
                case 4: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block7;
                }
                case 1000: 
            }
            n2 = zzbfn.zzg(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new DataType(n2, string4, arrayList, string3, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new DataType[n];
    }
}


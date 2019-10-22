/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.internal.zzbfn;

public final class zzai
implements Parcelable.Creator<Value> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean bl = false;
        byte[] arrby = null;
        int n = zzbfn.zzd(parcel);
        float f = 0.0f;
        float[] arrf = null;
        int[] arrn = null;
        Bundle bundle = null;
        String string2 = null;
        int n2 = 0;
        int n3 = 0;
        block11 : while (parcel.dataPosition() < n) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block11;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n4);
                    continue block11;
                }
                case 2: {
                    bl = zzbfn.zzc(parcel, n4);
                    continue block11;
                }
                case 3: {
                    f = zzbfn.zzl(parcel, n4);
                    continue block11;
                }
                case 4: {
                    string2 = zzbfn.zzq(parcel, n4);
                    continue block11;
                }
                case 5: {
                    bundle = zzbfn.zzs(parcel, n4);
                    continue block11;
                }
                case 6: {
                    arrn = zzbfn.zzw(parcel, n4);
                    continue block11;
                }
                case 7: {
                    arrf = zzbfn.zzy(parcel, n4);
                    continue block11;
                }
                case 1000: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block11;
                }
                case 8: 
            }
            arrby = zzbfn.zzt(parcel, n4);
        }
        zzbfn.zzaf(parcel, n);
        return new Value(n3, n2, bl, f, string2, bundle, arrn, arrf, arrby);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Value[n];
    }
}


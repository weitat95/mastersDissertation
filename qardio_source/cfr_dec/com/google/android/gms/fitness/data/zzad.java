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
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.zzb;
import com.google.android.gms.internal.zzbfn;

public final class zzad
implements Parcelable.Creator<Session> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long l = 0L;
        int n = 0;
        Long l2 = null;
        int n2 = zzbfn.zzd(parcel);
        zzb zzb2 = null;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        long l3 = 0L;
        int n3 = 0;
        block11 : while (parcel.dataPosition() < n2) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block11;
                }
                case 1: {
                    l3 = zzbfn.zzi(parcel, n4);
                    continue block11;
                }
                case 2: {
                    l = zzbfn.zzi(parcel, n4);
                    continue block11;
                }
                case 3: {
                    string4 = zzbfn.zzq(parcel, n4);
                    continue block11;
                }
                case 4: {
                    string3 = zzbfn.zzq(parcel, n4);
                    continue block11;
                }
                case 5: {
                    string2 = zzbfn.zzq(parcel, n4);
                    continue block11;
                }
                case 7: {
                    n = zzbfn.zzg(parcel, n4);
                    continue block11;
                }
                case 1000: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block11;
                }
                case 8: {
                    zzb2 = zzbfn.zza(parcel, n4, zzb.CREATOR);
                    continue block11;
                }
                case 9: 
            }
            l2 = zzbfn.zzj(parcel, n4);
        }
        zzbfn.zzaf(parcel, n2);
        return new Session(n3, l3, l, string4, string3, string2, n, zzb2, l2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Session[n];
    }
}


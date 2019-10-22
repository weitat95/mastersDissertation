/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzcln;

public final class zzclo
implements Parcelable.Creator<zzcln> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Double d = null;
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        long l = 0L;
        String string2 = null;
        String string3 = null;
        Float f = null;
        Long l2 = null;
        String string4 = null;
        block10 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block10;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block10;
                }
                case 2: {
                    string4 = zzbfn.zzq(parcel, n3);
                    continue block10;
                }
                case 3: {
                    l = zzbfn.zzi(parcel, n3);
                    continue block10;
                }
                case 4: {
                    l2 = zzbfn.zzj(parcel, n3);
                    continue block10;
                }
                case 5: {
                    f = zzbfn.zzm(parcel, n3);
                    continue block10;
                }
                case 6: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block10;
                }
                case 7: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block10;
                }
                case 8: 
            }
            d = zzbfn.zzo(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new zzcln(n2, string4, l, l2, f, string3, string2, d);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzcln[n];
    }
}

